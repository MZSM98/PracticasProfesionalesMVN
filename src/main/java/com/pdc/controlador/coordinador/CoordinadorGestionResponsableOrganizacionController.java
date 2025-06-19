package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.ResponsableOrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;

public class CoordinadorGestionResponsableOrganizacionController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionResponsableOrganizacionController.class);

    @FXML
    private TableColumn <ResponsableOrganizacionVinculadaDTO, String> columnRfc;
    
    @FXML
    private TableColumn <ResponsableOrganizacionVinculadaDTO, String> columnNombreResponsable;
    
    @FXML
    private TableColumn <ResponsableOrganizacionVinculadaDTO, String> columnCorreoResponsable;
    
    @FXML
    private TableColumn <ResponsableOrganizacionVinculadaDTO, String> columnCargoResponsable;
    
    @FXML
    private TableView <ResponsableOrganizacionVinculadaDTO> tableResponsablesOrganizacion;
    
    private IResponsableOrganizacionVinculadaDAO interfazResponsableOrganizacionVinculadaDAO;
    private OrganizacionVinculadaDTO organizacionVinculadaDTO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazResponsableOrganizacionVinculadaDAO = new ResponsableOrganizacionVinculadaDAOImpl();
        configurarColumnas();
    }

    private void configurarColumnas(){
        
        columnRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        columnNombreResponsable.setCellValueFactory(new PropertyValueFactory<>("nombreResponsable"));
        columnCorreoResponsable.setCellValueFactory(new PropertyValueFactory<>("correoResponsable"));
        columnCargoResponsable.setCellValueFactory(new PropertyValueFactory<>("cargo"));
    }
    
    @FXML
    public void cargarListaResponsablesOrganizacion(OrganizacionVinculadaDTO organizacionVinculadaDTO){
        
        this.organizacionVinculadaDTO = organizacionVinculadaDTO;
        
        ObservableList<ResponsableOrganizacionVinculadaDTO> listaResponsablesOrganizacion;
        
        try {            
            
            List<ResponsableOrganizacionVinculadaDTO> responsables =
                    interfazResponsableOrganizacionVinculadaDAO.listarResponsablesPorOrganizacion(organizacionVinculadaDTO.getRfcMoral());
            listaResponsablesOrganizacion = FXCollections.observableArrayList(responsables);
            tableResponsablesOrganizacion.setItems(listaResponsablesOrganizacion);            
        } catch (SQLException sqle) {
            
            LOG.error("Error al cargar las organizaciones vinculadas: " + sqle.getMessage());
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe){
            
            LOG.error("No se lograron cargar los registros" + ioe.getMessage());
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    @FXML
    private void abrirRegistrarResponsableOrganizacion(){
        
        try {
            
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_RESPONSABLE_ORGANIZACION_VINCULADA);
            CoordinadorRegistroResponsableOrganizacionVinculadaController controlador;
            controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_RESPONSABLE_ORGANIZACION_VINCULADA);
            controlador.establecerOrganizacionVinculada(organizacionVinculadaDTO);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_RESPONSABLE_ORGANIZACION_VINCULADA);
        } catch (IOException ioe) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_CARGAR_INFORMACION, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
        
    }
    
    @FXML
    private void abrirEditarResponsableOrganizacion(){
        
    }
    
    @FXML
    private void cerrarVentana(){
        
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_ORGANIZACION_VINCULADA);
    }
    
    @FXML
    private void eliminarResponsableOrganizacion(){
        
    }
    
}
