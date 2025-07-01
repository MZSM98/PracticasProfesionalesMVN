package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CoordinadorReasignarProyectoController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(CoordinadorReasignarProyectoController.class);

    @FXML
    private ComboBox<OrganizacionVinculadaDTO> comboOrganizacionVinculada;

    @FXML
    private ComboBox<ProyectoDTO> comboProyecto;

    @FXML
    private TextField textMatricula;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textOrganizacionActual;
    
    @FXML
    private TextField textProyectoActual;

    private IProyectoDAO interfazProyectoDAO;
    private IEstudianteDAO interfazEstudianteDAO;
    private IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    
    private ProyectoAsignadoDTO proyectoAsginado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        llenarDatosComboOrganizacionesVinculadas();
    }

    public void asignarDatosVentana(ProyectoAsignadoDTO proyectoAsginado) {
        this.proyectoAsginado = proyectoAsginado;
        EstudianteDTO estudiante = proyectoAsginado.getEstudiante();
        ProyectoDTO proyecto = proyectoAsginado.getProyecto();
        OrganizacionVinculadaDTO organizacionVinculada = proyecto.getOrganizacion();
        textMatricula.setText(estudiante.getMatricula());
        textNombre.setText(estudiante.getNombreEstudiante());
        textProyectoActual.setText(proyecto.getTituloProyecto());
        textOrganizacionActual.setText(organizacionVinculada.getRfcMoral().concat(" - "+organizacionVinculada.getNombreOV()));
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_REASIGNAR_PROYECTO);
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
    }

    @FXML
    private void accionGuardar(ActionEvent event) {
        
        ProyectoDTO proyectoSeleccionado = comboProyecto.getSelectionModel().getSelectedItem();
        if (Objects.isNull(proyectoSeleccionado)) {
            AlertaUtil.mostrarAlerta("Advertencia", "Debe seleccionar un proyecto.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            proyectoAsginado.setProyecto(proyectoSeleccionado);
            interfazProyectoAsignadoDAO.editarProyectoAsignado(proyectoAsginado);
        } catch (SQLException ex) {
            
            LOG.error(ex);
            AlertaUtil.mostrarAlertaError("No se pudo reasignar el proyecto");
        } 
    }
    
    @FXML
    private void accionComboOrganizacionVinculada(ActionEvent event) {
        if (Objects.nonNull(comboOrganizacionVinculada.getSelectionModel().getSelectedItem())) {
            llenarDatosComboProyecto();
        }
    }

    private void llenarDatosComboProyecto() {
        OrganizacionVinculadaDTO organizacionVinculadaSeleccion = comboOrganizacionVinculada.getSelectionModel().getSelectedItem();
        try {
            String rfcMoral = organizacionVinculadaSeleccion.getRfcMoral();
            comboProyecto.setItems(FXCollections.observableArrayList(interfazProyectoDAO.listarProyectosPorOv(rfcMoral)));
            comboProyecto.getItems().remove(proyectoAsginado.getProyecto());
        } catch (SQLException ex) {
            LOG.error(ex);
        } 
    }

    private void llenarDatosComboOrganizacionesVinculadas() {
        try {
            comboOrganizacionVinculada.setItems(FXCollections.observableArrayList(interfazOrganizacionVinculadaDAO.listarOrganizacionesVinculadas()));
        } catch (SQLException ex) {
            LOG.error(ex);
        } 
    }
}
