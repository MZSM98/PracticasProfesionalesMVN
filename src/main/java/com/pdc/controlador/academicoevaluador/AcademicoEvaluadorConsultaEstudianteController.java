package com.pdc.controlador.academicoevaluador;

import com.pdc.dao.implementacion.EstudianteEvaluacionDAOImpl;
import com.pdc.dao.implementacion.EstudianteExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IEstudianteEvaluacionDAO;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativa;
import com.pdc.dao.interfaz.IExperienciaEducativa;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.EstudianteEvaluacionDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AcademicoEvaluadorConsultaEstudianteController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(AcademicoEvaluadorConsultaEstudianteController.class);

    @FXML
    private TableColumn<EstudianteExperienciaEducativaDTO, String> columnaEvaluado;

    @FXML
    private TableColumn<EstudianteExperienciaEducativaDTO, String> columnaExperienciaEducativa;

    @FXML
    private TableColumn<EstudianteExperienciaEducativaDTO, String> columnaMatricula;

    @FXML
    private TableColumn<EstudianteExperienciaEducativaDTO, String> columnaNRC;

    @FXML
    private TableColumn<EstudianteExperienciaEducativaDTO, String> columnaNombreAlumno;

    @FXML
    private ComboBox<ExperienciaEducativaDTO> comboGrupo;

    @FXML
    private TableView<EstudianteExperienciaEducativaDTO> tablaEstudiante;

    private IEstudianteEvaluacionDAO interfazEstudianteEvaluacion;

    private IEstudianteExperienciaEducativa interfazEstudianteExperienciaEducativa;

    private IExperienciaEducativa interfazExperienciaEducativa;

    private IProyectoAsignadoDAO interfazProyectoAsignado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteEvaluacion = new EstudianteEvaluacionDAOImpl();
        interfazEstudianteExperienciaEducativa = new EstudianteExperienciaEducativaDAOImpl();
        interfazExperienciaEducativa = new ExperienciaEducativaDAOImpl();
        interfazProyectoAsignado = new ProyectoAsignadoDAOImpl();
        configurarTablaEstudiante();
        llenarComboGrupo();
    }

    @FXML
    private void accionCambiarGrupo(ActionEvent event) {
        try {
            ExperienciaEducativaDTO experienciaEducativa = comboGrupo.getSelectionModel().getSelectedItem();
            if (Objects.isNull(experienciaEducativa)) {
                AlertaUtil.mostrarAlertaError("Debe seleccionar un grupo.");
                return;
            }
            String nrc = experienciaEducativa.getNrc();
            List<EstudianteExperienciaEducativaDTO> datos = interfazEstudianteExperienciaEducativa.listaExperienciaAsignadaPorNRC(nrc);
            llenarTablaEstudiante(datos);
        } catch (SQLException ex) {
            LOG.error("Error al cargar la información", ex);
            AlertaUtil.mostrarAlertaError("Error al cargar la información.");
        }

    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_MENU_PRINCIPAL);
    }

    @FXML
    private void accionEditar(ActionEvent event) {
        EstudianteExperienciaEducativaDTO estudianteSeleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();
        if (Objects.isNull(estudianteSeleccionado)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un registro.");
            return;
        }
        try {
            AcademicoEvaluadorRegistroEvaluacionParcialController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
            String matricula = estudianteSeleccionado.getEstudiante().getMatricula();
            EstudianteEvaluacionDTO estudianteEvaluacion = interfazEstudianteEvaluacion.obtenerEstudianteEvaluacionPorMatricula(matricula);
            controlador.llenarDatosPantallaEdicion(estudianteEvaluacion);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
        } catch (IOException ex) {
            LOG.error(ex);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    @FXML
    private void accionRegistrar(ActionEvent event) {
        EstudianteExperienciaEducativaDTO estudianteSeleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();
        if (Objects.isNull(estudianteSeleccionado)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un registro.");
            return;
        }

        if (!verificarProyectoAsignado()) {
            AlertaUtil.mostrarAlertaError("El alumno aún no tiene un proyecto asignado.");
            return;
        }
        try {
            AcademicoEvaluadorRegistroEvaluacionParcialController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
            controlador.llenarDatosPantallaRegistro(estudianteSeleccionado);
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    private void llenarComboGrupo() {
        try {
            List<ExperienciaEducativaDTO> listaExperienciaEducativa = interfazExperienciaEducativa.listarExperienciaEducativa();
            comboGrupo.setItems(FXCollections.observableArrayList(listaExperienciaEducativa));
        } catch (SQLException ex) {
            LOG.error("Ocurrió un error al cargar datos del grupo.", ex);
            AlertaUtil.mostrarAlertaError("Ocurrió un error al cargar datos del grupo.");
        }
    }

    private void llenarTablaEstudiante(List<EstudianteExperienciaEducativaDTO> datos) {
        tablaEstudiante.setItems(FXCollections.observableArrayList(datos));
    }

    private void configurarTablaEstudiante() {
        columnaNombreAlumno.setCellValueFactory(cellData
                -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEstudiante().getNombreEstudiante()));

        columnaMatricula.setCellValueFactory(cellData
                -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEstudiante().getMatricula()));

        columnaExperienciaEducativa.setCellValueFactory(cellData
                -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getExperienciaEducativa().getNombre()));

        columnaNRC.setCellValueFactory(cellData
                -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getExperienciaEducativa().getNrc()));

        columnaEvaluado.setCellValueFactory(cellData -> {
            String matricula = cellData.getValue().getEstudiante().getMatricula();
            boolean evaluado = verificarSiEstaEvaluado(matricula);
            return new javafx.beans.property.SimpleStringProperty(evaluado ? "SÍ" : "NO");
        });
    }

    private boolean verificarSiEstaEvaluado(String matricula) {
        try {
            List<EstudianteEvaluacionDTO> evaluaciones = interfazEstudianteEvaluacion.listarEstudianteEvaluaciones();
            return evaluaciones.stream()
                    .anyMatch(evaluacion -> matricula.equals(evaluacion.getMatricula()));
        } catch (SQLException ex) {
            LOG.error("Error al validar estatus de estudiantes.", ex);
            AlertaUtil.mostrarAlertaError("Error al validar estatus de estudiantes.");
            return false;
        }
    }

    private boolean verificarProyectoAsignado() {
        EstudianteExperienciaEducativaDTO estudianteEvaluacion = tablaEstudiante.getSelectionModel().getSelectedItem();
        try {
            if (Objects.nonNull(estudianteEvaluacion)) {

                String matricula = estudianteEvaluacion.getEstudiante().getMatricula();
                ProyectoAsignadoDTO proyectoAsignado = interfazProyectoAsignado.obtenerProyectoAsignadoPorMatricula(matricula);
                return Objects.nonNull(proyectoAsignado);
            }
        } catch (SQLException ex) {
            LOG.error("Error al verificar el estado del proyecto", ex);
        }
        return false;
    }
}
