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
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO.EstadoOrganizacionVinculada;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;

import java.net.URL;
import java.util.ResourceBundle;

public class CoordinadorGestionOrganizacionVinculadaController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionOrganizacionVinculadaController.class);
    
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
        
        ObservableList<OrganizacionVinculadaDTO> listaOrganizacionesVinculadas;
        
        try {            
            
            List<OrganizacionVinculadaDTO> organizaciones = organizacionVinculadaDAO.listarOrganizacionesVinculadas();
            listaOrganizacionesVinculadas = FXCollections.observableArrayList(organizaciones);
            tableOrganizacionesVinculadas.setItems(listaOrganizacionesVinculadas);            
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        } catch (IOException ioe){
            
            LOG.error(ConstantesUtil.LOG_ERROR_CARGAR_INFORMACION, ioe);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
        
    @FXML
    private void abrirRegistroOrganizacionVinculada(ActionEvent event) {
        
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA);
    }
    
    @FXML
    private void editarOrganizacionVinculada(ActionEvent event) {
        
        OrganizacionVinculadaDTO organizacionSeleccionada = tableOrganizacionesVinculadas.getSelectionModel().getSelectedItem();
        
        if (organizacionSeleccionada == null) {
            
            AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, ConstantesUtil.ALERTA_SELECCION_EDITAR, Alert.AlertType.WARNING);
            return;
        }
        
        try {
            
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA);
            CoordinadorRegistroOrganizacionVinculadaController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA);
            controlador.cambiarAModoEdicion(true);
            controlador.llenarCamposEditablesOrganizacionVinculada(organizacionSeleccionada);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
        
    }
    
    @FXML
    private void cambiarEstadoOrganizacionVinculada(ActionEvent event) {
        
        OrganizacionVinculadaDTO organizacionSeleccionada;
        organizacionSeleccionada = tableOrganizacionesVinculadas.getSelectionModel().getSelectedItem();
        
        if (organizacionSeleccionada == null) {
            
            AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, "Por favor, seleccione una organización para cambiar su estado", Alert.AlertType.WARNING);
            return;
        }
        
        String estadoActual = organizacionSeleccionada.getEstadoOV();
        
        String nuevoEstado = estadoActual.equalsIgnoreCase(EstadoOrganizacionVinculada.ACTIVO.name()) ? 
                EstadoOrganizacionVinculada.INACTIVO.name() : EstadoOrganizacionVinculada.ACTIVO.name();
        
        organizacionSeleccionada.setEstadoOV(nuevoEstado);
        
        try {
            
            boolean actualizacionExitosa = organizacionVinculadaDAO.editarOrganizacionVinculada(organizacionSeleccionada);
            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta(AlertaUtil.EXITO, "Estado de la organización cambiado a: " + nuevoEstado, Alert.AlertType.INFORMATION);
                cargarOrganizacionesVinculadas();                
            } else {
                
                AlertaUtil.mostrarAlerta(AlertaUtil.ERROR, "No se pudo cambiar el estado de la organización", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD + e.getMessage());
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException e){
            
            LOG.error("Error al cambiar estado de la organización: " + e.getMessage());
            AlertaUtil.mostrarAlertaActualizacionFallida();
        }
    }
    
    @FXML
    private void abrirListaDeResponsables (ActionEvent event){
        
        OrganizacionVinculadaDTO organizacionSeleccionada = tableOrganizacionesVinculadas.getSelectionModel().getSelectedItem();

        if (organizacionSeleccionada == null){
            
            AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, ConstantesUtil.ALERTA_SELECCION_ORGANIZACION_VINCULADA, Alert.AlertType.WARNING);
            return;
        }
        try {
            
        CoordinadorGestionResponsableOrganizacionController controlador;
        controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_GESTION_RESPONSABLE_ORGANIZACION);
        controlador.cargarListaResponsablesOrganizacion(organizacionSeleccionada);
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_RESPONSABLE_ORGANIZACION);
        } catch (IOException ioe){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }
    
    @FXML
    private void salirAMenuPrincipal(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
    }
}