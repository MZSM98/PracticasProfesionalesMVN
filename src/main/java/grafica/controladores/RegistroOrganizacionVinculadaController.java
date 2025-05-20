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
            
            botonRegistrarOrganizacionVinculada.setText("Actualizar");
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
        organizacionVinculadaDTO.setNombreOV(textNombreOV.getText().trim());
        organizacionVinculadaDTO.setTelefonoOV(textTelefonoOV.getText().trim());
        organizacionVinculadaDTO.setDireccionOV(textDireccionOV.getText().trim());
        
        if (!validarCampos(organizacionVinculadaDTO)) {
            
            return;
        }        
        if (modoEdicion) {
            
            actualizarOrganizacionVinculada(organizacionVinculadaDTO);
        } else {
            
            crearNuevaOrganizacionVinculada(organizacionVinculadaDTO);
        }
    }
    
    private boolean validarCampos(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
               
        try {
            
            OrganizacionVinculadaValidador.validarOrganizacionVinculada(organizacionVinculadaDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error ("Se ingresaron datos inválidos");
            AlertaUtil.mostrarAlerta("Datos Inválidos o Incompletos", iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void crearNuevaOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        organizacionVinculadaDTO.setEstadoOV(EstadoOrganizacionVinculada.ACTIVO.name());                
        try {
            
            organizacionVinculadaDAO.insertarOrganizacionVinculada(organizacionVinculadaDTO);
            AlertaUtil.mostrarAlerta("Éxito", "Organización Registrada", Alert.AlertType.INFORMATION);
            limpiarCampos();
            
        } catch(SQLIntegrityConstraintViolationException icve){
            
            LOG.error(icve);
            AlertaUtil.mostrarAlerta("Error", "La Organización que está tratando de registrar ya existe", Alert.AlertType.WARNING);
            
        } catch (SQLException e) {
            
            LOG.error("Error con la conexion de base de datos", e);
            AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos: ", Alert.AlertType.ERROR);
            
        } catch (IOException e) {
            
            LOG.error("Error al registrar la organización", e);
            AlertaUtil.mostrarAlerta("Error", "Error al registrar la organización: ", Alert.AlertType.ERROR);
        } 
    }
    
    private void actualizarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
                
        try {
            
            boolean actualizacionExitosa = organizacionVinculadaDAO.editarOrganizacionVinculada(organizacionVinculadaDTO);            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta("Éxito", "Organización actualizada correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                
                AlertaUtil.mostrarAlerta("Error", "No se pudo actualizar la organización", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException e) {
            
            LOG.error("Error con la conexión de base de datos", e);
            AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            
            LOG.error("Error al actualizar la organización", e);
            AlertaUtil.mostrarAlerta("Error", "Error al actualizar la organización: " + e.getMessage(), Alert.AlertType.ERROR);
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