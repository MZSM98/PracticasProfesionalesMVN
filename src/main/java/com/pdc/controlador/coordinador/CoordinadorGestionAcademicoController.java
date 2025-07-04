package com.pdc.controlador.coordinador;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.ProfesorExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.TipoUsuarioDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.ITipoUsuarioDAO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import java.io.IOException;

public class CoordinadorGestionAcademicoController implements Initializable{
    
    private static final Logger LOG = Logger.getLogger(CoordinadorGestionAcademicoController.class);
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;

    @FXML
    private TableColumn<UsuarioDTO, String> columnNombre;

    @FXML
    private TableColumn<UsuarioDTO, String> columnNumeroEmpleado;
    
    @FXML
    private TableColumn<UsuarioDTO, String> columnSeccion;

    @FXML
    private TableView<UsuarioDTO> tableGestionAcademicos;
    
    @FXML
    private ComboBox<TipoUsuarioDTO> comboTipoAcademico;
    
    private IAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private IProfesorExperienciaEducativaDAO interfazProfesorExperienciaEducativaDAO;
    private ITipoUsuarioDAO interfazTipoUsuarioDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
       interfazProfesorExperienciaEducativaDAO = new ProfesorExperienciaEducativaDAOImpl();
       interfazTipoUsuarioDAO = new TipoUsuarioDAOImpl();
       configurarComboBox();
       configurarColumnas();
       cargarListaAcademico();
    }
    
    private void configurarColumnas(){
        
        tableGestionAcademicos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
        
        if(ACADEMICO_EVALUADOR.equals(tipoUsuarioSeleccionado.getIdTipo())){
            
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAcademico"));
            columnNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<>("numeroDeTrabajador"));
            tableGestionAcademicos.getColumns().remove(columnSeccion);
        }else if(PROFESOR_EE.equals(tipoUsuarioSeleccionado.getIdTipo())){
            
            tableGestionAcademicos.getColumns().add(columnSeccion);
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
            columnNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<>("numeroTrabajador"));
            columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccion"));
        }
    }
    
    private void configurarComboBox(){
        
        try {
            
            TipoUsuarioDTO tipoUsuarioAcademicoEvaluador = interfazTipoUsuarioDAO.buscarTipoUsuario(ACADEMICO_EVALUADOR);
            TipoUsuarioDTO tipoUsuarioProfesorEE = interfazTipoUsuarioDAO.buscarTipoUsuario(PROFESOR_EE);

            ObservableList<TipoUsuarioDTO> listaObservableAcademico = FXCollections.observableArrayList();
            listaObservableAcademico.add(tipoUsuarioAcademicoEvaluador);
            listaObservableAcademico.add(tipoUsuarioProfesorEE);
            comboTipoAcademico.setItems(listaObservableAcademico);
            comboTipoAcademico.getSelectionModel().selectFirst();
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    private void cargarListaAcademico(){
        
        try {
            
            List<AcademicoEvaluadorDTO> listaAcademicoEvaluador;
            listaAcademicoEvaluador = interfazAcademicoEvaluadorDAO.listarAcademicoEvaluador();
            tableGestionAcademicos.setItems(FXCollections.observableArrayList(listaAcademicoEvaluador));
        } catch (SQLException sqle) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }
    
    private void cargarListaProfesorExperienciaEducativa(){
        
            try {
            List<ProfesorExperienciaEducativaDTO> listaProfesorExperienciaEducativa;
            listaProfesorExperienciaEducativa = interfazProfesorExperienciaEducativaDAO.listaProfesorEE();
            tableGestionAcademicos.setItems(FXCollections.observableArrayList(listaProfesorExperienciaEducativa));
        } catch (SQLException e) {
            
            LOG.error(AlertaUtil.ALERTA_ERROR_BD, e);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }    

    @FXML
    private void abrirRegistrarAcademico(ActionEvent event) {
        
        try {
            
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ACADEMICO);
            CoordinadorRegistroAcademicoController controlador;
            controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ACADEMICO);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REGISTRO_ACADEMICO);
            TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
            controlador.asignarTipoUsuario(tipoUsuarioSeleccionado);

            if (tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)) {
                cargarListaAcademico();
            } else if (tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)) {
                cargarListaProfesorExperienciaEducativa();
            }

        } catch (IOException e) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, e);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }

    @FXML
    private void abrirEditarAcademico(ActionEvent event) {
        
        UsuarioDTO academicoSeleccionado = tableGestionAcademicos.getSelectionModel().getSelectedItem();
        
        if (academicoSeleccionado == null) {
            
            AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, ConstantesUtil.ALERTA_SELECCION_EDITAR, Alert.AlertType.WARNING);
            return;
        }
        try {
            
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_ACTUALIZA_ACADEMICO);
            CoordinadorRegistroAcademicoController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_ACTUALIZA_ACADEMICO);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_ACTUALIZA_ACADEMICO);
            
            controlador.cambiarAModoEdicion(true);
                
            TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();
            controlador.asignarTipoUsuario(tipoUsuarioSeleccionado);
            controlador.llenarCamposEditablesAcademico(academicoSeleccionado);
            
            if (tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)) {
                cargarListaAcademico();
            } else if (tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)) {
                cargarListaProfesorExperienciaEducativa();
            }
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }

    @FXML
    void cambiaTipoAcademico(ActionEvent event) {
        
       configurarColumnas();
       
        TipoUsuarioDTO tipoUsuarioSeleccionado = comboTipoAcademico.getSelectionModel().getSelectedItem();

        if(tipoUsuarioSeleccionado.getIdTipo().equals(ACADEMICO_EVALUADOR)){
            
            cargarListaAcademico();
        }else if(tipoUsuarioSeleccionado.getIdTipo().equals(PROFESOR_EE)){
            
            cargarListaProfesorExperienciaEducativa();
        }
    }
    
    @FXML
    void salirAMenuPrincipal(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
    }

}
