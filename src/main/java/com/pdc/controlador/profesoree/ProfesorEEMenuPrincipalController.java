package com.pdc.controlador.profesoree;

import com.pdc.utileria.manejador.ManejadorDeVistas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

public class ProfesorEEMenuPrincipalController{
    
    private static final Logger LOG = Logger.getLogger(ProfesorEEMenuPrincipalController.class);
    
    @FXML
    private Label labelCerrarSesion;
    
    @FXML
    void abrirVentanaConsultaCalificaciones(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_REGISTRO_CALIFACION_FINAL);
    }

    @FXML
    void abrirVentanaEvaluaciones(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_CONSULTA_EVALUACION_PRESENTACION);
    }

    @FXML
    void abrirVentanaGestionEstudiante(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION);
    }
    
    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }

}
