package grafica.controladores.estudiante;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ActualizacionPerfilEstudianteController implements Initializable {
    @FXML
    private Button botonCancelar;

    @FXML
    private PasswordField botonConfirmeNuevaContrasena;

    @FXML
    private Button botonGuardar;

    @FXML
    private PasswordField botonNuevaContrasena;

    @FXML
    private Label labelConfirmeNuevaContrasena;

    @FXML
    private Label labelContrasena;

    @FXML
    private Label labelNuevaContrasena;

    @FXML
    private Label labelTitulo;

    @FXML
    private PasswordField textContrasenaActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }        
    
    @FXML
    void guardarPerfil(ActionEvent event) {

    }

    @FXML
    void volverAMenuPrincipal(ActionEvent event) {
        

    }
    

    
}
