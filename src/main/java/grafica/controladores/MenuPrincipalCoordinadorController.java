package grafica.controladores;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.interfaces.InterfazMenuPrincipal;
import org.apache.log4j.Logger;


public class MenuPrincipalCoordinadorController implements InterfazMenuPrincipal{
    
    private static final Logger LOG = Logger.getLogger(GestionOrganizacionVinculadaController.class);
    
    @FXML
    private Label labelCerrarSesion;    
    
    private Stage parentStage;
    
    @FXML    
    private void abrirGestionOrganizacionVinculada(ActionEvent event){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/organizacionvinculada/FXMLGestionOrganizacionVinculada.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestión De Organizaciones");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana de gestión de Organizaciones Vinculadas: " + ex.getMessage());            
        }        
    }
    
    @FXML    
    private void abrirGestionAcademico(ActionEvent event){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLGestionAcademicoEvaluador.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestión Académicos");
            stage.setScene(new Scene(root));
            stage.showAndWait();            
            
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana de Gestion de Académicos: " + ex.getMessage());
            
        }        
    }
    
    
    @FXML
    private void abrirGestionProyecto(){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/proyecto/FXMLGestionProyecto.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestión Proyectos");
            stage.setScene(new Scene(root));
            stage.showAndWait();            
            
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana de gestión de proyectos: " + ex.getMessage());            
        }     
    }
    
    @FXML
    private void cerrarSesion() {
        
        Stage currentStage = (Stage) labelCerrarSesion.getScene().getWindow();
        currentStage.close();
        parentStage.show();
    }
    
    public void setParentStage(Stage parentStage) {
        
        this.parentStage = parentStage;
    }
}