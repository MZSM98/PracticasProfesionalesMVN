package com.pdc.controlador.profesoree;

import com.pdc.controlador.academicoevaluador.AcademicoEvaluadorRegistroEvaluacionParcialController;
import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.EstudianteEvaluacionDAOImpl;
import com.pdc.dao.implementacion.EstudianteExperienciaEducativaDAOImpl;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IEstudianteEvaluacionDAO;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativaDAO;
import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteEvaluacionDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ProfesorEEConsultaEvaluacionPresentacionController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(ProfesorEEConsultaEvaluacionPresentacionController.class);

    @FXML
    private TableColumn<EstudianteEvaluacionDTO, String> columnaEstudiante;

    @FXML
    private TableColumn<EstudianteEvaluacionDTO, String> columnaEvaluador;

    @FXML
    private TableColumn<EstudianteEvaluacionDTO, String> columnaMatricula;

    @FXML
    private TableView<EstudianteEvaluacionDTO> tablaEvaluaciones;

    private IEstudianteEvaluacionDAO interfazEstudianteEvaluacion;

    private IEstudianteDAO interfazEstudiante;

    private IAcademicoEvaluadorDAO interfazAcademicoEvaluador;
    
    private IEstudianteExperienciaEducativaDAO interfazEstudianteExperienciaEducativa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteEvaluacion = new EstudianteEvaluacionDAOImpl();
        interfazEstudiante = new EstudianteDAOImpl();
        interfazAcademicoEvaluador = new AcademicoEvaluadorDAOImpl();
        interfazEstudianteExperienciaEducativa = new EstudianteExperienciaEducativaDAOImpl();
        configurarTablaEstudiante();
        llenarTablaEstudiante();

    }

    @FXML
    void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_MENU_PRINCIPAL);
    }

    @FXML
    void accionConsultar(ActionEvent event) {
        EstudianteEvaluacionDTO evaluacion = tablaEvaluaciones.getSelectionModel().getSelectedItem();
        if (Objects.isNull(evaluacion)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un registro.");
            return;
        }

        try {
            AcademicoEvaluadorRegistroEvaluacionParcialController controlador = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
            String matricula = evaluacion.getMatricula();
            EstudianteExperienciaEducativaDTO experienciaEducativa = interfazEstudianteExperienciaEducativa.obtenerExperienciaAsignadaPorEstudiante(matricula);
            controlador.llenarDatosPantallaEdicion(evaluacion, experienciaEducativa);
            controlador.modoConsulta();
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
        } catch (IOException ex) {
            LOG.error(ex);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    private void configurarTablaEstudiante() {
        columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaEstudiante.setCellValueFactory(cellData -> {
            String matricula = cellData.getValue().getMatricula();
            String nombre = obtieneNombreEstudiante(matricula);
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });
        columnaEvaluador.setCellValueFactory(cellData -> {
            String numeroDeTrabajador = cellData.getValue().getNumeroDeTrabajador();
            String nombre = obtieneNombreAcademico(numeroDeTrabajador);
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });

    }

    private String obtieneNombreEstudiante(String matricula) {
        EstudianteDTO estudiante = null;
        try {
            estudiante = interfazEstudiante.buscarEstudiante(matricula);
            if (Objects.nonNull(estudiante)) {
                return estudiante.getNombreEstudiante();
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return "";
    }

    private String obtieneNombreAcademico(String numeroDeTrabajador) {
        AcademicoEvaluadorDTO academico = null;
        try {
            academico = interfazAcademicoEvaluador.buscarAcademicoEvaluador(numeroDeTrabajador);
            if (Objects.nonNull(academico)) {
                return academico.getNombreAcademico();
            }

        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return "";
    }

    private void llenarTablaEstudiante() {
        try {
            ProfesorExperienciaEducativaDTO profesor = (ProfesorExperienciaEducativaDTO) ManejadorDeSesion.getUsuario();
            String numeroDeTrabajador = profesor.getNumeroTrabajador();
            List<EstudianteEvaluacionDTO> listaEvaluaciones = interfazEstudianteEvaluacion.listarEvaluacionesPorProfesor(numeroDeTrabajador);
            tablaEvaluaciones.setItems(FXCollections.observableArrayList(listaEvaluaciones));
        } catch (SQLException ex) {
        }
    }

}
