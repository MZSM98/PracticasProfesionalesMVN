package grafica.controladores;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.dto.UsuarioDTO;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.dao.AcademicoEvaluadorDAO;
import logica.dao.ProfesorEEDAO;
import logica.interfaces.InterfazAcademicoEvaluadorDAO;
import logica.interfaces.InterfazProfesorEEDAO;
import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;

public class GestionAcademicoEvaluadorController implements Initializable{
    
    private static final Logger LOG = Logger.getLogger(GestionAcademicoEvaluadorController.class);

    @FXML
    private Button botonSalir;

    @FXML
    private TableColumn<AcademicoEvaluadorDTO, String> columnNombre;

    @FXML
    private TableColumn<AcademicoEvaluadorDTO, String> columnNumeroEmpleado;

    @FXML
    private TableView<AcademicoEvaluadorDTO> tableGestionAcademicos;
    
    private InterfazAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private InterfazProfesorEEDAO interfazProfesorEEDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAO();
       interfazProfesorEEDAO = new ProfesorEEDAO();
       configurarColumnas();
       cargarListaAcademicoEvaluador();
    }
    
    private void configurarColumnas(){
        
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAcademico"));
        columnNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<>("numeroDeTrabajador"));
    }
    
    private void cargarListaAcademicoEvaluador(){
        
        try {            
            
            List<AcademicoEvaluadorDTO> listaAcademicoEvaluador = interfazAcademicoEvaluadorDAO.listarAcademicoEvaluador();
            ObservableList<AcademicoEvaluadorDTO> listaObservableAcademicoEvaluador = FXCollections.observableArrayList(listaAcademicoEvaluador);
            tableGestionAcademicos.setItems(listaObservableAcademicoEvaluador);            
        } catch (SQLException e) {
            
            LOG.error("Error al cargar la información de los academicos: " + e.getMessage(), e);
            AlertaUtil.mostrarAlerta("Error", "No se puedo cargar la información, contacte a un administrador.", Alert.AlertType.ERROR);            
        } catch (IOException e){
            
            LOG.error("No se lograron cargar los registros",e);
            AlertaUtil.mostrarAlerta("Error", "No se puedo cargar la información, contacte a un administrador.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void abrirRegistrarAcademico(ActionEvent event) {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLRegistroAcademicoEvaluador.fxml"));
            Parent root = loader.load();            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registro académico evaluador");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarListaAcademicoEvaluador();
        } catch (IOException e) {
            
            LOG.error("Error al abrir pantalla de registro.: " + e.getMessage(),e);
            AlertaUtil.mostrarAlerta ("Error", "Ha ocurrido un error, intentelo más tarde", Alert.AlertType.ERROR);            
        }
    }

    @FXML
    private void abrirEditarAcademico(ActionEvent event) {
        
        AcademicoEvaluadorDTO academicoEvaluadorSeleccionado = tableGestionAcademicos.getSelectionModel().getSelectedItem();
        
        if (academicoEvaluadorSeleccionado == null) {
            
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione una organización para editar", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLRegistroAcademicoEvaluador.fxml"));
            Parent root = loader.load();
            
            RegistroAcademicoEvaluadorController controlador = loader.getController();
            controlador.cambiarAModoEdicion(true);
            controlador.llenarCamposEditablesAcademicoEvaluador(academicoEvaluadorSeleccionado);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Académico Evaluador");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            cargarListaAcademicoEvaluador();            
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana de edición: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void seleccionaTipoAcademico(ActionEvent event) {
        
        configurarColumnas();
    }
    
    @FXML
    void salirAMenuPrincipal(ActionEvent event) {
        
        Stage ventanaActual = (Stage) botonSalir.getScene().getWindow();
        ventanaActual.close();
    }

}
