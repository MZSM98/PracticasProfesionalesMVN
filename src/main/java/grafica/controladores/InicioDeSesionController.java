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
import logica.dao.TipoUsuarioDAOImpl;
import logica.dao.UsuarioDAOImpl;
import grafica.validadores.InicioDeSesionValidador;

import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;
import grafica.utils.ConstantesUtil;
import grafica.utils.RestriccionCamposUtil;
import logica.interfaces.IMenuPrincipal;
import logica.interfaces.IUsuarioDAO;
import logica.interfaces.ITipoUsuarioDAO;

public class InicioDeSesionController implements Initializable {
    
    private static final Integer COORDINADOR = 1;
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    private static final Integer ESTUDIANTE = 4;
    
    private static final Logger LOG = Logger.getLogger(InicioDeSesionController.class);

    @FXML
    private Button botonSalir;

    @FXML
    private TextField textUsuario;
    
    @FXML
    private PasswordField textContrasena;

    @FXML
    private ComboBox<TipoUsuarioDTO> comboTipoUsuario;
    
    @FXML
    private Button botonIniciarSesion;

    private IUsuarioDAO interfazUsuarioDAO;
    private ITipoUsuarioDAO interfazTipoUsuarioDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonIniciarSesion.setDefaultButton(Boolean.TRUE);
        interfazUsuarioDAO = new UsuarioDAOImpl();
        interfazTipoUsuarioDAO = new TipoUsuarioDAOImpl();
        poblarComboTipoUsuario();
        aplicarRestriccionesLongitudACampos();
    }    

    @FXML
    void cerrarPrograma(ActionEvent event) {
        
        Stage currentStage = (Stage) botonSalir.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void iniciarSesion(ActionEvent event){
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(textUsuario.getText().trim());
        usuarioDTO.setContrasena(textContrasena.getText().trim());
        usuarioDTO.setTipoUsuario(comboTipoUsuario.getValue());
        
        if(!validarCamposInicioSesion(usuarioDTO)){
            
            return;
        }
        try {
            
            UsuarioDTO usuarioBusqueda = interfazUsuarioDAO.buscarUsuario(usuarioDTO.getUsuario());
            usuarioDTO.setSalt(usuarioBusqueda.getSalt());

            if (interfazUsuarioDAO.autenticarUsuario(usuarioDTO)) {

                abrirMenuPrincipal(event, obtenerRecursoVetana(usuarioDTO.getTipoUsuario()));
                limpiarCampos();
            } else {

                AlertaUtil.mostrarAlerta(ConstantesUtil.ADVERTENCIA, ConstantesUtil.ALERTA_CREDENCIALES_INVALIDAS, Alert.AlertType.WARNING);
            }
        }catch (IllegalArgumentException ioe){
            
            LOG.error(ConstantesUtil.LOG_DATOS_NO_VALIDOS, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_DATOS_INVALIDOS, Alert.AlertType.WARNING); 
        }catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD,sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA,ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
        }
    }
    

    private void abrirMenuPrincipal(ActionEvent event, String resource){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));

        Parent root = null;
        
        try {      
            
            root = loader.load();
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
        }
            
        IMenuPrincipal controlador = loader.getController();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlador.setParentStage(currentStage);
        currentStage.close();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(ConstantesUtil.MENU_PRINCIPAL);
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
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD,sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_CARGAR_INFORMACION,ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_INFORMACION, Alert.AlertType.WARNING);
        }
    }
    

    private String obtenerRecursoVetana(TipoUsuarioDTO tipoUsuario){
        
        if (COORDINADOR.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/principalcoordinador/FXMLMenuPrincipalCoordinador.fxml";
        } else if (ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/academico/FXMLMenuPrincipalAcademicoEvaluador.fxml";
        } else if (PROFESOR_EE.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/profesoree/FXMLMenuPrincipalProfesorEE.fxml";
        } else if (ESTUDIANTE.equals(tipoUsuario.getIdTipo())) {
            return "/grafica/estudiante/FXMLMenuPrincipalEstudiante.fxml";
        } else {
            throw new IllegalArgumentException("No existe tipo de usuario");
        }
    }

    private boolean validarCamposInicioSesion(UsuarioDTO usuarioDTO) {
               
        try {
            
            InicioDeSesionValidador.validarInicioDeSesion(usuarioDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.LOG_DATOS_NO_VALIDOS,iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ALERTA_DATOS_INVALIDOS, iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        } 
    }
    
    private void limpiarCampos() {
        
        comboTipoUsuario.setValue(null);
        textUsuario.setText("");
        textContrasena.setText("");
    }

    private void aplicarRestriccionesLongitudACampos(){
        
        RestriccionCamposUtil.aplicarRestriccionLongitud(textContrasena, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textUsuario, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
    }

}
