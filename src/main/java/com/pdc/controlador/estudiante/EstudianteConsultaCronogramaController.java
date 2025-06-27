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
import javafx.scene.control.TextArea;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EstudianteConsultaCronogramaController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteConsultaCronogramaController.class);

    @FXML
    private TextArea textMesCuatro;

    @FXML
    private TextArea textMesDos;

    @FXML
    private TextArea textMesTres;

    @FXML
    private TextArea textMesUno;

    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        llenarDatosPantalla();
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }

    private void llenarDatosPantalla() {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        if (Objects.nonNull(proyecto)) {
            textMesUno.setText(proyecto.getCronogramaMesUno());
            textMesDos.setText(proyecto.getCronogramaMesDos());
            textMesTres.setText(proyecto.getCronogramaMesTres());
            textMesCuatro.setText(proyecto.getCronogramaMesCuatro());
        }
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
