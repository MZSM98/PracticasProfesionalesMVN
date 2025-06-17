package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO.EstadoOrganizacionVinculada;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import com.pdc.validador.OrganizacionVinculadaValidador;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.RestriccionCamposUtil;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.utileria.manejador.ManejadorDeVistas;

public class CoordinadorRegistroOrganizacionVinculadaController {    
    
    private static final Logger LOG = Logger.getLogger(CoordinadorRegistroOrganizacionVinculadaController.class);
    
    @FXML
    private TextField textRfcOV;
    
    @FXML
    private TextField textNombreOV;
    
    @FXML
    private TextField textTelefonoOV;
    
    @FXML
    private TextField textDireccionOV;
            
    @FXML 
    private Button botonCancelarRegistroOV;
    
    @FXML
    private Button botonRegistrarOrganizacionVinculada;
    
    private IOrganizacionVinculadaDAO organizacionVinculadaDAO;
    private OrganizacionVinculadaDTO organizacionVinculadaDTO;
    
    private boolean modoEdicion = false;
    
    public void initialize() {
        
        organizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        aplicarRestriccionesACampos();
    }
    
    public void cambiarAModoEdicion(boolean modoEdicion) {
        
        this.modoEdicion = modoEdicion;
        if (modoEdicion) {
            
            botonRegistrarOrganizacionVinculada.setText(ConstantesUtil.ACTUALIZAR);
        }
    }
    
    public void llenarCamposEditablesOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        this.organizacionVinculadaDTO = organizacionVinculadaDTO;
        
        textRfcOV.setText(organizacionVinculadaDTO.getRfcMoral());
        textNombreOV.setText(organizacionVinculadaDTO.getNombreOV());
        textTelefonoOV.setText(organizacionVinculadaDTO.getTelefonoOV());
        textDireccionOV.setText(organizacionVinculadaDTO.getDireccionOV());
        
        textRfcOV.setDisable(modoEdicion);
    }
    
    @FXML
    private void registrarOrganizacionVinculada(ActionEvent evento) {
        
        if(!modoEdicion){
            
            organizacionVinculadaDTO = new OrganizacionVinculadaDTO();
        }
        
        organizacionVinculadaDTO.setRfcMoral(textRfcOV.getText().trim().toUpperCase());
        organizacionVinculadaDTO.setNombreOV(textNombreOV.getText().replaceAll(ConstantesUtil.REGEX_ESPACIOS_MULTIPLES,ConstantesUtil.ESPACIO).trim());
        organizacionVinculadaDTO.setTelefonoOV(textTelefonoOV.getText().trim());
        organizacionVinculadaDTO.setDireccionOV(textDireccionOV.getText().replaceAll(ConstantesUtil.REGEX_ESPACIOS_MULTIPLES,ConstantesUtil.ESPACIO).trim());
        
        if (!validarCamposOrganizacionVinculada(organizacionVinculadaDTO)) {
            
            return;
        }        
        if (modoEdicion) {
            
            actualizarOrganizacionVinculada(organizacionVinculadaDTO);
        } else {
            
            crearNuevaOrganizacionVinculada(organizacionVinculadaDTO);
        }
    }
    
    private boolean validarCamposOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
               
        try {
            
            OrganizacionVinculadaValidador.validarOrganizacionVinculada(organizacionVinculadaDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.ALERTA_DATOS_INVALIDOS, iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ADVERTENCIA, iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void crearNuevaOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        organizacionVinculadaDTO.setEstadoOV(EstadoOrganizacionVinculada.ACTIVO.name());
        
        try {
            
            organizacionVinculadaDAO.insertarOrganizacionVinculada(organizacionVinculadaDTO);
            AlertaUtil.mostrarAlerta(ConstantesUtil.EXITO, ConstantesUtil.ALERTA_REGISTRO_EXITOSO, Alert.AlertType.INFORMATION);
            limpiarCampos();            
        } catch(SQLIntegrityConstraintViolationException icve){
            
            LOG.error(ConstantesUtil.LOG_ERROR_REGISTRO_DUPLICADO,icve);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_REGISTRO_RFC_MORAL_DUPLICADO, Alert.AlertType.WARNING);
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.ALERTA_REGISTRO_FALLIDO, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_REGISTRO_FALLIDO, Alert.AlertType.ERROR);
        } 
    }
    
    private void actualizarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
                
        try {
            
            boolean actualizacionExitosa = organizacionVinculadaDAO.editarOrganizacionVinculada(organizacionVinculadaDTO);            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta(ConstantesUtil.EXITO, ConstantesUtil.ALERTA_ACTUALIZACION_EXITOSA, Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                
                AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ACTUALIZACION_FALLIDA, Alert.AlertType.ERROR);
            }
            
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ACTUALIZACION_FALLIDA, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ACTUALIZACION_FALLIDA, Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelarRegistroOrganizacionVinculada(ActionEvent evento) {
        
        cerrarVentana();
    }    
    
    private void cerrarVentana() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ORGANIZACION_VINCULADA);
    }    
    
    private void limpiarCampos() {
        
        textRfcOV.setText("");
        textNombreOV.setText("");
        textTelefonoOV.setText("");
        textDireccionOV.setText("");
    }
    
    private void aplicarRestriccionesACampos(){
        
        RestriccionCamposUtil.aplicarRestriccionLongitud(textRfcOV, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textNombreOV, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textTelefonoOV, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textDireccionOV, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
    }
        
}