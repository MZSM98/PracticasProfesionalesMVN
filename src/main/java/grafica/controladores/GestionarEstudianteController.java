package grafica.controladores;

import accesoadatos.dto.EstudianteDTO;
import grafica.utils.AlertaUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.dao.EstudianteDAOImpl;
import org.apache.log4j.Logger;
import logica.interfaces.IEstudianteDAO;

public class GestionarEstudianteController implements Initializable {
    private static final Logger LOG = Logger.getLogger(GestionarEstudianteController.class);

    @FXML
    private TableColumn<EstudianteDTO, Integer> columnAvance;

    @FXML
    private TableColumn<EstudianteDTO, String> columnMatricula;

    @FXML
    private TableColumn<EstudianteDTO, String> columnNombre;

    @FXML
    private TableColumn<EstudianteDTO, String> columnPeriodo;

    @FXML
    private TableColumn<EstudianteDTO, Double> columnPromedio;

    @FXML
    private TableColumn<EstudianteDTO, String> columnSeccion;

    @FXML
    private TableView<EstudianteDTO> tableEstudiantes;
    
    private IEstudianteDAO interfazEstudianteDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDAO = new EstudianteDAOImpl();
        configurarColumnasTablaEstudiante();
        poblarTablaEstudiante();
    }    
    
    @FXML
    void abrirEditarAcademico(ActionEvent event) {
        EstudianteDTO estudianteSeleccionado = tableEstudiantes.getSelectionModel().getSelectedItem();
        
        if (estudianteSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione un registro para editar", Alert.AlertType.WARNING);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLRegistrarEstudiante.fxml"));
            Parent root = loader.load();
            
            RegistroEstudianteController controlador = loader.getController();
            controlador.setModoEdicion(Boolean.TRUE);
            controlador.llenarCamposEditablesEstudiante(estudianteSeleccionado);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            poblarTablaEstudiante();
            
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void abrirRegistrarAcademico(ActionEvent event) {
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLRegistrarEstudiante.fxml"));
            Parent root = loader.load();
            RegistroEstudianteController controlador = loader.getController();
            controlador.poblarInformacionComboPeriodoySeccion();
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            poblarTablaEstudiante();
            
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana de edición: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void salirAMenuPrincipal(ActionEvent event) {
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
    }

    private void configurarColumnasTablaEstudiante(){
        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        columnPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccionEstudiante"));
        columnAvance.setCellValueFactory(new PropertyValueFactory<>("avanceCrediticio"));
        columnPromedio.setCellValueFactory(new PropertyValueFactory<>("promedio"));
    }
    private void poblarTablaEstudiante(){
        try {
            List<EstudianteDTO> listaEstudiantes = interfazEstudianteDAO.listarEstudiantes();
            ObservableList<EstudianteDTO> listaObservableEstudiantes = FXCollections.observableArrayList(listaEstudiantes);
            tableEstudiantes.setItems(listaObservableEstudiantes);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
    
}
