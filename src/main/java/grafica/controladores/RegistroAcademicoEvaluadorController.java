package grafica.controladores;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logica.dao.AcademicoEvaluadorDAO;
import logica.interfaces.InterfazAcademicoEvaluadorDAO;
import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;
import grafica.validadores.AcademicoValidador;

public class RegistroAcademicoEvaluadorController {
    
    private static final Logger LOG = Logger.getLogger(RegistroAcademicoEvaluadorController.class);

    @FXML
    private Button botonCancelar, botonGuardar;

    @FXML
    private TextField txtNombreDelTrabajador, txtNumeroDeTrabajador;
    
    private InterfazAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    
    private boolean modoEdicion;
    
    public void initialize() {
        
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAO();
    }

    @FXML
    private void guardarDatosAcademico(ActionEvent event) {
        
        AcademicoEvaluadorDTO academicoEvaluadorDTO = new AcademicoEvaluadorDTO();
        academicoEvaluadorDTO.setNumeroDeTrabajador(txtNumeroDeTrabajador.getText().trim());
        academicoEvaluadorDTO.setNombreAcademico(txtNombreDelTrabajador.getText().trim());
        
        if(!validarCampos(academicoEvaluadorDTO)){
            
            return;
        }
        if(!modoEdicion){
            
            crearAcademicoEvaluador(academicoEvaluadorDTO);
        }else{
            
            editarAcademicoEvaluador(academicoEvaluadorDTO);
        }
    }

    @FXML
    private void volverAGestionAcademico(ActionEvent event) {
        
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void  llenarCamposEditablesAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluadorSeleccionado){
        
        AcademicoEvaluadorDTO academicoEvaluadorDTO = academicoEvaluadorSeleccionado;
        txtNumeroDeTrabajador.setText(academicoEvaluadorDTO.getNumeroDeTrabajador());
        txtNombreDelTrabajador.setText(academicoEvaluadorDTO.getNombreAcademico());
        
        txtNumeroDeTrabajador.setDisable(modoEdicion);
    }
    
    public void cambiarAModoEdicion(Boolean modoEdicion){
        
        this.modoEdicion = modoEdicion;        
    }
    
    private void crearAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluadorDTO){
        
        try {
            
            interfazAcademicoEvaluadorDAO.insertarAcademicoEvaluador(academicoEvaluadorDTO);
            AlertaUtil.mostrarAlerta("Error", "Académico registrado.", Alert.AlertType.CONFIRMATION);
            limpiarCampos();
        } catch (SQLException sqle) {
            
            LOG.error(sqle);
            AlertaUtil.mostrarAlerta("Error", "Error al guardar la información.", Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ioe);
            AlertaUtil.mostrarAlerta("Error", "Error al guardar la información.", Alert.AlertType.ERROR);
        }
    }
    
    private void editarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluadorDTO){
        
        try {
            
            interfazAcademicoEvaluadorDAO.editarAcademicoEvaluador(academicoEvaluadorDTO);
            AlertaUtil.mostrarAlerta("Error", "Académico actualizado.", Alert.AlertType.CONFIRMATION);
            cerrarVentana();            
        } catch (SQLException sqle) {
            
            LOG.error(sqle);
             AlertaUtil.mostrarAlerta("Error", "No hay conexion con la base de datos", Alert.AlertType.ERROR);             
        } catch (IOException ioe) {
            
            LOG.error(ioe);
            AlertaUtil.mostrarAlerta("Error", "Error al editar los datos.", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos(AcademicoEvaluadorDTO academicoEvaluadorDTO) {
               
        try {
            
            AcademicoValidador.validarAcademico(academicoEvaluadorDTO);
            return true;            
        } catch (IllegalArgumentException iae) {
            
            LOG.error ("Se ingresaron datos inválidos");
            AlertaUtil.mostrarAlerta("Datos Inválidos o Incompletos", iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void limpiarCampos(){
        
        txtNumeroDeTrabajador.setText("");
        txtNombreDelTrabajador.setText("");
    }
    
    private void cerrarVentana(){
        
        Stage stage = (Stage) botonGuardar.getScene().getWindow();
        stage.close();
    }

}
