package grafica.controladores;

import accesoadatos.dto.ProyectoDTO;
import accesoadatos.dto.ProyectoDTO.EstadoProyecto;
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
import grafica.validadores.ProyectoValidador;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import logica.dao.ProyectoDAO;
import logica.interfaces.InterfazProyectoDAO;

public class RegistroProyectoController {    
    
    private static final Logger LOG = Logger.getLogger(RegistroOrganizacionVinculadaController.class);
    
    @FXML
    private TextField textProyectoID;
    
    @FXML
    private TextField textTituloProyecto;
    
    @FXML
    private TextField textPeriodoEscolar;
    
    @FXML
    private TextArea textDescripcionProyecto;
    
    @FXML
    private ComboBox comboOrganizacionVinculada;
      
            
    @FXML 
    private Button botonCancelarRegistroOV;
    
    @FXML
    private Button botonRegistrarOrganizacionVinculada;
    
    private InterfazProyectoDAO interfazProyectoDAO;
    private ProyectoDTO proyectoDTO;
    
    private boolean modoEdicion = false;
    
    public void initialize() {
        
        interfazProyectoDAO = new ProyectoDAO();
    }
    
    public void cambiarAModoEdicion(boolean modoEdicion) {
        
        textProyectoID.setDisable(true);        
        this.modoEdicion = modoEdicion;
        
        if (modoEdicion) {
            
            botonRegistrarOrganizacionVinculada.setText("Actualizar");
        }
    }
    
    public void llenarCamposEditablesProyecto(ProyectoDTO proyectoDTO) {
        
        this.proyectoDTO = proyectoDTO;
        
        textTituloProyecto.setText(proyectoDTO.getTituloProyecto());
        textDescripcionProyecto.setText(proyectoDTO.getDescripcionProyecto());
        comboOrganizacionVinculada.set(proyectoDTO.getRfcMoral());
        text.setText(proyectoDTO.getDireccionOV());
        
    }
    
    @FXML
    private void registrarOrganizacionVinculada(ActionEvent evento) {
        
        if(!modoEdicion){
            
            proyectoDTO = new ProyectoDTO();
        }
        
        proyectoDTO.setTituloProyecto(textTituloProyecto.getText().trim());
        proyectoDTO.setNombreOV(textNombreOV.getText().trim());
        proyectoDTO.setTelefonoOV(textTelefonoOV.getText().trim());
        proyectoDTO.setDireccionOV(textDireccionOV.getText().trim());
        
        if (!validarCampos(proyectoDTO)) {
            
            return;
        }        
        if (modoEdicion) {
            
            actualizarOrganizacionVinculada(proyectoDTO);
        } else {
            
            crearNuevaOrganizacionVinculada(proyectoDTO);
        }
    }
    
    private boolean validarCampos(ProyectoDTO proyectoDTO) {
               
        try {
            
            ProyectoValidador.validarOrganizacionVinculada(proyectoDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error ("Se ingresaron datos inválidos");
            AlertaUtil.mostrarAlerta("Datos Inválidos o Incompletos", iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void crearNuevaOrganizacionVinculada(ProyectoDTO proyectoDTO) {
        
        proyectoDTO.setEstadoProyecto(EstadoProyecto.ACTIVO.name());                
        try {
            
            interfazProyectoDAO.insertarProyecto(proyectoDTO);
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
    
    private void actualizarOrganizacionVinculada(ProyectoDTO organizacionVinculadaDTO) {
                
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
        
        text.setText("");
        textNombreOV.setText("");
        textTelefonoOV.setText("");
        textDireccionOV.setText("");
    }
        
}