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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO.EstadoOrganizacionVinculada;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class CoordinadorGestionOrganizacionVinculadaController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionOrganizacionVinculadaController.class);
    
    @FXML
    private Button botonSalir;
        
    @FXML
    private TableView<OrganizacionVinculadaDTO> tableOrganizacionesVinculadas;
    
    @FXML
    private TableColumn<OrganizacionVinculadaDTO, String> columnRFC;
    
    @FXML
    private TableColumn<OrganizacionVinculadaDTO, String> columnNombre;
    
    @FXML
    private TableColumn<OrganizacionVinculadaDTO, String> columnTelefono;
    
    @FXML
    private TableColumn<OrganizacionVinculadaDTO, String> columnDireccion;
    
    @FXML
    private TableColumn<OrganizacionVinculadaDTO, String> columnEstado;
    
    private OrganizacionVinculadaDAOImpl organizacionVinculadaDAO;
    private ObservableList<OrganizacionVinculadaDTO> listaOrganizacionesVinculadas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        organizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        configurarColumnas();
        cargarOrganizacionesVinculadas();
    }
    
    private void configurarColumnas() {
        
        columnRFC.setCellValueFactory(new PropertyValueFactory<>("rfcMoral"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreOV"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoOV"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionOV"));
        columnEstado.setCellValueFactory(new PropertyValueFactory<>("estadoOV"));
    }
    
    private void cargarOrganizacionesVinculadas() {
        
        try {            
            
            List<OrganizacionVinculadaDTO> organizaciones = organizacionVinculadaDAO.listarOrganizacionesVinculadas();
            listaOrganizacionesVinculadas = FXCollections.observableArrayList(organizaciones);
            tableOrganizacionesVinculadas.setItems(listaOrganizacionesVinculadas);            
        } catch (SQLException sqle) {
            
            LOG.error("Error al cargar las organizaciones vinculadas: " + sqle.getMessage());
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, "No se pudo cargar la información, contacte al administrador: ", Alert.AlertType.ERROR);            
        } catch (IOException ioe){
            
            LOG.error("No se lograron cargar los registros" + ioe.getMessage());
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, "No se pudo cargar la información, contacte al adminsitrador", Alert.AlertType.ERROR);
        }
    }
        
    @FXML
    private void abrirRegistroOrganizacionVinculada(ActionEvent event) {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/organizacionvinculada/FXMLRegistroOrganizacionVinculada.fxml"));
            Parent root = loader.load();            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registro de Organizaciones");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarOrganizacionesVinculadas();
        } catch (IOException ioe) {
            
            LOG.error("Error al cargar la ventana de registro OV: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Ha ocurrido un error, intentelo más tarde", Alert.AlertType.ERROR);            
        }        
    }
    
    @FXML
    private void editarOrganizacionVinculada(ActionEvent event) {
        
        OrganizacionVinculadaDTO organizacionSeleccionada = tableOrganizacionesVinculadas.getSelectionModel().getSelectedItem();
        
        if (organizacionSeleccionada == null) {
            
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione una organización para editar", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/organizacionvinculada/FXMLRegistroOrganizacionVinculada.fxml"));
            Parent root = loader.load();
            
            CoordinadorRegistroOrganizacionVinculadaController controlador = loader.getController();
            
            controlador.cambiarAModoEdicion(true);
            controlador.llenarCamposEditablesOrganizacionVinculada(organizacionSeleccionada);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Organización");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarOrganizacionesVinculadas();
            
        } catch (IOException ioe) {
            
            LOG.error("Error al cargar la ventana de edición OV: " + ioe.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición, contacte con un administrador ", Alert.AlertType.ERROR);
        }
        
    }
    
    @FXML
    private void cambiarEstadoOrganizacionVinculada(ActionEvent event) {
        
        OrganizacionVinculadaDTO organizacionSeleccionada = tableOrganizacionesVinculadas.getSelectionModel().getSelectedItem();
        
        if (organizacionSeleccionada == null) {
            
            AlertaUtil.mostrarAlerta(ConstantesUtil.ADVERTENCIA, "Por favor, seleccione una organización para cambiar su estado", Alert.AlertType.WARNING);
            return;
        }
        
        String estadoActual = organizacionSeleccionada.getEstadoOV();
        
        String nuevoEstado = estadoActual.equalsIgnoreCase(EstadoOrganizacionVinculada.ACTIVO.name()) ? 
                EstadoOrganizacionVinculada.INACTIVO.name() : EstadoOrganizacionVinculada.ACTIVO.name();
        
        organizacionSeleccionada.setEstadoOV(nuevoEstado);
        
        try {
            
            boolean actualizacionExitosa = organizacionVinculadaDAO.editarOrganizacionVinculada(organizacionSeleccionada);
            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta(ConstantesUtil.EXITO, "Estado de la organización cambiado a: " + nuevoEstado, Alert.AlertType.INFORMATION);
                cargarOrganizacionesVinculadas();                
            } else {
                
                AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, "No se pudo cambiar el estado de la organización", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD + e.getMessage());
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException e){
            
            LOG.error("Error al cambiar estado de la organización: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Error al actualizar el registro", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salirAMenuPrincipal(ActionEvent event) {
        
        Stage ventanaActual = (Stage) botonSalir.getScene().getWindow();
        ventanaActual.close();
    }
    
}