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
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.GmailUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CoordinadorAsignarProyectoController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(CoordinadorAsignarProyectoController.class);

    @FXML
    private Button botonAsignar;

    @FXML
    private Button botonGuardar;
    
    @FXML
    private Button botonReiniciar;

    @FXML
    private ComboBox<OrganizacionVinculadaDTO> comboOrganizacionVinculada;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaMatricula;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaMatriculaAsignado;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaNombre;

    @FXML
    private TableColumn<EstudianteDTO, String> columnaNombreAsginado;
    
    
    @FXML
    private TableColumn<EstudianteDTO, Integer> columnaAvanceCrediticio;

    @FXML
    private TableColumn<EstudianteDTO, Integer> columnaAvanceCrediticioAsignado;
    
    @FXML
    private TableColumn<EstudianteDTO, Double> columnaPromedio;

    @FXML
    private TableColumn<EstudianteDTO, Double> columnaPromedioAsignado;

    @FXML
    private ComboBox<ProyectoDTO> comboProyecto;

    @FXML
    private TableView<EstudianteDTO> tablaAsignados;

    @FXML
    private TableView<EstudianteDTO> tablaSinAsignar;
    
    @FXML
    private Label labelVacantes;

    private List<ProyectoAsignadoDTO> listaProyectosPorAsignar;

    private IProyectoDAO interfazProyectoDAO;
    private IEstudianteDAO interfazEstudianteDAO;
    private IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        listaProyectosPorAsignar = new ArrayList<>();
        configurarTablasEstudiante();
        llenarDatosComboOrganizacionesVinculadas();
    }

    @FXML
    private void asignarProyecto(ActionEvent event) {
        
        EstudianteDTO estudianteSeleccionado = tablaSinAsignar.getSelectionModel().getSelectedItem();
        ProyectoDTO proyectoSeleccionado = comboProyecto.getSelectionModel().getSelectedItem();
        
        if (Objects.isNull(estudianteSeleccionado)) {
            
            AlertaUtil.mostrarAlerta("Alerta", "Debe seleccionar un registro", Alert.AlertType.WARNING);
        }else if(validaVacantesProyecto(proyectoSeleccionado)){
            
            tablaAsignados.getItems().add(estudianteSeleccionado);
            tablaSinAsignar.getItems().remove(estudianteSeleccionado);
            ProyectoAsignadoDTO proyectoAsignado;
            proyectoAsignado = new ProyectoAsignadoDTO();
            proyectoAsignado.setEstudiante(estudianteSeleccionado);
            proyectoAsignado.setProyecto(proyectoSeleccionado);
            listaProyectosPorAsignar.add(proyectoAsignado);
            botonReiniciar.setDisable(Boolean.FALSE);
            actualizarVacantes();
        }
    }

    @FXML
    private void accionCambioComboProyecto(ActionEvent event) {
        
        ProyectoDTO proyecto;
        proyecto = comboProyecto.getSelectionModel().getSelectedItem();
        
        if (Objects.nonNull(proyecto)) {
            
            botonAsignar.setDisable(Boolean.FALSE);
            botonGuardar.setDisable(Boolean.FALSE);
            llenarTablaEstudianteSinAsignar();
            llenarTablaEstudiantesAsignados();
            actualizarVacantes();
        } else {
            
            botonAsignar.setDisable(Boolean.TRUE);
            botonGuardar.setDisable(Boolean.TRUE);
            actualizarVacantes();
        }
    }

    @FXML
    private void llenarComboProyectosPorOrganizacion(ActionEvent event) {
        
        OrganizacionVinculadaDTO organizacionSeleccionada;
        organizacionSeleccionada = comboOrganizacionVinculada.getSelectionModel().getSelectedItem();
        
        if (Objects.nonNull(organizacionSeleccionada)) {
            
            llenarDatosComboProyecto();
            llenarTablaEstudianteSinAsignar();
            llenarTablaEstudiantesAsignados();
            comboProyecto.setDisable(Boolean.FALSE);
        }
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_ASIGNAR_PROYECTO);
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_PROYECTO_ASIGNADO);
    }

    @FXML
    private void guardarAsignacion(ActionEvent event) {
        
        ProyectoDTO proyectoSeleccionado;
        proyectoSeleccionado = comboProyecto.getSelectionModel().getSelectedItem();

        if (Objects.nonNull(proyectoSeleccionado) && !listaProyectosPorAsignar.isEmpty()) {
            
            for (ProyectoAsignadoDTO proyectoAsignado : listaProyectosPorAsignar) {
                
                try {
                    
                    interfazProyectoAsignadoDAO.insertarProyectoAsignado(proyectoAsignado);
                    informarConCorreo(proyectoAsignado.getEstudiante(), proyectoSeleccionado.getResponsable(), proyectoSeleccionado);
                } catch (SQLException sqle){
                    
                    LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
                    AlertaUtil.mostrarAlertaBaseDatos();
                }
            }
            AlertaUtil.mostrarAlertaRegistroExitoso();
            accionCancelar(event);
        }else{
            
            AlertaUtil.mostrarAlerta("Advertencia", "No hay cambios por guardar", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void reiniciarAsignacionEnCurso(ActionEvent event) {
        
        if(!listaProyectosPorAsignar.isEmpty()){
            
            llenarTablaEstudianteSinAsignar();
            llenarTablaEstudiantesAsignados();
            actualizarVacantes();
            listaProyectosPorAsignar.clear();
            botonReiniciar.setDisable(Boolean.FALSE);
        }
    }
    
    private boolean validaVacantesProyecto(ProyectoDTO proyectoSeleccionado){
        
        Integer vacantes = proyectoSeleccionado.getVacantes();
        Integer actuales = tablaAsignados.getItems().size()+1;
        
        if(actuales > vacantes){
            
            AlertaUtil.mostrarAlerta("Advertencia", "Se ha alcanzado el limite de vacantes: "+ vacantes, Alert.AlertType.WARNING);
            return false;
        }else{
            return true;
        }
    }

    private void llenarDatosComboProyecto() {
        
        OrganizacionVinculadaDTO organizacionVinculadaSeleccion = comboOrganizacionVinculada.getSelectionModel().getSelectedItem();
        try {
            
            String rfcMoral = organizacionVinculadaSeleccion.getRfcMoral();
            comboProyecto.setItems(FXCollections.observableArrayList(interfazProyectoDAO.listarProyectosPorOv(rfcMoral)));
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }

    private void llenarDatosComboOrganizacionesVinculadas() {
        
        try {
            
            List<OrganizacionVinculadaDTO> organizacionesActivas = interfazOrganizacionVinculadaDAO.listarOrganizacionesVinculadas()
                .stream()
                .filter(organizacion -> "ACTIVO".equalsIgnoreCase(organizacion.getEstadoOV()))
                .collect(Collectors.toList());
            comboOrganizacionVinculada.setItems(FXCollections.observableArrayList(organizacionesActivas));
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }

    private void llenarTablaEstudianteSinAsignar() {
        
        ProyectoDTO proyectoSeleccionado;
        proyectoSeleccionado = comboProyecto.getSelectionModel().getSelectedItem();
        
        if (Objects.isNull(proyectoSeleccionado)) {
            tablaSinAsignar.getItems().clear();
            return;
        }
        try {
            
            tablaSinAsignar.setItems(FXCollections.observableArrayList(interfazEstudianteDAO.listarEstudiantesSinProyectoAsignado()));
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }

    private void llenarTablaEstudiantesAsignados() {
        
        ProyectoDTO proyectoSeleccionado;
        proyectoSeleccionado = comboProyecto.getSelectionModel().getSelectedItem();
        
        if (Objects.isNull(proyectoSeleccionado)) {
            tablaAsignados.getItems().clear();
            listaProyectosPorAsignar.clear();
            return;
        }
        try {
            
            List<ProyectoAsignadoDTO> listaProyectoAsignado = interfazProyectoAsignadoDAO.listaProyectoAsignadoPorProyectoID(proyectoSeleccionado.getProyectoID());
            List<EstudianteDTO> listaEstudianteAsignado = new ArrayList<>();
            for (ProyectoAsignadoDTO proyectoAsignado : listaProyectoAsignado) {
                listaEstudianteAsignado.add(proyectoAsignado.getEstudiante());
            }
            tablaAsignados.setItems(FXCollections.observableArrayList(listaEstudianteAsignado));
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }
    }

    private void configurarTablasEstudiante() {
        
        columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        columnaMatriculaAsignado.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaNombreAsginado.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        columnaAvanceCrediticio.setCellValueFactory(new PropertyValueFactory<>("avanceCrediticio"));
        columnaAvanceCrediticioAsignado.setCellValueFactory(new PropertyValueFactory<>("avanceCrediticio"));
        columnaPromedio.setCellValueFactory(new PropertyValueFactory<>("promedio"));
        columnaPromedioAsignado.setCellValueFactory(new PropertyValueFactory<>("promedio"));
    }

    private void actualizarVacantes(){
        
        ProyectoDTO proyecto = comboProyecto.getSelectionModel().getSelectedItem();
        if(Objects.nonNull(proyecto)){
            labelVacantes.setText(String.format("%d / %d", tablaAsignados.getItems().size(), proyecto.getVacantes()));
        }else{
            labelVacantes.setText(String.format("%d / %d", 0, 0));
        }
    }
    
    private void informarConCorreo(EstudianteDTO estudiante, ResponsableOrganizacionVinculadaDTO responsable, ProyectoDTO proyecto){
        //GmailUtil.enviarCorreoHTML(estudiante.getCorreo(), asunto, nombre, mensaje);
        try {
            
            GmailUtil.enviarCorreoHTML(responsable.getCorreoResponsable(), 
                    "Proyecto asignado", responsable.getNombreResponsable(), 
                    String.format("El estudiante %s con matricula %s ha sido asignado al proyecto %s",
                    estudiante.getNombreEstudiante(),
                    estudiante.getMatricula(),
                    proyecto.getTituloProyecto()));
        } catch (Exception e) {
            LOG.error(e);
        }
        

    }
}
