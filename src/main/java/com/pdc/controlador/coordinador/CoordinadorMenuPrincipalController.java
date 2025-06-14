package com.pdc.controlador.coordinador;


import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import com.pdc.dao.interfaz.IMenuPrincipal;


public class CoordinadorMenuPrincipalController implements IMenuPrincipal{
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionOrganizacionVinculadaController.class);
    
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
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
        }        
    }
    
    @FXML    
    private void abrirGestionAcademico(ActionEvent event){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/academico/FXMLGestionAcademicos.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestión Académicos");
            stage.setScene(new Scene(root));
            stage.showAndWait();            
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA + ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
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
            
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
        }     
    }
    
    @FXML
    void abrirGestionEstudiantes(ActionEvent event) {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLGestionarEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestionar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
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