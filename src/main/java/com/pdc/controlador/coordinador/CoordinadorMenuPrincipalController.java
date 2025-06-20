package com.pdc.controlador.coordinador;


import com.pdc.dao.implementacion.CoordinadorDAOImpl;
import com.pdc.dao.interfaz.ICoordinadorDAO;
import com.pdc.modelo.dto.CoordinadorDTO;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

public class CoordinadorMenuPrincipalController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionOrganizacionVinculadaController.class);
    
    ICoordinadorDAO interfazCoordinadorDAO;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        interfazCoordinadorDAO = new CoordinadorDAOImpl();
        configurarSesion();
    }
    @FXML
    private Label labelCerrarSesion; 
    
    @FXML
    private Label labelNombreUsuario;
    
    @FXML    
    private void abrirGestionOrganizacionVinculada(ActionEvent event){
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ORGANIZACION_VINCULADA);
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
    private void abrirProyectosAsignados(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
    }
    
    @FXML
    private void abrirGestionEstudiantes(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION);
    }
    
    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }
    
    private void configurarSesion(){
        
        CoordinadorDTO coordinador;
        coordinador = (CoordinadorDTO) ManejadorDeSesion.getUsuario();
        String nombreCoordinador;
        nombreCoordinador = coordinador.getNombreCoordinador();
        labelNombreUsuario.setText(nombreCoordinador);
    }
}