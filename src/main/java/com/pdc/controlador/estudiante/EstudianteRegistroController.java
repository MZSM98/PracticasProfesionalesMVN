package com.pdc.controlador.estudiante;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.modelo.dto.SeccionDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.validador.EstudianteValidador;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.EstudianteExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.PeriodoEscolarDAOImpl;
import com.pdc.dao.implementacion.SeccionDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.dao.interfaz.ISeccionDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativa;
import com.pdc.dao.interfaz.IExperienciaEducativa;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.utileria.manejador.ManejadorDeVistas;

public class EstudianteRegistroController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(EstudianteRegistroController.class);
    @FXML
    private Button botonCancelar;
    
    @FXML
    private TextField textMatricula;

    @FXML
    private TextField textNombreEstudiante;

    @FXML
    private ComboBox<PeriodoEscolarDTO> comboPeriodoEscolar;

    @FXML
    private ComboBox<SeccionDTO> comboSeccionEstudiante;
    
    @FXML
    private ComboBox<ExperienciaEducativaDTO> comboExperienciaEducativa;
    @FXML
    private TextField textPromedio;

    @FXML
    private TextField textAvanceCrediticio;
    
    private boolean modoEdicion;
    
    private IEstudianteDAO interfazEstudianteDAO;
    private IPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    private ISeccionDAO interfazSeccionDAO;
    private IExperienciaEducativa interfazExperienciaEducativaDAO;
    private IEstudianteExperienciaEducativa interfazEstudianteExperienciaEducativaDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAOImpl();
        interfazSeccionDAO = new SeccionDAOImpl();
        interfazExperienciaEducativaDAO = new ExperienciaEducativaDAOImpl();
        interfazEstudianteExperienciaEducativaDAO = new EstudianteExperienciaEducativaDAOImpl();
        poblarInformacionCombos();
    }    

    @FXML
    void cancelarRegistroEstudiante(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION);
    }

    @FXML
    void registrarEstudiante(ActionEvent event) {
        
        try {
            EstudianteDTO estudiante = new EstudianteDTO();
            estudiante.setMatricula(textMatricula.getText().trim());
            estudiante.setNombreEstudiante(textNombreEstudiante.getText().trim());
            estudiante.setPeriodoEscolar(comboPeriodoEscolar.getValue());
            estudiante.setSeccionEstudiante(comboSeccionEstudiante.getValue());
            estudiante.setAvanceCrediticio(Integer.valueOf(textAvanceCrediticio.getText().trim()));
            estudiante.setPromedio(Double.valueOf(textPromedio.getText().trim()));
            
            EstudianteExperienciaEducativaDTO experienciaAsignada;
            experienciaAsignada = new EstudianteExperienciaEducativaDTO();
            experienciaAsignada.setEstudiante(estudiante);
            experienciaAsignada.setExperienciaEducativa(comboExperienciaEducativa.getValue());
            
            if (!EstudianteValidador.validarEstudiante(estudiante)) {
                return;
            }

            if (modoEdicion) {
                editarEstudiante(estudiante);
            } else {
                registrarEstudiante(estudiante, experienciaAsignada);
            }
            
            salirAMenuPrincipal();
        } catch (NumberFormatException e) {
            LOG.error(e);
        }
    }
     
    public void llenarCamposEditablesEstudiante(EstudianteDTO estudiante){
        
        textMatricula.setText(estudiante.getMatricula());
        textNombreEstudiante.setText(estudiante.getNombreEstudiante());
        comboPeriodoEscolar.setValue(estudiante.getPeriodoEscolar());
        comboSeccionEstudiante.setValue(estudiante.getSeccionEstudiante());
        
        try {
            
            String matricula;
            matricula = estudiante.getMatricula();  
            
            EstudianteExperienciaEducativaDTO experienciaEducativa;
            experienciaEducativa = interfazEstudianteExperienciaEducativaDAO.obtenerExperienciaAsignadaPorEstudiante(matricula);
            
            ExperienciaEducativaDTO experienciaSeleccionada;
            experienciaSeleccionada = experienciaEducativa.getExperienciaEducativa();
            
            comboExperienciaEducativa.setValue(experienciaSeleccionada);
        }catch (SQLException sqle){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }catch (IOException slqe){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_CARGAR_INFORMACION);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
        
        textAvanceCrediticio.setText(estudiante.getAvanceCrediticio().toString());
        textPromedio.setText(estudiante.getPromedio().toString());
        textMatricula.setDisable(modoEdicion);
        textNombreEstudiante.setDisable(modoEdicion);
        comboPeriodoEscolar.setDisable(modoEdicion);
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    
    private void registrarEstudiante(EstudianteDTO estudiante, EstudianteExperienciaEducativaDTO experienciaAsignada){
        
        try {
            
            interfazEstudianteDAO.insertarEstudiante(estudiante);
            interfazEstudianteExperienciaEducativaDAO.insertarExperienciaAsignada(experienciaAsignada);
        } catch (SQLException ex) {
            
            LOG.error(ex);
        } catch (IOException ex) {
            
            LOG.error(ex);
        }
    }
    
    private void editarEstudiante(EstudianteDTO estudiante) {
        
        try {
            
            interfazEstudianteDAO.editarEstudiante(estudiante);
            salirAMenuPrincipal();
        } catch (SQLException ex) {
            
            LOG.error(ex);
        } catch (IOException ex) {
            
            LOG.error(ex);
        }
    }
    
    public void poblarInformacionCombos(){
        
        ObservableList<PeriodoEscolarDTO> listaPeriodos;
        ObservableList<SeccionDTO> listaSecciones;
        ObservableList<ExperienciaEducativaDTO> listaExperienciasEducativas;
        
        try {
            
            listaPeriodos = FXCollections.observableArrayList(interfazPeriodoEscolarDAO.listarPeriodos());
            comboPeriodoEscolar.setItems(listaPeriodos);
            
            listaSecciones = FXCollections.observableArrayList(interfazSeccionDAO.listarSecciones());
            comboSeccionEstudiante.setItems(listaSecciones);
            
            listaExperienciasEducativas = FXCollections.observableArrayList(interfazExperienciaEducativaDAO.listarExperienciaEducativa());
            comboExperienciaEducativa.setItems(listaExperienciasEducativas);
        } catch (SQLException ex) {
            
            LOG.error(ex);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ex) {
            
            LOG.error(ex);
        }
    }
    
    private void salirAMenuPrincipal (){
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION);
    }
}
