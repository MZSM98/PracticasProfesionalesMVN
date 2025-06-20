package com.pdc.controlador.estudiante;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.AlertaUtil;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import org.apache.log4j.Logger;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;

public class EstudianteGestionController implements Initializable {
    private static final Logger LOG = Logger.getLogger(EstudianteGestionController.class);

    @FXML
    private TableColumn<EstudianteDTO, Integer> columnAvance;

    @FXML
    private TableColumn<EstudianteDTO, String> columnMatricula;

    @FXML
    private TableColumn<EstudianteDTO, String> columnNombre;

    @FXML
    private TableColumn<EstudianteDTO, String> columnPeriodo;

    @FXML
    private TableColumn<EstudianteDTO, Double> columnPromedio;

    @FXML
    private TableColumn<EstudianteDTO, String> columnSeccion;

    @FXML
    private TableView<EstudianteDTO> tableEstudiantes;
    
    private IEstudianteDAO interfazEstudianteDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDAO = new EstudianteDAOImpl();
        configurarColumnasTablaEstudiante();
        poblarTablaEstudiante();
    }    
    
    @FXML
    void abrirEditarAcademico(ActionEvent event) {
        EstudianteDTO estudianteSeleccionado = tableEstudiantes.getSelectionModel().getSelectedItem();
        
        if (estudianteSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Aviso", "Por favor, seleccione un registro para editar", Alert.AlertType.WARNING);
            return;
        }
        try {
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);
            EstudianteRegistroController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);
            controlador.setModoEdicion(Boolean.TRUE);
            controlador.llenarCamposEditablesEstudiante(estudianteSeleccionado);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void abrirRegistrarAcademico(ActionEvent event) {
       
        try {
            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO);
            EstudianteRegistroController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO);
            controlador.poblarInformacionComboPeriodoySeccion();
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO);
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana de edición: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana de edición.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void salirAMenuPrincipal(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        final Integer COORDINADOR = 1;
        final Integer PROFESOREE = 3;
        ManejadorDeVistas.getInstancia().limpiarCache();
        if(COORDINADOR.equals(ManejadorDeSesion.getUsuario().getTipoUsuario().getIdTipo())){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
        }else if(PROFESOREE.equals(ManejadorDeSesion.getUsuario().getTipoUsuario().getIdTipo())){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_MENU_PRINCIPAL);
        }
    }

    private void configurarColumnasTablaEstudiante(){
        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        columnPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccionEstudiante"));
        columnAvance.setCellValueFactory(new PropertyValueFactory<>("avanceCrediticio"));
        columnPromedio.setCellValueFactory(new PropertyValueFactory<>("promedio"));
    }
    private void poblarTablaEstudiante(){
        
        try {
            List<EstudianteDTO> listaEstudiantes = interfazEstudianteDAO.listarEstudiantes();
            ObservableList<EstudianteDTO> listaObservableEstudiantes = FXCollections.observableArrayList(listaEstudiantes);
            tableEstudiantes.setItems(listaObservableEstudiantes);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
    
}
