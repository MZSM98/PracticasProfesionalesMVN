package com.pdc.controlador.coordinador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ProyectoDTO.EstadoProyecto;
import com.pdc.utileria.AlertaUtil;
import java.net.URL;
import java.util.ResourceBundle;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import javafx.beans.property.SimpleStringProperty;

public class CoordinadorGestionProyectoController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionProyectoController.class);
    
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
    private ObservableList<ProyectoDTO> listaProyectos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazProyectoDAO = new ProyectoDAOImpl();
        configurarColumnas();
        cargarProyectos();
        
        textBuscarTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarProyectosPorTitulo(newValue);
        });
    }
    
    private void configurarColumnas() {
        
        columnProyectoID.setCellValueFactory(new PropertyValueFactory<>("proyectoID"));
        columnTituloProyecto.setCellValueFactory(new PropertyValueFactory<>("tituloProyecto"));
        columnPeriodoEscolar.setCellValueFactory(cellData -> {
            PeriodoEscolarDTO periodo = cellData.getValue().getPeriodoEscolar(); 
            return new SimpleStringProperty(periodo != null ? periodo.getNombrePeriodoEscolar() : "");
        });        
        columnEstadoProyecto.setCellValueFactory(new PropertyValueFactory<>("estadoProyecto"));
        
        columnOrganizacionVinculada.setCellValueFactory(cellData -> {
            OrganizacionVinculadaDTO organizacion = cellData.getValue().getOrganizacion();
            return new SimpleStringProperty(organizacion != null ? organizacion.getNombreOV() : "");
        });
    }
    
    private void cargarProyectos() {
        
        try {
            
            List<ProyectoDTO> proyectos;
            proyectos = interfazProyectoDAO.listarProyectos();
            listaProyectos = FXCollections.observableArrayList(proyectos);
            tableProyectos.setItems(listaProyectos);
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    private void buscarProyectosPorTitulo(String titulo) {
        
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                cargarProyectos();
                return;
            }
            
            List<ProyectoDTO> proyectosEncontrados;
            proyectosEncontrados = interfazProyectoDAO.buscarProyectosPorNombre(titulo);
            listaProyectos = FXCollections.observableArrayList(proyectosEncontrados);
            tableProyectos.setItems(listaProyectos);
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
        
    @FXML
    private void abrirRegistroProyecto(ActionEvent event) {
        
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_PROYECTO);
    }
    
    @FXML
    private void editarProyecto(ActionEvent event) {
        
        ProyectoDTO proyectoSeleccionado = tableProyectos.getSelectionModel().getSelectedItem();
        
        if (proyectoSeleccionado == null) {
            
            AlertaUtil.mostrarAlertaSeleccionRegistro();
            return;
        }
        try {
            CoordinadorRegistroProyectoController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_PROYECTO);       
            controlador.cambiarAModoEdicion(true);
            controlador.llenarCamposEditablesProyecto(proyectoSeleccionado);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_PROYECTO);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }
    
    @FXML
    private void cambiarEstadoProyecto(ActionEvent event) {
        
        ProyectoDTO proyectoSeleccionado;
        proyectoSeleccionado = tableProyectos.getSelectionModel().getSelectedItem();
        
        if (proyectoSeleccionado == null) {
            
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione un proyecto para cambiar su estado", Alert.AlertType.WARNING);
            return;
        }
        
        String estadoActual;
        estadoActual = proyectoSeleccionado.getEstadoProyecto();
        
        String nuevoEstado;
        nuevoEstado = estadoActual.equalsIgnoreCase(EstadoProyecto.ACTIVO.name()) ? 
                EstadoProyecto.INACTIVO.name() : EstadoProyecto.ACTIVO.name();
        
        proyectoSeleccionado.setEstadoProyecto(nuevoEstado);
        
        try {
            boolean actualizacionExitosa = interfazProyectoDAO.editarProyecto(proyectoSeleccionado);
            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta(AlertaUtil.EXITO, ConstantesUtil.ALERTA_CAMBIO_ESTADO_PROYECTO + nuevoEstado, Alert.AlertType.INFORMATION);
                cargarProyectos();
            } else {
                AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, "No se pudo cambiar el estado del proyecto", Alert.AlertType.WARNING);
            }
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } 
    }
    
    @FXML
    private void salirAMenuPrincipal(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
    }
}