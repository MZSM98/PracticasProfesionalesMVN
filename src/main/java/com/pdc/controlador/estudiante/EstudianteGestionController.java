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
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.util.ArrayList;
import javafx.scene.control.Button;

public class EstudianteGestionController implements Initializable {

    private static final Logger LOG = Logger.getLogger(EstudianteGestionController.class);
    @FXML
    private Button botonAbrirRegistrarEstudiante;
    
    @FXML
    private Button botonAbrirEditarEstudiante;

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
        mostrarBotones();
    }

    @FXML
    void abrirEditarEstudiante(ActionEvent event) {

        EstudianteDTO estudianteSeleccionado;
        estudianteSeleccionado = tableEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {

            AlertaUtil.mostrarAlertaSeleccionRegistro();
            return;
        }

        try {

            ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);

            EstudianteRegistroController controlador;
            controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);

            controlador.setModoEdicion(Boolean.TRUE);
            controlador.llenarCamposEditablesEstudiante(estudianteSeleccionado);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZAR);
        } catch (IOException ex) {
            LOG.error("Error al cargar la ventana: " + ex.getMessage());
            AlertaUtil.mostrarAlerta("Error", "No se pudo abrir la ventana.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void abrirRegistrarEstudiante(ActionEvent event) {

        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO);
    }

    @FXML
    void salirAMenuPrincipal(ActionEvent event) {

        ManejadorDeVistas.getInstancia().limpiarCache();
        final Integer COORDINADOR = 1;
        final Integer PROFESOREE = 3;
        ManejadorDeVistas.getInstancia().limpiarCache();
        if (COORDINADOR.equals(ManejadorDeSesion.getUsuario().getTipoUsuario().getIdTipo())) {

            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
        } else if (PROFESOREE.equals(ManejadorDeSesion.getUsuario().getTipoUsuario().getIdTipo())) {

            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_MENU_PRINCIPAL);
        }
    }

    private void configurarColumnasTablaEstudiante() {

        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEstudiante"));
        columnPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccionEstudiante"));
        columnAvance.setCellValueFactory(new PropertyValueFactory<>("avanceCrediticio"));
        columnPromedio.setCellValueFactory(new PropertyValueFactory<>("promedio"));
    }

    private void poblarTablaEstudiante() {

        try {
            
            List<EstudianteDTO> listaEstudiantes;
            UsuarioDTO usuario = ManejadorDeSesion.getUsuario();
            
            if (usuario instanceof ProfesorExperienciaEducativaDTO) {
                listaEstudiantes = interfazEstudianteDAO.listarEstudiantesAsignadosPorProfesor(((ProfesorExperienciaEducativaDTO) usuario).getNumeroTrabajador());
            }else{
                listaEstudiantes = interfazEstudianteDAO.listarEstudiantes();
            }
            
            ObservableList<EstudianteDTO> listaObservableEstudiantes = FXCollections.observableArrayList(listaEstudiantes);
            tableEstudiantes.setItems(listaObservableEstudiantes);
        } catch (SQLException sqle) {

            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
    }

    private void mostrarBotones() {
        UsuarioDTO usuario = ManejadorDeSesion.getUsuario();
        if (usuario instanceof ProfesorExperienciaEducativaDTO) {
            botonAbrirRegistrarEstudiante.setVisible(Boolean.FALSE);
            botonAbrirEditarEstudiante.setVisible(Boolean.FALSE);
        }
    }
}
