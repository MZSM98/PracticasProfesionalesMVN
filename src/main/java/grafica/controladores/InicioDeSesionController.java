package grafica.controladores;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logica.dao.TipoUsuarioDAO;
import logica.dao.UsuarioDAO;
import logica.funcionalidades.InicioDeSesionValidador;
import logica.interfaces.InterfazTipoUsuarioDAO;
import logica.interfaces.InterfazUsuarioDAO;

import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;
import logica.interfaces.InterfazMenuPrincipal;

public class InicioDeSesionController implements Initializable {
    
    private static final Integer COORDINADOR = 1;
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    private static final Integer ESTUDIANTE = 4;
    
    private static final Logger LOG = Logger.getLogger(InicioDeSesionController.class);

    @FXML
    private Button botonSalir;

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<TipoUsuarioDTO> comboTipoUsuario;

    private InterfazUsuarioDAO interfazUsuarioDAO;
    private InterfazTipoUsuarioDAO interfazTipoUsuarioDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazUsuarioDAO = new UsuarioDAO();
        interfazTipoUsuarioDAO = new TipoUsuarioDAO();
        poblarComboTipoUsuario();
    }    

    @FXML
    void cerrarPrograma(ActionEvent event) {
        Stage currentStage = (Stage) botonSalir.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void iniciarSesion(ActionEvent event){
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuario(txtUsuario.getText().trim());
        usuario.setContrasena(txtContrasena.getText().trim());
        usuario.setTipoUsuario(comboTipoUsuario.getValue());
        
        if(!InicioDeSesionValidador.validar(usuario)){
            return;
        }
        
        try {
            UsuarioDTO usuarioBusqueda = interfazUsuarioDAO.buscarUsuario(usuario.getUsuario());
            usuario.setSalt(usuarioBusqueda.getSalt());
            
            if(interfazUsuarioDAO.autenticarUsuario(usuario)){
                abrirMenuPrincipal(event, obtenerRecursoVetana(usuario.getTipoUsuario()));
            }else{
                AlertaUtil.mostrarAlerta("Advertencia", "Favor de validar los datos de acceso.", Alert.AlertType.WARNING);
            }

        } catch (SQLException ex) {
           LOG.error(ex);
           AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
        
        } catch (NullPointerException npe){
            
            LOG.error("No se seleccionó ningún tipo de usuario",npe);
            AlertaUtil.mostrarAlerta("Advertencia", "Debe seleccionar un tipo de usuario", Alert.AlertType.WARNING);
        }
    
        catch (IOException ex) {
            
           LOG.error(ex);
        }

    }
    
    private void abrirMenuPrincipal(ActionEvent event, String resource){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = null;
        try {          
            root = loader.load();
        } catch (IOException ex) {
            LOG.error(ex);
        }
            
        InterfazMenuPrincipal controlador = loader.getController();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlador.setParentStage(currentStage);
        currentStage.close();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    private void poblarComboTipoUsuario(){
        List<TipoUsuarioDTO> listaTipoUsuario;
        try {
            listaTipoUsuario = interfazTipoUsuarioDAO.listaTipoUsuario();
            ObservableList<TipoUsuarioDTO> listaObservableTipoUsuario = FXCollections.observableArrayList(listaTipoUsuario);
            comboTipoUsuario.setItems(listaObservableTipoUsuario);
        } catch (SQLException sqle) {
            LOG.error("Error de conexión con la base de datos",sqle);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
    
    private String obtenerRecursoVetana(TipoUsuarioDTO tipoUsuario){
        if (COORDINADOR.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/principalcoordinador/FXMLMenuPrincipalCoordinador.fxml";
        } else if (ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/academicoevaluador/FXMLMenuPrincipalAcademicoEvaluador.fxml";
        } else if (PROFESOR_EE.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/profesoree/FXMLMenuPrincipalProfesorEE.fxml";
        } else if (ESTUDIANTE.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/estudiante/FXMLMenuPrincipalEstudiante.fxml";
        } else {
            throw new AssertionError("No existe tipo de usuario");
        }
    }
 
}
