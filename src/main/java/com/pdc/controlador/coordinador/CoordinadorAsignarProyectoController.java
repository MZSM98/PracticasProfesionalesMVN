package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import static java.awt.SystemColor.text;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CoordinadorAsignarProyectoController implements Initializable {
    
    private static final Logger LOG = LogManager.getLogger(CoordinadorAsignarProyectoController.class);
    

    @FXML
    private TableColumn<EstudianteDTO, String> columnaMatricula;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaMatriculaAsignado;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaNombre;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaNombreAsginado;
    
    @FXML
    private ComboBox<ProyectoDTO> comboProyecto;

    @FXML
    private TableView<EstudianteDTO> tablaAsignados;

    @FXML
    private TableView<EstudianteDTO> tablaSinAsignar;
    
    private IProyectoDAO interfazProyectoDAO;
    private IEstudianteDAO interfazEstudianteDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
        configurarTablasEstudiante();
        llenarDatosComboProyecto();
    }    

    @FXML
    private void accionAsignar(ActionEvent event) {
        
    }

    @FXML
    private void accionCambioComboProyecto(ActionEvent event) {
        
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
    }

    @FXML
    private void accionGuardar(ActionEvent event) {
        
    }
    
    private void llenarDatosComboProyecto(){
        try {
            comboProyecto.setItems(FXCollections.observableArrayList(interfazProyectoDAO.listarProyectos()));
        } catch (SQLException ex) {
                LOG.error(ex);
        } catch (IOException ex) {
                LOG.error(ex);
        }
    }
    
    private void configurarTablasEstudiante(){
       columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
       columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
       columnaMatriculaAsignado.setCellValueFactory(new PropertyValueFactory<>("matricula"));
       columnaNombreAsginado.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
    }
    
}
