package com.pdc.controlador.estudiante;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class EstudianteMenuPrincipalController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(EstudianteMenuPrincipalController.class);
    
    @FXML
    private Label labelNombreUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSesion();
    }
    
    @FXML
    void abrirVentanaConsultarCronograma(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_CONSULTA_CRONOGRAMA);
    }

    @FXML
    void abrirVentanaProyectoAsignado(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
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
    void abrirVentanaRegistrarReporteMensual(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_REPORTE_MENSUAL);
    }

    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }   
    
    private void configurarSesion(){
        EstudianteDTO estudiante;
        estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String nombreCoordinador;
        nombreCoordinador = estudiante.getNombreEstudiante();
        labelNombreUsuario.setText(nombreCoordinador);
    }
}
