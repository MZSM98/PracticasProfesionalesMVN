package com.pdc.controlador.academicoevaluador;

import com.pdc.dao.implementacion.EstudianteEvaluacionDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IEstudianteEvaluacionDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.EstudianteEvaluacionDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AcademicoEvaluadorRegistroEvaluacionParcialController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(AcademicoEvaluadorRegistroEvaluacionParcialController.class);

    @FXML
    private Label labelNombreEstudiante;

    @FXML
    private Label labelNombreProyecto;

    @FXML
    private Label labelNumeroEvaluacion;

    @FXML
    private TextField textCriterioCuatro;

    @FXML
    private TextField textCriterioDos;

    @FXML
    private TextField textCriterioTres;

    @FXML
    private TextField textCriterioUno;

    private boolean modoEdicion;
    
    private EstudianteEvaluacionDTO estudianteEvaluacionEdicion;

    private IEstudianteEvaluacionDAO interfazEstudianteEvaluacion;

    private EstudianteExperienciaEducativaDTO estudianteExperienciaEducativa;
    
    private IProyectoAsignadoDAO interfazProyectoAsignado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteEvaluacion = new EstudianteEvaluacionDAOImpl();
        interfazProyectoAsignado = new ProyectoAsignadoDAOImpl();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_CONSULTA_EVALUACION_PARCIAL);
    }

    @FXML
    private void accionGuardar(ActionEvent event) {
        EstudianteEvaluacionDTO estudianteEvaluacion = generarObjetoEstudianteEvaluacion();
        try {
            if (modoEdicion) {
                interfazEstudianteEvaluacion.editarEstudianteEvaluacion(estudianteEvaluacion);
                AlertaUtil.mostrarAlertaRegistroExitoso();
                accionCancelar(event);

            } else {
                interfazEstudianteEvaluacion.insertarEstudianteEvaluacion(estudianteEvaluacion);
                AlertaUtil.mostrarAlertaRegistroExitoso();
                accionCancelar(event);
            }
        } catch (SQLException ex) {
            LOG.error("Error al guardar los datos.", ex);
            AlertaUtil.mostrarAlertaError("Error al guardar los datos.");
        }
    }

    private EstudianteEvaluacionDTO generarObjetoEstudianteEvaluacion() {
        EstudianteEvaluacionDTO evaluacion;

        UsuarioDTO academicoEvaluador = (AcademicoEvaluadorDTO) ManejadorDeSesion.getUsuario();
        try {
            ProyectoAsignadoDTO proyectoAsignado = proyectoAsignadoAlEstudiante();
            evaluacion = new EstudianteEvaluacionDTO();
            if(!modoEdicion){
                evaluacion.setMatricula(estudianteExperienciaEducativa.getEstudiante().getMatricula());
                evaluacion.setNumeroDeTrabajador(academicoEvaluador.getUsuario());
                evaluacion.setProyectoID(proyectoAsignado.getProyecto().getProyectoID());
            }else{
                evaluacion = estudianteEvaluacionEdicion;
            }

            int criterioUno = Integer.parseInt(textCriterioUno.getText().trim());
            int criterioDos = Integer.parseInt(textCriterioDos.getText().trim());
            int criterioTres = Integer.parseInt(textCriterioTres.getText().trim());
            int criterioCuatro = Integer.parseInt(textCriterioCuatro.getText().trim());

            evaluacion.setCriterioUno(criterioUno);
            evaluacion.setCriterioDos(criterioDos);
            evaluacion.setCriterioTres(criterioTres);
            evaluacion.setCriterioCuatro(criterioCuatro);

            LOG.info("Objeto EstudianteEvaluacionDTO generado correctamente");
            return evaluacion;

        } catch (NumberFormatException e) {
            LOG.error("Error al convertir criterios a enteros: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOG.error("Error al generar el objeto EstudianteEvaluacionDTO: " + e.getMessage(), e);
            return null;
        }
    }

    public void llenarDatosPantallaEdicion(EstudianteEvaluacionDTO estudianteEvaluacion) {
        modoEdicion = Boolean.TRUE;
        this.estudianteEvaluacionEdicion = estudianteEvaluacion;
        textCriterioUno.setText(String.valueOf(estudianteEvaluacion.getCriterioUno()));
        textCriterioDos.setText(String.valueOf(estudianteEvaluacion.getCriterioDos()));
        textCriterioTres.setText(String.valueOf(estudianteEvaluacion.getCriterioTres()));
        textCriterioCuatro.setText(String.valueOf(estudianteEvaluacion.getCriterioCuatro()));

        labelNombreEstudiante.setText(estudianteEvaluacion.getMatricula());
        labelNombreProyecto.setText(String.valueOf(estudianteEvaluacion.getProyectoID()));
        labelNumeroEvaluacion.setText(String.valueOf(estudianteEvaluacion.getIdvaluacion()));
    }

    public void llenarDatosPantallaRegistro(EstudianteExperienciaEducativaDTO estudianteExperienciaEducativa) {
        try {
            this.estudianteExperienciaEducativa = estudianteExperienciaEducativa;
            ProyectoAsignadoDTO proyectoAsignado = proyectoAsignadoAlEstudiante();
            
            labelNombreEstudiante.setText(estudianteExperienciaEducativa.getEstudiante().getNombreEstudiante());
            labelNombreProyecto.setText(proyectoAsignado.getProyecto().getTituloProyecto());
            labelNumeroEvaluacion.setText(String.valueOf(interfazEstudianteEvaluacion.obtenerSiguienteId()));
        } catch (SQLException ex) {
            LOG.error("Error al consultar información en BD",ex);
        }
    }

    private ProyectoAsignadoDTO proyectoAsignadoAlEstudiante() {
        ProyectoAsignadoDTO proyectoAsignado = null;
        try {
            if (Objects.nonNull(estudianteExperienciaEducativa)) {
                String matricula = estudianteExperienciaEducativa.getEstudiante().getMatricula();
                proyectoAsignado = interfazProyectoAsignado.obtenerProyectoAsignadoPorMatricula(matricula);
                return proyectoAsignado;
            }
        } catch (SQLException ex) {
            LOG.error("Error al verificar el estado del proyecto", ex);
        }
        return proyectoAsignado;
    }

}
