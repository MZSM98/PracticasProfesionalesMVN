package com.pdc.controlador.coordinador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ProyectoDTO.EstadoProyecto;
import com.pdc.utileria.AlertaUtil;

import java.net.URL;
import java.util.ResourceBundle;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;

public class CoordinadorGestionProyectoController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionProyectoController.class);
    
    @FXML
    private Button botonSalir;
    
    @FXML
    private TextField textBuscarTitulo;
        
    @FXML
    private TableView<ProyectoDTO> tableProyectos;
    
    @FXML
    private TableColumn<ProyectoDTO, Integer> columnProyectoID;
    
    @FXML
    private TableColumn<ProyectoDTO, String> columnTituloProyecto;
    
    @FXML
    private TableColumn<ProyectoDTO, String> columnPeriodoEscolar;
    
    @FXML
    private TableColumn<ProyectoDTO, String> columnOrganizacionVinculada;
    
    @FXML
    private TableColumn<ProyectoDTO, String> columnEstadoProyecto;
    
    private IProyectoDAO interfazProyectoDAO;
    private IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private ObservableList<ProyectoDTO> listaProyectos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        configurarColumnas();
        cargarProyectos();
        
        textBuscarTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarProyectosPorTitulo(newValue);
        });
    }
    
    private void configurarColumnas() {
        columnProyectoID.setCellValueFactory(new PropertyValueFactory<>("proyectoID"));
        columnTituloProyecto.setCellValueFactory(new PropertyValueFactory<>("tituloProyecto"));
        columnPeriodoEscolar.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        columnEstadoProyecto.setCellValueFactory(new PropertyValueFactory<>("estadoProyecto"));
        
        columnOrganizacionVinculada.setCellValueFactory(cellData -> {
            String rfcMoral = cellData.getValue().getRfcMoral();
            try {
                OrganizacionVinculadaDTO organizacion = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
                return new javafx.beans.property.SimpleStringProperty(
                    organizacion != null ? organizacion.getNombreOV() : rfcMoral
                );
            } catch (SQLException | IOException e) {
                LOG.error("Error al obtener nombre de organización: " + e.getMessage());
                return new javafx.beans.property.SimpleStringProperty(rfcMoral);
            }
        });
    }
    
    private void cargarProyectos() {
        try {
            List<ProyectoDTO> proyectos = interfazProyectoDAO.listarProyectos();
            listaProyectos = FXCollections.observableArrayList(proyectos);
            tableProyectos.setItems(listaProyectos);
        } catch (SQLException sqle) {
            LOG.error("Error al cargar los proyectos: " + sqle.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo cargar la información, contacte al administrador", Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            LOG.error("No se lograron cargar los proyectos: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo cargar la información, contacte al administrador", Alert.AlertType.ERROR);
        }
    }
    
    private void buscarProyectosPorTitulo(String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                cargarProyectos();
                return;
            }
            
            List<ProyectoDTO> proyectosEncontrados = interfazProyectoDAO.buscarProyectosPorNombre(titulo);
            listaProyectos = FXCollections.observableArrayList(proyectosEncontrados);
            tableProyectos.setItems(listaProyectos);
        } catch (SQLException sqle) {
            LOG.error("Error al buscar proyectos: " + sqle.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo realizar la búsqueda, contacte al administrador", Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            LOG.error("Error al buscar proyectos: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo realizar la búsqueda, contacte al administrador", Alert.AlertType.ERROR);
        }
    }
        
    @FXML
    private void abrirRegistroProyecto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/proyecto/FXMLRegistroProyecto.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registro de Proyecto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarProyectos();
        } catch (IOException ioe) {
            LOG.error("Error al cargar la ventana de registro: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Ha ocurrido un error, inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void editarProyecto(ActionEvent event) {
        ProyectoDTO proyectoSeleccionado = tableProyectos.getSelectionModel().getSelectedItem();
        
        if (proyectoSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione un proyecto para editar", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/proyecto/FXMLRegistroProyecto.fxml"));
            Parent root = loader.load();
            
            CoordinadorRegistroProyectoController controlador = loader.getController();
            
            controlador.cambiarAModoEdicion(true);
            controlador.llenarCamposEditablesProyecto(proyectoSeleccionado);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Proyecto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarProyectos();
        } catch (IOException ioe) {
            LOG.error("Error al cargar la ventana de edición: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición, contacte con un administrador", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cambiarEstadoProyecto(ActionEvent event) {
        ProyectoDTO proyectoSeleccionado = tableProyectos.getSelectionModel().getSelectedItem();
        
        if (proyectoSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione un proyecto para cambiar su estado", Alert.AlertType.WARNING);
            return;
        }
        
        String estadoActual = proyectoSeleccionado.getEstadoProyecto();
        
        String nuevoEstado = estadoActual.equalsIgnoreCase(EstadoProyecto.ACTIVO.name()) ? 
                EstadoProyecto.INACTIVO.name() : EstadoProyecto.ACTIVO.name();
        
        proyectoSeleccionado.setEstadoProyecto(nuevoEstado);
        
        try {
            boolean actualizacionExitosa = interfazProyectoDAO.editarProyecto(proyectoSeleccionado);
            
            if (actualizacionExitosa) {
                AlertaUtil.mostrarAlerta("Éxito", "Estado del proyecto cambiado a: " + nuevoEstado, Alert.AlertType.INFORMATION);
                cargarProyectos();
            } else {
                AlertaUtil.mostrarAlerta("Error", "No se pudo cambiar el estado del proyecto", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            LOG.error("Error de conexión con la base de datos: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Error al conectar con la base de datos: ", Alert.AlertType.ERROR);
        } catch (IOException e) {
            LOG.error("Error al cambiar estado del proyecto: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Error al actualizar el registro", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salirAMenuPrincipal(ActionEvent event) {
        Stage ventanaActual = (Stage) botonSalir.getScene().getWindow();
        ventanaActual.close();
    }
}