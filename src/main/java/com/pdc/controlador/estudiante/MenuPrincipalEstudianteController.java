package com.pdc.controlador.estudiante;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import com.pdc.dao.interfaz.IMenuPrincipal;

public class MenuPrincipalEstudianteController implements Initializable, IMenuPrincipal {
    private static final Logger LOG = Logger.getLogger(MenuPrincipalEstudianteController.class);

    
    @FXML
    private Label labelCerrarSesion;
    
    private Stage parentStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    void abrirVentanaConsultarCronograma(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLConsultaCronogramaEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consultar cronograma");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaProyectoAsignado(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLConsultaProyectoAsignadoEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consulta proyecto asignado");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaRegistrarAutoevaluacion(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLRegistroAutoevaluacionEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar autoevaluacion");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaRegistrarSolicitudProyecto(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLRegistroSolicitudProyectoEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar solicitud de proyecto");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaActualizarPerfil(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLActualizacionPerfilEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Actualizar perfil");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaEvaluacionPresentacion(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLEvaluacionPresentacionEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar evaluacion de presentación");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
    }

    @FXML
    void abrirVentanaRegistrarReporteMensual(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/estudiante/FXMLRegistroReporteMensualEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar reporte mensual");
            stage.setScene(new Scene(root));
            stage.showAndWait();                     
        } catch (IOException ex) {
            
            LOG.error("Error al cargar la ventana", ex);            
        }
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
