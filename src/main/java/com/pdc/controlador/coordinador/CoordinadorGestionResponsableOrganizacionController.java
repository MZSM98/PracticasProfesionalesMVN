package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.implementacion.ResponsableOrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
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
    
    @FXML
    private ObservableList<ResponsableOrganizacionVinculadaDTO> listaResponsables;
    
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
        columnCargoResponsable.setCellValueFactory(new PropertyValueFactory<>("cargoResponsable"));
    }
    
    @FXML
    public void cargarListaResponsablesOrganizacion(OrganizacionVinculadaDTO organizacionVinculadaDTO){
        
        this.organizacionVinculadaDTO = organizacionVinculadaDTO;
        
        try {
            
        List<ResponsableOrganizacionVinculadaDTO> responsables
                = interfazResponsableOrganizacionVinculadaDAO.listarResponsablesPorOrganizacion(organizacionVinculadaDTO.getRfcMoral());
            listaResponsables = FXCollections.observableArrayList(responsables);
            tableResponsablesOrganizacion.setItems(listaResponsables);
        }catch(SQLException sqle){
            
            LOG.error(sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }catch (IOException ioe){
            
            LOG.error(ioe);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    private void abrirRegistrarResponsableOrganizacion(){
        
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_REPRESENTANTE_ORGANIZACION_VINCULADA);
    }
    
    private void abrirEditarResponsableOrganizacion(){
        
    }
    
    private void salirAGestionOrganizacionVinculada(){
        
    }
    
    private void eliminarResponsableOrganizacion(){
        
    }
    
}
