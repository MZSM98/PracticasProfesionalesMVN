package grafica.controladores;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.dto.ProfesorEEDTO;
import accesoadatos.dto.TipoUsuarioDTO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.dao.AcademicoEvaluadorDAO;
import logica.dao.ProfesorEEDAO;
import logica.dao.TipoUsuarioDAO;
import logica.interfaces.InterfazAcademicoEvaluadorDAO;
import logica.interfaces.InterfazProfesorEEDAO;
import logica.interfaces.InterfazTipoUsuarioDAO;
import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;

public class GestionAcademicoEvaluadorController implements Initializable{
    
    private static final Logger LOG = Logger.getLogger(GestionAcademicoEvaluadorController.class);
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    @FXML
    private Button botonSalir;

    @FXML
    private TableColumn<UsuarioDTO, String> columnNombre;

    @FXML
    private TableColumn<UsuarioDTO, String> columnNumeroEmpleado;
    
    @FXML
    private TableColumn<UsuarioDTO, String> columnSeccion;

    @FXML
    private TableView<UsuarioDTO> tableGestionAcademicos;
    
    @FXML
    private ComboBox<TipoUsuarioDTO> comboTipoAcademico;
    
    private InterfazAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private InterfazProfesorEEDAO interfazProfesorEEDAO;
    private InterfazTipoUsuarioDAO interfazTipoUsuarioDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAO();
       interfazProfesorEEDAO = new ProfesorEEDAO();
       interfazTipoUsuarioDAO = new TipoUsuarioDAO();
       configurarComboBox();
       configurarColumnas();
       cargarListaAcademico();
    }
    
    private void configurarColumnas(){
        tableGestionAcademicos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
        
        if(ACADEMICO_EVALUADOR.equals(tipoUsuarioSeleccionado.getIdTipo())){
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAcademico"));
            columnNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<>("numeroDeTrabajador"));
            tableGestionAcademicos.getColumns().remove(columnSeccion);
        }else if(PROFESOR_EE.equals(tipoUsuarioSeleccionado.getIdTipo())){
            tableGestionAcademicos.getColumns().add(columnSeccion);
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
            columnNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<>("numeroTrabajador"));
            columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccion"));
        }
 
    }
    
    private void configurarComboBox(){
        try {
            TipoUsuarioDTO tipoUsuarioAcademicoEvaluador = interfazTipoUsuarioDAO.buscarTipoUsuario(ACADEMICO_EVALUADOR);
            TipoUsuarioDTO tipoUsuarioProfesorEE = interfazTipoUsuarioDAO.buscarTipoUsuario(PROFESOR_EE);

            ObservableList<TipoUsuarioDTO> listaObservableAcademico = FXCollections.observableArrayList();
            listaObservableAcademico.add(tipoUsuarioAcademicoEvaluador);
            listaObservableAcademico.add(tipoUsuarioProfesorEE);
            comboTipoAcademico.setItems(listaObservableAcademico);
            comboTipoAcademico.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
    
    private void cargarListaAcademico(){
            try {
            ObservableList<UsuarioDTO> listaObservableUsuarioDTO = FXCollections.observableArrayList();

            List<AcademicoEvaluadorDTO> listaAcademicoEvaluador = interfazAcademicoEvaluadorDAO.listarAcademicoEvaluador();
            ObservableList<AcademicoEvaluadorDTO> listaObservableAcademicoEvaluador = FXCollections.observableArrayList(listaAcademicoEvaluador);
                        
            for (AcademicoEvaluadorDTO academico : listaObservableAcademicoEvaluador) {
                listaObservableUsuarioDTO.add(academico); 
            }

            tableGestionAcademicos.setItems(listaObservableUsuarioDTO);
        } catch (SQLException e) {
            LOG.error("Error al cargar la información de los academicos: " + e.getMessage(), e);
            AlertaUtil.mostrarAlerta("Error", "No se puede cargar la información de los academicos, contacte a un administrador.", Alert.AlertType.ERROR);
            
        } catch (IOException e){
            LOG.error("No se lograron cargar los registros",e);
            AlertaUtil.mostrarAlerta("Error", "No se puede cargar la información de los academicos, contacte a un administrador.", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarListaProfesorEvaluador(){
            try {
            ObservableList<UsuarioDTO> listaObservableUsuarioDTO = FXCollections.observableArrayList();

            List<ProfesorEEDTO> listaAcademicoEvaluador = interfazProfesorEEDAO.listaProfesorEE();
            ObservableList<ProfesorEEDTO> listaObservableAcademicoEvaluador = FXCollections.observableArrayList(listaAcademicoEvaluador);
                        
            for (ProfesorEEDTO academico : listaObservableAcademicoEvaluador) {
                listaObservableUsuarioDTO.add(academico); 
            }

            tableGestionAcademicos.setItems(listaObservableUsuarioDTO);
            
        } catch (SQLException e) {
            
            LOG.error("Error al cargar la información de los academicos: " + e.getMessage(), e);
            AlertaUtil.mostrarAlerta("Error", "No se puede cargar la información de los academicos, contacte a un administrador.", Alert.AlertType.ERROR);
            
        } catch (IOException e){
            
            LOG.error("No se lograron cargar los registros",e);
            AlertaUtil.mostrarAlerta("Error", "No se puede cargar la información de los academicos, contacte a un administrador.", Alert.AlertType.ERROR);
        }
    }    

    @FXML
    private void abrirRegistrarAcademico(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLRegistroAcademicoEvaluador.fxml"));
            Parent root = loader.load();          
            
            RegistroAcademicoEvaluadorController controlador = loader.getController();
            TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
            controlador.asignarTipoUsuario(tipoUsuarioSeleccionado);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registro académico evaluador");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)) {
                cargarListaAcademico();
            } else if (tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)) {
                cargarListaProfesorEvaluador();
            }

        } catch (IOException e) {
            LOG.error("Error al abrir pantalla de registro.: " + e.getMessage(),e);
            AlertaUtil.mostrarAlerta ("Error", "Ha ocurrido un error, intentelo más tarde", Alert.AlertType.ERROR);
            
        }
    }

    @FXML
    private void abrirEditarAcademico(ActionEvent event) {
        UsuarioDTO academicoSeleccionado = tableGestionAcademicos.getSelectionModel().getSelectedItem();
        
        if (academicoSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione una organización para editar", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLRegistroAcademicoEvaluador.fxml"));
            Parent root = loader.load();
            
            RegistroAcademicoEvaluadorController controlador = loader.getController();
                controlador.cambiarAModoEdicion(true);
                
            TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
                controlador.asignarTipoUsuario(tipoUsuarioSeleccionado);
                controlador.llenarCamposEditablesAcademico(academicoSeleccionado);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Académico Evaluador");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            if (tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)) {
                cargarListaAcademico();
            } else if (tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)) {
                cargarListaProfesorEvaluador();
            }
            
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana de edición: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cambiaTipoAcademico(ActionEvent event) {
       configurarColumnas();
       
        TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();

        if(tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)){
            cargarListaAcademico();
        }else if(tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)){
            cargarListaProfesorEvaluador();
        }
    }
    
    @FXML
    void salirAMenuPrincipal(ActionEvent event) {
        Stage ventanaActual = (Stage) botonSalir.getScene().getWindow();
        ventanaActual.close();
    }

}
