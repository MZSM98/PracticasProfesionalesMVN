package grafica.controladores;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.dto.ProfesorEEDTO;
import accesoadatos.dto.TipoUsuarioDTO;
import accesoadatos.dto.UsuarioDTO;
import grafica.utils.AlertaUtil;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logica.dao.AcademicoEvaluadorDAO;
import logica.dao.ProfesorEEDAO;
import logica.interfaces.InterfazAcademicoEvaluadorDAO;
import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;
import logica.funcionalidades.AcademicoValidador;
import logica.interfaces.InterfazProfesorEEDAO;

public class RegistroAcademicoEvaluadorController {
    
    private static final Logger LOG = Logger.getLogger(RegistroAcademicoEvaluadorController.class);
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    
    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private TextField txtNombreDelTrabajador;

    @FXML
    private TextField txtNumeroDeTrabajador;
    
    @FXML
    private TextField txtSeccion;
    
    @FXML
    private Label lblSeccion;
    
    private TipoUsuarioDTO tipoUsuario;
    
    private InterfazAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    
    private InterfazProfesorEEDAO interfazProfesorEEDAO;
    
    private boolean modoEdicion;
    
    public void initialize() {
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAO();
        interfazProfesorEEDAO = new ProfesorEEDAO();
    }

    @FXML
    private void guardarDatosAcademico(ActionEvent event) {
        if(ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())){
            guardarDatosAcademicoEvaluador();
        }else if(PROFESOR_EE.equals(tipoUsuario.getIdTipo())){
            guardarDatosProfesorExperienciaEducativa();
        }
    }
    
    private void guardarDatosAcademicoEvaluador(){
        AcademicoEvaluadorDTO academicoEvaluador = new AcademicoEvaluadorDTO();
        academicoEvaluador.setNumeroDeTrabajador(txtNumeroDeTrabajador.getText().trim());
        academicoEvaluador.setNombreAcademico(txtNombreDelTrabajador.getText().trim());
        academicoEvaluador.setTipoUsuario(tipoUsuario);
        
        if(!validarCampos(academicoEvaluador)){
            return;
        }
        
        if(!modoEdicion){
            crearAcademico(academicoEvaluador);
        }else{
            editarAcademico(academicoEvaluador);
        }
    }
    
    private void guardarDatosProfesorExperienciaEducativa(){
        ProfesorEEDTO profesorExperienciaEducativa = new ProfesorEEDTO();
        profesorExperienciaEducativa.setNumeroTrabajador(txtNumeroDeTrabajador.getText().trim());
        profesorExperienciaEducativa.setNombreProfesor(txtNombreDelTrabajador.getText().trim());
        profesorExperienciaEducativa.setSeccion(txtSeccion.getText().trim());
        profesorExperienciaEducativa.setTipoUsuario(tipoUsuario);
        
        if(!validarCampos(profesorExperienciaEducativa)){
            return;
        }
        
        if(!modoEdicion){
            crearAcademico(profesorExperienciaEducativa);
        }else{
            editarAcademico(profesorExperienciaEducativa);
        }
    }

    @FXML
    private void volverAGestionAcademico(ActionEvent event) {
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void  llenarCamposEditablesAcademico(UsuarioDTO academicoSeleccionado){
        
        if(academicoSeleccionado instanceof AcademicoEvaluadorDTO){
            AcademicoEvaluadorDTO academicoEvaluador = (AcademicoEvaluadorDTO) academicoSeleccionado;
            txtNumeroDeTrabajador.setText(academicoEvaluador.getNumeroDeTrabajador());
            txtNombreDelTrabajador.setText(academicoEvaluador.getNombreAcademico());
        }else if(academicoSeleccionado instanceof ProfesorEEDTO){
            ProfesorEEDTO profesor = (ProfesorEEDTO) academicoSeleccionado;
            txtNumeroDeTrabajador.setText(profesor.getNumeroTrabajador());
            txtNombreDelTrabajador.setText(profesor.getNombreProfesor());
            txtSeccion.setText(profesor.getSeccion());
        }
        txtNumeroDeTrabajador.setDisable(modoEdicion);

    }
    
    private void crearAcademico(UsuarioDTO academico){
        try {
            if(academico instanceof AcademicoEvaluadorDTO){
                interfazAcademicoEvaluadorDAO.insertarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorEEDTO){
                interfazProfesorEEDAO.insertarProfesorEE((ProfesorEEDTO) academico);
            }
            AlertaUtil.mostrarAlerta("Error", "Académico registrado.", Alert.AlertType.CONFIRMATION);
            limpiarCampos();
        } catch (SQLException ex) {
            LOG.error(ex);
            AlertaUtil.mostrarAlerta("Error", "Error al guardar la información.", Alert.AlertType.ERROR);
        } catch (IOException ex) {
            LOG.error(ex);
            AlertaUtil.mostrarAlerta("Error", "Error al guardar la información.", Alert.AlertType.ERROR);
        }
    }
    
    private void editarAcademico(UsuarioDTO academico){
        try {
            if(academico instanceof AcademicoEvaluadorDTO){
            interfazAcademicoEvaluadorDAO.editarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorEEDTO){
                interfazProfesorEEDAO.editarProfesorEE((ProfesorEEDTO) academico);
            }
            AlertaUtil.mostrarAlerta("Error", "Académico actualizado.", Alert.AlertType.CONFIRMATION);
            cerrarVentana();
        } catch (SQLException ex) {
            LOG.error(ex);
             AlertaUtil.mostrarAlerta("Error", "Error al editar los datos.", Alert.AlertType.ERROR);
        } catch (IOException ex) {
            LOG.error(ex);
            AlertaUtil.mostrarAlerta("Error", "Error al editar los datos.", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos(UsuarioDTO academico) {
               
        try {
            AcademicoValidador.validarAcademico(academico);
            return true;
        } catch (IllegalArgumentException iae) {
            LOG.error ("Se ingresaron datos inválidos");
            AlertaUtil.mostrarAlerta("Datos Inválidos o Incompletos", iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    public void cambiarAModoEdicion(Boolean modoEdicion){
        this.modoEdicion = modoEdicion;
    }

    public void asignarTipoUsuario(TipoUsuarioDTO tipoUsuario){
        this.tipoUsuario = tipoUsuario;
        if(ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())){
            txtSeccion.setVisible(Boolean.FALSE);
            lblSeccion.setVisible(Boolean.FALSE);
        }
    }
    
    private void limpiarCampos(){
        txtNumeroDeTrabajador.setText("");
        txtNombreDelTrabajador.setText("");
        txtSeccion.setText("");
    }
    
    private void cerrarVentana(){
        Stage stage = (Stage) botonGuardar.getScene().getWindow();
        stage.close();
    }

}
