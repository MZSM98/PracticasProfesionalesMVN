package com.pdc.controlador.profesoree;

import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class ProfesorEEMenuPrincipalController{
    
    private static final Logger LOG = Logger.getLogger(ProfesorEEMenuPrincipalController.class);
    
    @FXML
    private Label labelCerrarSesion;
    
    private Stage parentStage;
   
    @FXML
    void abrirVentanaConsultaCalificaciones(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLRegistrarCalificacionFinal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestión de Estudiantes");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana", ex);
        }
    }

    @FXML
    void abrirVentanaEvaluaciones(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLRegistrarCalificacionFinal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Consta de Evaluación de Presentaciones");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana", ex);
        }
    }

    @FXML
    void abrirVentanaGestionEstudiante(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grafica/profesoree/FXMLGestionarEstudiante.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestionar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana", ex);
        }
    }
    
    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }

}
