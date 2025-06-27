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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.ExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ProfesorExperienciaEducativaDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.validador.AcademicoValidador;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import com.pdc.dao.interfaz.IExperienciaEducativaDAO;

public class CoordinadorRegistroAcademicoController {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorRegistroAcademicoController.class);
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    
    @FXML
    private TextField textNombreDelTrabajador;

    @FXML
    private TextField textNumeroDeTrabajador;
    
    @FXML
    private TextField textSeccion;
    
    @FXML
    private Label labelSeccion;
    
    @FXML
    private Label labelExperienciaEducativa;
    
    @FXML
    private ComboBox<ExperienciaEducativaDTO> comboExperienciaEducativa;
    
    
    private TipoUsuarioDTO tipoUsuario;
    private IAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private IProfesorExperienciaEducativaDAO interfazProfesorEEDAO;
    private IExperienciaEducativaDAO interfazExperienciaEducativaDAO;
    private boolean modoEdicion;
    
    public void initialize() {
        
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
        interfazProfesorEEDAO = new ProfesorExperienciaEducativaDAOImpl();
        interfazExperienciaEducativaDAO = new ExperienciaEducativaDAOImpl();
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
        academicoEvaluador.setNumeroDeTrabajador(textNumeroDeTrabajador.getText().trim());
        academicoEvaluador.setNombreAcademico(textNombreDelTrabajador.getText().trim());
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
        profesorExperienciaEducativa.setNumeroTrabajador(textNumeroDeTrabajador.getText().trim());
        profesorExperienciaEducativa.setNombreProfesor(textNombreDelTrabajador.getText().trim());
        profesorExperienciaEducativa.setSeccion(textSeccion.getText().trim());
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
                
            AcademicoEvaluadorDTO academicoEvaluador;
            academicoEvaluador = (AcademicoEvaluadorDTO) academicoSeleccionado;
            textNumeroDeTrabajador.setText(academicoEvaluador.getNumeroDeTrabajador());
            textNombreDelTrabajador.setText(academicoEvaluador.getNombreAcademico());
            
        }else if(academicoSeleccionado instanceof ProfesorExperienciaEducativaDTO){
            
            ProfesorExperienciaEducativaDTO profesor;
            profesor = (ProfesorExperienciaEducativaDTO) academicoSeleccionado;
            textNumeroDeTrabajador.setText(profesor.getNumeroTrabajador());
            textNombreDelTrabajador.setText(profesor.getNombreProfesor());
            textSeccion.setText(profesor.getSeccion());
            try {
                
            ExperienciaEducativaDTO experienciaEducativa;
            experienciaEducativa = interfazExperienciaEducativaDAO.buscarExperienciaEducativaPorProfesor(profesor.getNumeroTrabajador());
            comboExperienciaEducativa.setValue(experienciaEducativa);
            }catch(SQLException sqle){
                
                LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
                AlertaUtil.mostrarAlertaBaseDatos();
            }
        }
        textNumeroDeTrabajador.setDisable(modoEdicion);
    }
    
    private void crearAcademico(UsuarioDTO academico){
        
        try {
            
            if(academico instanceof AcademicoEvaluadorDTO){
                
                interfazAcademicoEvaluadorDAO.insertarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorExperienciaEducativaDTO){
                
                ExperienciaEducativaDTO experienciaEducativa;
                experienciaEducativa = comboExperienciaEducativa.getSelectionModel().getSelectedItem();
                
                String nrc;
                nrc= experienciaEducativa.getNrc();
                String numeroDeTrabajador;
                numeroDeTrabajador = ((ProfesorExperienciaEducativaDTO) academico).getNumeroTrabajador();
                
                interfazProfesorEEDAO.insertarProfesorEE((ProfesorExperienciaEducativaDTO) academico);
                interfazExperienciaEducativaDAO.asignarProfesorAExperienciaEducativa(nrc, numeroDeTrabajador);
            }
            AlertaUtil.mostrarAlertaRegistroExitoso();
            volverAGestionAcademico();
        } catch (SQLException sqle) {
            
            LOG.error (AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }
    }
    
    private void editarAcademico(UsuarioDTO academico){
        
        try {
            
            if(academico instanceof AcademicoEvaluadorDTO){
                
                interfazAcademicoEvaluadorDAO.editarAcademicoEvaluador((AcademicoEvaluadorDTO) academico);
            }else if(academico instanceof ProfesorExperienciaEducativaDTO){
                
             editarProfesorExperienciaEducativa((ProfesorExperienciaEducativaDTO) academico);
            
            }
            AlertaUtil.mostrarAlertaActualizacionExitosa();
            volverAGestionAcademico();
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ACTUALIZACION_FALLIDA, ioe);
            AlertaUtil.mostrarAlertaActualizacionFallida();
        } catch (IllegalStateException ise){
            
            LOG.error(ConstantesUtil.LOG_ERROR_ESTADO_INESPERADO,ise);
            AlertaUtil.mostrarAlertaErrorInesperado();
        }
    }
    
    private boolean validarCampos(UsuarioDTO academico) {
               
        try {
            
            AcademicoValidador.validarAcademico(academico);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.LOG_DATOS_NO_VALIDOS, iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ALERTA_DATOS_NO_VALIDOS, iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    public void cambiarAModoEdicion(Boolean modoEdicion){
        
        this.modoEdicion = modoEdicion;
    }

    public void asignarTipoUsuario(TipoUsuarioDTO tipoUsuario){
        
        this.tipoUsuario = tipoUsuario;
        
        if(ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())){
            
            textSeccion.setVisible(Boolean.FALSE);
            labelSeccion.setVisible(Boolean.FALSE);
            labelExperienciaEducativa.setVisible(Boolean.FALSE);
            comboExperienciaEducativa.setVisible(Boolean.FALSE);
        }else if (PROFESOR_EE.equals(tipoUsuario.getIdTipo())) {
            
            llenarComboExperienciaEducativa();
        }
    }
    
    private void llenarComboExperienciaEducativa(){
        
        try{
            
            List<ExperienciaEducativaDTO> experienciasDisponibles;
            experienciasDisponibles = interfazExperienciaEducativaDAO.listarExperienciaEducativa()
            .stream()
            .filter(experienciaEducativa -> experienciaEducativa.getProfesor() == null)
            .collect(Collectors.toList());
            
            comboExperienciaEducativa.setItems(FXCollections.observableArrayList(experienciasDisponibles));
        }catch(SQLException sqle){
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }
    }
    
    private Optional<String> obtenerNrcAnterior(String numeroDeTrabajador) throws SQLException, IOException {
        
        ExperienciaEducativaDTO experienciaAnterior;
        experienciaAnterior = interfazExperienciaEducativaDAO.buscarExperienciaEducativaPorProfesor(numeroDeTrabajador);
        return Optional.ofNullable(experienciaAnterior)
                       .map(ExperienciaEducativaDTO::getNrc);
    }
    
    
    private void editarProfesorExperienciaEducativa(ProfesorExperienciaEducativaDTO profesor) throws SQLException, IOException {
    
        String numeroDeTrabajador = profesor.getNumeroTrabajador();
        String nrcNuevo = comboExperienciaEducativa.getSelectionModel().getSelectedItem().getNrc();

        String nrcAnterior = obtenerNrcAnterior(numeroDeTrabajador)
            .orElseThrow(() -> new IllegalStateException(
                "Error del sistema: No se encontró experiencia educativa anterior para el profesor " + numeroDeTrabajador));

        interfazProfesorEEDAO.editarProfesorEE(profesor);
        interfazExperienciaEducativaDAO.reasignarProfesorExperienciaEducativa(nrcAnterior, nrcNuevo, numeroDeTrabajador);

    }

    @FXML
    private void volverAGestionAcademico() {
        
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ACADEMICO);
    }

}
