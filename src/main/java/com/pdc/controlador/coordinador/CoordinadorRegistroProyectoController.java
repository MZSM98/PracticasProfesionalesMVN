package com.pdc.controlador.coordinador;

import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.ProyectoDTO.EstadoProyecto;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.validador.ProyectoValidador;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.implementacion.PeriodoEscolarDAOImpl;
import com.pdc.dao.implementacion.ResponsableOrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.sql.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class CoordinadorRegistroProyectoController implements Initializable {    
    
    private static final Logger LOG = Logger.getLogger(CoordinadorRegistroProyectoController.class);
    
    @FXML
    private TextField textProyectoID;
    
    @FXML
    private TextField textTituloProyecto;
    
    @FXML
    private ComboBox <ResponsableOrganizacionVinculadaDTO> comboResponsableProyecto;
    
    @FXML
    private ComboBox<PeriodoEscolarDTO> comboPeriodoEscolar;
    
    @FXML
    private TextArea textDescripcionProyecto;
    
    @FXML
    private ComboBox<OrganizacionVinculadaDTO> comboOrganizacionVinculada;
    
    @FXML
    private DatePicker dateInicioProyecto;
    
    @FXML
    private DatePicker dateFinalProyecto;
    
    @FXML
    private Spinner<Integer> spinnerVacantes;
    
    @FXML
    private TextArea textCronogramaMesUno;
    
    @FXML
    private TextArea textCronogramaMesDos;
    
    @FXML
    private TextArea textCronogramaMesTres;
    
    @FXML
    private TextArea textCronogramaMesCuatro;
    
    @FXML
    private Button botonGuardarProyecto;
    
    private IProyectoDAO interfazProyectoDAO;
    private IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private ProyectoDTO proyectoDTO;
    private IPeriodoEscolarDAO interfazPeriodoEscolar;
    private IResponsableOrganizacionVinculadaDAO interfazResponsableOrganizacionVinculadaDAO;
    
    private boolean modoEdicion = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazPeriodoEscolar = new PeriodoEscolarDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazResponsableOrganizacionVinculadaDAO = new ResponsableOrganizacionVinculadaDAOImpl();
        textProyectoID.setDisable(true);
        cargarPeriodosEscolares();
        cargarOrganizacionesVinculadas();
        configurarSpinnerVacantes();
    }
    
    private void configurarSpinnerVacantes(){
        
        SpinnerValueFactory<Integer> valueFactory;
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 1, 1);
        spinnerVacantes.setValueFactory(valueFactory);
    }
    
    private void cargarPeriodosEscolares() {
        try {
            
        List<PeriodoEscolarDTO> periodosEscolares = interfazPeriodoEscolar.listarPeriodos();
        comboPeriodoEscolar.setItems(FXCollections.observableArrayList(periodosEscolares));
        }catch (SQLException sqle) {
            
            AlertaUtil.mostrarAlertaBaseDatos();
        }catch (IOException ioe){
            
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    private void cargarOrganizacionesVinculadas() {
        
        try {
            
            List<OrganizacionVinculadaDTO> organizaciones;
            organizaciones = interfazOrganizacionVinculadaDAO.listarOrganizacionesVinculadas();

            List<OrganizacionVinculadaDTO> organizacionesActivas;
            organizacionesActivas = organizaciones.stream()
                .filter(organizacion -> "ACTIVO".equals(organizacion.getEstadoOV()))
                .collect(Collectors.toList());
            
            comboOrganizacionVinculada.setItems(FXCollections.observableArrayList(organizacionesActivas));
            
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_CARGAR_INFORMACION, ioe);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
        
    }
    
    @FXML
    private void accionComboOrganizacionVinculada(){
        
        if (Objects.nonNull(comboOrganizacionVinculada.getSelectionModel().getSelectedItem())){
            
            cargarResponsablesOrganizacion();
        }
    }
    
    private void cargarResponsablesOrganizacion(){
        
        OrganizacionVinculadaDTO organizacionSeleccionada;
        organizacionSeleccionada= comboOrganizacionVinculada.getSelectionModel().getSelectedItem();
        
        String rfcOrganizacionSeleccionada;
        rfcOrganizacionSeleccionada = organizacionSeleccionada.getRfcMoral();
        
        try {
            
            List<ResponsableOrganizacionVinculadaDTO> responsables;
            responsables = interfazResponsableOrganizacionVinculadaDAO.listarResponsablesPorOrganizacion(rfcOrganizacionSeleccionada);
            comboResponsableProyecto.setItems(FXCollections.observableArrayList(responsables));
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_CARGAR_INFORMACION, ioe);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
        
    }
    
    public void cambiarAModoEdicion(boolean modoEdicion) {
        
        this.modoEdicion = modoEdicion;
        
        if (modoEdicion) {
            
            botonGuardarProyecto.setText("Actualizar");
        }
    }
    
    public void llenarCamposEditablesProyecto(ProyectoDTO proyectoDTO) {
        
        this.proyectoDTO = proyectoDTO;
        
        textProyectoID.setText(String.valueOf(proyectoDTO.getProyectoID()));
        textTituloProyecto.setText(proyectoDTO.getTituloProyecto());
        textDescripcionProyecto.setText(proyectoDTO.getDescripcionProyecto());
        comboPeriodoEscolar.getSelectionModel().select(proyectoDTO.getPeriodoEscolar());
        spinnerVacantes.getValueFactory().setValue(proyectoDTO.getVacantes());
        textCronogramaMesUno.setText(String.valueOf(proyectoDTO.getCronogramaMesUno()));
        textCronogramaMesDos.setText(String.valueOf(proyectoDTO.getCronogramaMesDos()));
        textCronogramaMesTres.setText(String.valueOf(proyectoDTO.getCronogramaMesTres()));
        textCronogramaMesCuatro.setText(String.valueOf(proyectoDTO.getCronogramaMesCuatro()));
        
        try {
            
            OrganizacionVinculadaDTO organizacion = 
                    interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(proyectoDTO.getOrganizacion().getRfcMoral());
            ResponsableOrganizacionVinculadaDTO responsable = 
                    interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(organizacion.getRfcMoral());
                
            for (OrganizacionVinculadaDTO org : comboOrganizacionVinculada.getItems()) {
                    
                if (org.getRfcMoral().equals(organizacion.getRfcMoral())) {
                        
                        comboOrganizacionVinculada.setValue(org);
                        comboResponsableProyecto.setValue(responsable);
                        break;
                }
            }
            
        }catch(SQLException sqle){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD + sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        }catch(IOException ioe){
            
            LOG.error(AlertaUtil.ALERTA_ERROR_CARGAR_INFORMACION);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    @FXML
    private void guardarProyecto(ActionEvent evento) {
        
        if (!modoEdicion) {
            
            proyectoDTO = new ProyectoDTO();
        }
        
        proyectoDTO.setTituloProyecto(textTituloProyecto.getText().trim());
        proyectoDTO.setDescripcionProyecto(textDescripcionProyecto.getText().trim());
        proyectoDTO.setPeriodoEscolar(comboPeriodoEscolar.getSelectionModel().getSelectedItem());
        proyectoDTO.setFechaInicio(Date.valueOf(dateInicioProyecto.getValue()));
        proyectoDTO.setFechaFinal(Date.valueOf(dateFinalProyecto.getValue()));
        proyectoDTO.setResponsable(comboResponsableProyecto.getSelectionModel().getSelectedItem());
        proyectoDTO.setOrganizacion(comboOrganizacionVinculada.getSelectionModel().getSelectedItem());
        proyectoDTO.setVacantes(spinnerVacantes.getValue());
        proyectoDTO.setCronogramaMesUno(textCronogramaMesUno.getText().trim());
        proyectoDTO.setCronogramaMesDos(textCronogramaMesDos.getText().trim());
        proyectoDTO.setCronogramaMesTres(textCronogramaMesTres.getText().trim());
        proyectoDTO.setCronogramaMesCuatro(textCronogramaMesCuatro.getText().trim());
        
        
        if (!validarCampos(proyectoDTO)) {
            return;
        }
        if (modoEdicion) {
            
            actualizarProyecto(proyectoDTO);
        } else {
            
            crearNuevoProyecto(proyectoDTO);
        }
    }
    
    private boolean validarCampos(ProyectoDTO proyectoDTO) {
               
        try {
            
            ProyectoValidador.validarProyecto(proyectoDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error ("Se ingresaron datos inválidos");
            AlertaUtil.mostrarAlerta("Datos Inválidos o Incompletos", iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        }
    }
    
    private void crearNuevoProyecto(ProyectoDTO proyectoDTO) {
        
        proyectoDTO.setEstadoProyecto(EstadoProyecto.ACTIVO.name());
        
        try {
            
            boolean exito = interfazProyectoDAO.insertarProyecto(proyectoDTO);
            
            if (exito) {
                
                AlertaUtil.mostrarAlerta("Éxito", "Proyecto registrado correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                
                AlertaUtil.mostrarAlerta("Error", "No se pudo registrar el proyecto", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException sqle) {
            
            LOG.error("Error con la conexión de base de datos", sqle);
            AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error("Error al registrar el proyecto", ioe);
            AlertaUtil.mostrarAlerta("Error", "Error al registrar el proyecto", Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarProyecto(ProyectoDTO proyectoDTO) {
        
        try {
            
            boolean actualizacionExitosa = interfazProyectoDAO.editarProyecto(proyectoDTO);
            
            if (actualizacionExitosa) {
                
                AlertaUtil.mostrarAlerta("Éxito", "Proyecto actualizado correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                
                AlertaUtil.mostrarAlerta("Error", "No se pudo actualizar el proyecto", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException sqle) {
            
            LOG.error("Error con la conexión de base de datos", sqle);
            AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error("Error al actualizar el proyecto", ioe);
            AlertaUtil.mostrarAlerta("Error", "Error al actualizar el proyecto", Alert.AlertType.ERROR);
        }
        
    }
    
    @FXML
    private void cancelarRegistro(ActionEvent evento) {
        
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_GESTION_PROYECTO);
    }
    
}