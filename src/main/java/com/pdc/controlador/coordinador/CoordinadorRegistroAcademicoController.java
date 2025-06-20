package com.pdc.controlador.coordinador;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
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
import com.pdc.dao.implementacion.ProfesorExperienciaEducativaDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.validador.AcademicoValidador;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;

public class CoordinadorRegistroAcademicoController {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorRegistroAcademicoController.class);
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
    
    private IProfesorExperienciaEducativaDAO interfazProfesorEEDAO;
    
    private boolean modoEdicion;
    
    public void initialize() {
        
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
        interfazProfesorEEDAO = new ProfesorExperienciaEducativaDAOImpl();
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
        
        ProfesorExperienciaEducativaDTO profesorExperienciaEducativa = new ProfesorExperienciaEducativaDTO();
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
        }else if(academicoSeleccionado instanceof ProfesorExperienciaEducativaDTO){
            
            ProfesorExperienciaEducativaDTO profesor = (ProfesorExperienciaEducativaDTO) academicoSeleccionado;
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
            }else if(academico instanceof ProfesorExperienciaEducativaDTO){
                
                interfazProfesorEEDAO.insertarProfesorEE((ProfesorExperienciaEducativaDTO) academico);
            }
            AlertaUtil.mostrarAlertaRegistroExitoso();
            limpiarCampos();
        } catch (SQLException sqle) {
            
            LOG.error (AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(AlertaUtil.ALERTA_REGISTRO_FALLIDO, ioe);
            AlertaUtil.mostrarAlertaRegistroFallido();
        }
    }
    
    private void editarAcademico(UsuarioDTO academico){
        try {
            
            if(academico instanceof AcademicoEvaluadorDTO){
                
            interfazAcademicoEvaluadorDAO.editarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorExperienciaEducativaDTO){
                
                interfazProfesorEEDAO.editarProfesorEE((ProfesorExperienciaEducativaDTO) academico);
            }
            AlertaUtil.mostrarAlerta(ConstantesUtil.ACTUALIZAR, ConstantesUtil.ALERTA_ACTUALIZACION_EXITOSA, Alert.AlertType.INFORMATION);
            volverAGestionAcademico();
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(ioe);
            AlertaUtil.mostrarAlertaActualizacionFallida();
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
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ACADEMICO);
    }

}
