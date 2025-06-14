package com.pdc.controlador.estudiante;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.modelo.dto.SeccionDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.PeriodoEscolarDAOImpl;
import com.pdc.dao.implementacion.SeccionDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.dao.interfaz.ISeccionDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;

public class RegistroEstudianteController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(RegistroEstudianteController.class);
    @FXML
    private Button botonCancelar;
    
    @FXML
    private TextField textMatricula;

    @FXML
    private TextField textNombreEstudiante;

    @FXML
    private ComboBox<PeriodoEscolarDTO> comboPeriodoEscolar;

    @FXML
    private TextField textPromedio;

    @FXML
    private ComboBox<SeccionDTO> comboSeccionEstudiante;

    @FXML
    private TextField textAvanceCrediticio;
    
    private boolean modoEdicion;
    
    private IEstudianteDAO interfazEstudianteDAO;
    
    private IPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    
    private ISeccionDAO interfazSeccionDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAOImpl();
        interfazSeccionDAO = new SeccionDAOImpl();
        poblarInformacionComboPeriodoySeccion();
    }    

    @FXML
    void cancelarRegistroEstudiante(ActionEvent event) {
        
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
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
            
            if (!EstudianteValidador.validarEstudiante(estudiante)) {
                return;
            }

            if (modoEdicion) {
                editarEstudiante(estudiante);
            } else {
                registrarEstudiante(estudiante);
            }
            
            limpiarCampos();
        } catch (NumberFormatException e) {
            LOG.error(e);
        }
    }
     
    public void llenarCamposEditablesEstudiante(EstudianteDTO estudiante){
        textMatricula.setText(estudiante.getMatricula());
        textNombreEstudiante.setText(estudiante.getNombreEstudiante());
        comboPeriodoEscolar.setValue(estudiante.getPeriodoEscolar());
        comboSeccionEstudiante.setValue(estudiante.getSeccionEstudiante());
        textAvanceCrediticio.setText(estudiante.getAvanceCrediticio().toString());
        textPromedio.setText(estudiante.getPromedio().toString());
        textMatricula.setDisable(modoEdicion);
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    
    private void registrarEstudiante(EstudianteDTO estudiante){
        
        try {
            
            interfazEstudianteDAO.insertarEstudiante(estudiante);
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
    
    public void poblarInformacionComboPeriodoySeccion(){
        
        ObservableList<PeriodoEscolarDTO> listaPeriodos;
        ObservableList<SeccionDTO> listaSecciones;
        
        try {
            
            listaPeriodos = FXCollections.observableArrayList(interfazPeriodoEscolarDAO.listarPeriodos());
            comboPeriodoEscolar.setItems(listaPeriodos);
            
            listaSecciones = FXCollections.observableArrayList(interfazSeccionDAO.listarSecciones());
            comboSeccionEstudiante.setItems(listaSecciones);
        } catch (SQLException ex) {
            
            LOG.error(ex);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ex) {
            
            LOG.error(ex);
        }
    }
    
    private void limpiarCampos(){
        
        textMatricula.setText("");
        textNombreEstudiante.setText("");
        textAvanceCrediticio.setText("");
        textPromedio.setText("");
        comboPeriodoEscolar.setValue(null);
        comboSeccionEstudiante.setValue(null);
    }
    
    private void salirAMenuPrincipal (){
        
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();  
    }
}
