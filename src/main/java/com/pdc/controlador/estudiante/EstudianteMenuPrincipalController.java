package com.pdc.controlador.estudiante;

import com.pdc.utileria.manejador.ManejadorDeVistas;
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

public class EstudianteMenuPrincipalController implements Initializable {
    private static final Logger LOG = Logger.getLogger(EstudianteMenuPrincipalController.class);

    
    @FXML
    private Label labelCerrarSesion;
    
    private Stage parentStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    void abrirVentanaConsultarCronograma(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_CONSULTA_CRONOGRAMA);
    }

    @FXML
    void abrirVentanaProyectoAsignado(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_CONSULTA_PROYECTO_ASIGNADO);
    }

    @FXML
    void abrirVentanaRegistrarAutoevaluacion(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_AUTOEVALUACION);
    }

    @FXML
    void abrirVentanaRegistrarSolicitudProyecto(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_SOLICITUD_PROYECTO);
    }

    @FXML
    void abrirVentanaActualizarPerfil(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZACION_PERFIL);
    }

    @FXML
    void abrirVentanaEvaluacionPresentacion(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_EVALUACION_PRESENTACION);
    }

    @FXML
    void abrirVentanaRegistrarReporteMensual(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_REPORTE_MENSUAL);
    }

    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }   
}
