package grafica.controladores;

import accesoadatos.dto.EstudianteDTO;
import accesoadatos.dto.PeriodoEscolarDTO;
import accesoadatos.dto.SeccionDTO;
import grafica.validadores.EstudianteValidador;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logica.dao.EstudianteDAO;
import logica.dao.PeriodoEscolarDAO;
import logica.dao.SeccionDAO;
import logica.interfaces.InterfazEstudianteDAO;
import logica.interfaces.InterfazPeriodoEscolarDAO;
import logica.interfaces.InterfazSeccionDAO;
import org.apache.log4j.Logger;

public class RegistrarEstudianteController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(RegistrarEstudianteController.class);
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
    
    private InterfazEstudianteDAO interfazEstudianteDAO;
    
    private InterfazPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    
    private InterfazSeccionDAO interfazSeccionDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDAO = new EstudianteDAO();
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAO();
        interfazSeccionDAO = new SeccionDAO();
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
            Stage stage = (Stage) botonCancelar.getScene().getWindow();
            stage.close();  
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
}
