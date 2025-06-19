package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.ResponsableOrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class CoordinadorRegistroResponsableOrganizacionVinculadaController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorRegistroResponsableOrganizacionVinculadaController.class);

    @FXML
    private TextField textRfcResponsable;
    
    @FXML
    private TextField textNombreResponsable;
    
    @FXML
    private TextField textCargoResponsable;
    
    @FXML
    private TextField textCorreoResponsable;
            
    @FXML 
    private Button botonCancelarRegistroOV;
    
    @FXML
    private Button botonRegistrarOrganizacionVinculada;
    
    private OrganizacionVinculadaDTO organizacionVinculadaDTO;
    private ResponsableOrganizacionVinculadaDTO responsableOrganizacionVinculadaDTO;
    private IResponsableOrganizacionVinculadaDAO interfazResponsableOrganizacionVinculadaDAO;
    private boolean modoEdicion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazResponsableOrganizacionVinculadaDAO = new ResponsableOrganizacionVinculadaDAOImpl();
        
    }
    
    public void cambiarAModoEdicion(boolean modoEdicion) {
        
        this.modoEdicion = modoEdicion;
        
        if (modoEdicion) {
            
            botonRegistrarOrganizacionVinculada.setText("Actualizar");
        }
    }
    
    public void establecerOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO){
        this.organizacionVinculadaDTO = organizacionVinculadaDTO;
    }
    
    @FXML
    private void registrarResponsableOrganizacion(){
        
        if(!modoEdicion){
            
            responsableOrganizacionVinculadaDTO = new ResponsableOrganizacionVinculadaDTO();
        }
        
        responsableOrganizacionVinculadaDTO.setRfc(textRfcResponsable.getText().trim().toUpperCase());
        responsableOrganizacionVinculadaDTO.setNombreResponsable(textNombreResponsable.getText().replaceAll(ConstantesUtil.REGEX_ESPACIOS_MULTIPLES,ConstantesUtil.ESPACIO).trim());
        responsableOrganizacionVinculadaDTO.setCargo(textCargoResponsable.getText().trim());
        responsableOrganizacionVinculadaDTO.setCorreoResponsable(textCorreoResponsable.getText().trim());
        responsableOrganizacionVinculadaDTO.setRfcMoral(organizacionVinculadaDTO.getRfcMoral());
        
        if (!validarCamposResponsableOrganizacion(responsableOrganizacionVinculadaDTO)) {
            
            return;
        }     
        
        if (modoEdicion) {
            
            actualizarResponsableOrganizacion(responsableOrganizacionVinculadaDTO);
        } else {
            
            crearNuevoResponsableOrganizacion(responsableOrganizacionVinculadaDTO);
        }
        
    }
    
    @FXML
    private void volverAGestionResponsablesOrganizacion(){
        
    }
    
    private void actualizarResponsableOrganizacion(ResponsableOrganizacionVinculadaDTO responsableOrganizacionVinculadaDTO){
        
    }
    
    private void crearNuevoResponsableOrganizacion(ResponsableOrganizacionVinculadaDTO responsableOrganizacionVinculadaDTO){
        
        try {
            
            interfazResponsableOrganizacionVinculadaDAO.insertarResponsableOV(responsableOrganizacionVinculadaDTO);
            AlertaUtil.mostrarAlertaRegistroExitoso();
            cerrarVentana();
        } catch(SQLIntegrityConstraintViolationException icve){
            
            LOG.error(ConstantesUtil.LOG_ERROR_REGISTRO_DUPLICADO,icve);
            AlertaUtil.mostrarAlerta(AlertaUtil.ERROR, ConstantesUtil.ALERTA_REGISTRO_RFC_MORAL_DUPLICADO, Alert.AlertType.WARNING);
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(AlertaUtil.ALERTA_REGISTRO_FALLIDO, ioe);
            AlertaUtil.mostrarAlertaRegistroFallido();
        } 
    }
    
    private boolean validarCamposResponsableOrganizacion(ResponsableOrganizacionVinculadaDTO responsableOrganizacionVinculadaDTO){
        
        return true;
    }
    
    public void llenarCamposEditablesResponsable(){
        
    }
    
    @FXML
    private void cerrarVentana() {
        
        ManejadorDeVistas.getInstancia().limpiarCache();
        CoordinadorGestionResponsableOrganizacionController controlador;
        
        try {
            
            controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_GESTION_RESPONSABLE_ORGANIZACION);
            controlador.cargarListaResponsablesOrganizacion(organizacionVinculadaDTO);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_RESPONSABLE_ORGANIZACION);
        }catch (IOException ioe){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_VENTANA);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }    
}
