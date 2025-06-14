package com.pdc.controlador.academicoevaluador;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.ProfesorEEDTO;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.ProfesorEEDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.validador.AcademicoValidador;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.IProfesorEEDAO;

public class RegistroAcademicosController {
    
    private static final Logger LOG = Logger.getLogger(RegistroAcademicosController.class);
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
    
    private IAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    
    private IProfesorEEDAO interfazProfesorEEDAO;
    
    private boolean modoEdicion;
    
    public void initialize() {
        
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
        interfazProfesorEEDAO = new ProfesorEEDAOImpl();
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
            AlertaUtil.mostrarAlerta(ConstantesUtil.EXITO, ConstantesUtil.ALERTA_REGISTRO_EXITOSO, Alert.AlertType.CONFIRMATION);
            limpiarCampos();
        } catch (SQLException sqle) {
            
            LOG.error (ConstantesUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.ALERTA_REGISTRO_FALLIDO, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_REGISTRO_FALLIDO, Alert.AlertType.ERROR);
        }
    }
    
    private void editarAcademico(UsuarioDTO academico){
        try {
            
            if(academico instanceof AcademicoEvaluadorDTO){
                
            interfazAcademicoEvaluadorDAO.editarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorEEDTO){
                
                interfazProfesorEEDAO.editarProfesorEE((ProfesorEEDTO) academico);
            }
            AlertaUtil.mostrarAlerta(ConstantesUtil.ACTUALIZAR, ConstantesUtil.ALERTA_ACTUALIZACION_EXITOSA, Alert.AlertType.INFORMATION);
            volverAGestionAcademico();
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD, sqle);
             AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ACTUALIZACION_FALLIDA, Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos(UsuarioDTO academico) {
               
        try {
            
            AcademicoValidador.validarAcademico(academico);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.LOG_DATOS_NO_VALIDOS, iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ALERTA_DATOS_INVALIDOS, iae.getMessage(), Alert.AlertType.WARNING);
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
    
    @FXML
    private void volverAGestionAcademico() {
        
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }

}
