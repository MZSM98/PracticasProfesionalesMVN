package grafica.controladores;

import accesoadatos.dto.ProyectoDTO;
import accesoadatos.dto.OrganizacionVinculadaDTO;
import accesoadatos.dto.ProyectoDTO.EstadoProyecto;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import grafica.utils.AlertaUtil;
import grafica.validadores.ProyectoValidador;
import logica.dao.ProyectoDAO;
import logica.dao.OrganizacionVinculadaDAO;
import logica.interfaces.InterfazProyectoDAO;
import logica.interfaces.InterfazOrganizacionVinculadaDAO;

public class RegistroProyectoController implements Initializable {    
    
    private static final Logger LOG = Logger.getLogger(RegistroProyectoController.class);
    
    @FXML
    private TextField textProyectoID;
    
    @FXML
    private TextField textTituloProyecto;
    
    @FXML
    private TextField textResponsableProyecto;
    
    @FXML
    private ComboBox<String> comboPeriodoEscolar;
    
    @FXML
    private TextArea textDescripcionProyecto;
    
    @FXML
    private ComboBox<OrganizacionVinculadaDTO> comboOrganizacionVinculada;
      
    @FXML 
    private Button botonCancelar;
    
    @FXML
    private Button botonGuardarProyecto;
    
    private InterfazProyectoDAO interfazProyectoDAO;
    private InterfazOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private ProyectoDTO proyectoDTO;
    
    private boolean modoEdicion = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoDAO = new ProyectoDAO();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAO();
        
        
        textProyectoID.setDisable(true);
        
        cargarPeriodosEscolares();
        
        cargarOrganizacionesVinculadas();
    }
    
    private void cargarPeriodosEscolares() {
        // Debo agregar después la lógica e implementación en base de datos para los periodos escolares
        ObservableList<String> periodos = FXCollections.observableArrayList(
            "Febrero 2025 - Junio 2025",
            "Agosto 2025 - Diciembre 2025",
            "Febrero 2026 - Junio 2026"
        );
        comboPeriodoEscolar.setItems(periodos);
    }
    
    private void cargarOrganizacionesVinculadas() {
        
        try {
            
            List<OrganizacionVinculadaDTO> organizaciones = 
                interfazOrganizacionVinculadaDAO.listarOrganizacionesVinculadas();
            
            List<OrganizacionVinculadaDTO> organizacionesActivas = organizaciones.stream()
                .filter(org -> "ACTIVO".equals(org.getEstadoOV()))
                .collect(java.util.stream.Collectors.toList());
            
            comboOrganizacionVinculada.setItems(FXCollections.observableArrayList(organizacionesActivas));
            
            comboOrganizacionVinculada.setCellFactory(param -> new javafx.scene.control.ListCell<OrganizacionVinculadaDTO>() {
                @Override
                protected void updateItem(OrganizacionVinculadaDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombreOV());
                    }
                }
            });
            
            comboOrganizacionVinculada.setButtonCell(new javafx.scene.control.ListCell<OrganizacionVinculadaDTO>() {
                @Override
                protected void updateItem(OrganizacionVinculadaDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombreOV());
                    }
                }
            });
            
        } catch (SQLException e) {
            
            LOG.error("Error al cargar las organizaciones vinculadas: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
        } catch (IOException e) {
            
            LOG.error("Error al cargar las organizaciones vinculadas: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "Error al cargar la información, contacte con un administrador", Alert.AlertType.ERROR);
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
        comboPeriodoEscolar.setValue(proyectoDTO.getPeriodoEscolar());
        
        try {
            
            OrganizacionVinculadaDTO organizacion = 
                interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(proyectoDTO.getRfcMoral());
            
            if (organizacion != null) {
                
                for (OrganizacionVinculadaDTO org : comboOrganizacionVinculada.getItems()) {
                    
                    if (org.getRfcMoral().equals(organizacion.getRfcMoral())) {
                        
                        comboOrganizacionVinculada.setValue(org);
                        textResponsableProyecto.setText(organizacion.getNombreOV());
                        break;
                    }
                }
            }
        } catch (SQLException | IOException e) {
            
            LOG.error("Error al cargar la organización del proyecto: " + e.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudieron cargar las organizaciones, contacte con un administrador", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void guardarProyecto(ActionEvent evento) {
        
        if (!modoEdicion) {
            
            proyectoDTO = new ProyectoDTO();
        }
        
        proyectoDTO.setTituloProyecto(textTituloProyecto.getText().trim());
        proyectoDTO.setDescripcionProyecto(textDescripcionProyecto.getText().trim());
        proyectoDTO.setPeriodoEscolar(comboPeriodoEscolar.getValue());
        OrganizacionVinculadaDTO organizacionSeleccionada = comboOrganizacionVinculada.getValue();
        
        if (organizacionSeleccionada != null) {
            
            proyectoDTO.setRfcMoral(organizacionSeleccionada.getRfcMoral());
        }
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
                limpiarCampos();
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
        
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void limpiarCampos() {
        
        textProyectoID.setText("");
        textTituloProyecto.setText("");
        textDescripcionProyecto.setText("");
        comboPeriodoEscolar.setValue(null);
        comboOrganizacionVinculada.setValue(null);
        textResponsableProyecto.setText("");
    }
}