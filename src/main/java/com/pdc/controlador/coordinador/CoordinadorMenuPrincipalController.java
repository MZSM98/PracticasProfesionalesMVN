package com.pdc.controlador.coordinador;


import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;


public class CoordinadorMenuPrincipalController {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionOrganizacionVinculadaController.class);
    
    @FXML
    private Label labelCerrarSesion;    
    
    @FXML    
    private void abrirGestionOrganizacionVinculada(ActionEvent event){
        try{
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ORGANIZACION_VINCULADA);
        }catch(NullPointerException npe){
            LOG.error(npe);
            
        }
    }
    
    @FXML    
    private void abrirGestionAcademico(ActionEvent event){
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ACADEMICO);
    }
    
    @FXML
    private void abrirGestionProyecto(){
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_PROYECTO);
    }
    
    @FXML
    void abrirGestionEstudiantes(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION);
    }
    
    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }
    
}