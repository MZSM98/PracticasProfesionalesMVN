package grafica.controladores;

import logica.dao.OrganizacionVinculadaDAO;
import accesoadatos.dto.OrganizacionVinculadaDTO;
import accesoadatos.dto.OrganizacionVinculadaDTO.EstadoOrganizacionVinculada;
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
import grafica.validadores.OrganizacionVinculadaValidador;
import grafica.utils.AlertaUtil;
import grafica.utils.ConstantesUtil;
import logica.interfaces.InterfazOrganizacionVinculadaDAO;

public class RegistroOrganizacionVinculadaController {    
    
    private static final Logger LOG = Logger.getLogger(RegistroOrganizacionVinculadaController.class);
    
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
    
    private InterfazOrganizacionVinculadaDAO organizacionVinculadaDAO;
    private OrganizacionVinculadaDTO organizacionVinculadaDTO;
    
    private boolean modoEdicion = false;
    
    public void initialize() {
        
        organizacionVinculadaDAO = new OrganizacionVinculadaDAO();
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
        organizacionVinculadaDTO.setNombreOV(textNombreOV.getText().replaceAll(ConstantesUtil.REGEX_ESPACIOS_MULTIPLES,ConstantesUtil.REGEX_ESPACIO).trim());
        organizacionVinculadaDTO.setTelefonoOV(textTelefonoOV.getText().trim());
        organizacionVinculadaDTO.setDireccionOV(textDireccionOV.getText().replaceAll(ConstantesUtil.REGEX_ESPACIOS_MULTIPLES,ConstantesUtil.REGEX_ESPACIO).trim());
        
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
        
        Stage stage = (Stage) botonCancelarRegistroOV.getScene().getWindow();
        stage.close();
    }    
    
    private void limpiarCampos() {
        
        textRfcOV.setText("");
        textNombreOV.setText("");
        textTelefonoOV.setText("");
        textDireccionOV.setText("");
    }
        
}