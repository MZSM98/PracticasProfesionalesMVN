package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

public class EstudianteMenuPrincipalController implements Initializable {

    private static final Logger LOG = Logger.getLogger(EstudianteMenuPrincipalController.class);

    @FXML
    private Label labelNombreUsuario;
    
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        configurarSesion();
    }

    @FXML
    private void abrirVentanaConsultarCronograma(ActionEvent event) {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        if(Objects.nonNull(proyecto)){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_CONSULTA_CRONOGRAMA);
        }
    }

    @FXML
    private void abrirVentanaProyectoAsignado(ActionEvent event) {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        if(Objects.nonNull(proyecto)){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_CONSULTA_PROYECTO_ASIGNADO);
        }
    }

    @FXML
    private void abrirVentanaRegistrarAutoevaluacion(ActionEvent event) {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        if(Objects.nonNull(proyecto)){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_AUTOEVALUACION);
        }
    }

    @FXML
    private void abrirVentanaRegistrarSolicitudProyecto(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_SOLICITUD_PROYECTO);
    }

    @FXML
    private void abrirVentanaActualizarPerfil(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_ACTUALIZACION_PERFIL);
    }

    @FXML
    private void abrirVentanaRegistrarReporteMensual(ActionEvent event) {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        if(Objects.nonNull(proyecto)){
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_REPORTE_MENSUAL);
        }
    }

    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }

    private void configurarSesion() {
        EstudianteDTO estudiante;
        estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String nombreCoordinador;
        nombreCoordinador = estudiante.getNombreEstudiante();
        labelNombreUsuario.setText(nombreCoordinador);
    }

    private ProyectoDTO consultaProyectoAsingado() {
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        ProyectoAsignadoDTO proyectoAsignado = null;
        try {
            proyectoAsignado = interfazProyectoAsignadoDAO.obtenerProyectoAsignadoPorMatricula(matricula);
        } catch (SQLException sqle) {

            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
        
        if (Objects.nonNull(proyectoAsignado)) {
            return proyectoAsignado.getProyecto();
        } else {
            AlertaUtil.mostrarAlerta("Informativo", "Aún no cuentas con un proyecto asignado", Alert.AlertType.WARNING);
            return null;
        }
    }
}
