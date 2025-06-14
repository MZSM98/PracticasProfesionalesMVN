
package grafica.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logica.interfaces.IMenuPrincipal;

public class MenuPrincipalAcademicoEvaluadorController implements Initializable, IMenuPrincipal {

    @FXML
    private Label labelCerrarSesion;
    
    private Stage parentStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    
    @FXML
    private void cerrarSesion() {
        Stage currentStage = (Stage) labelCerrarSesion.getScene().getWindow();
        currentStage.close();
        parentStage.show();
    }
    
}
