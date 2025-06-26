package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.DocumentoDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.enums.DocumentoEnum;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.FTPUtil;
import com.pdc.utileria.FileSelectorUtil;
import com.pdc.utileria.POIUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class EstudianteGestionReporteMensualController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteGestionReporteMensualController.class);

    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    private IDocumentoDAO interfazDocumentoDAO;
    FileSelectorUtil pdfSelector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazDocumentoDAO = new DocumentoDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        pdfSelector = new FileSelectorUtil();
    }

    @FXML
    public void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }

    @FXML
    public void accionDescargarPlantilla(ActionEvent event) {
        ProyectoDTO proyecto = consultaProyectoAsingado();
        try {
            DocumentoEnum documentoEnum = DocumentoEnum.AUTOEVALUACION_ALUMNO;
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(documentoEnum.getId());
            FTPUtil.configurar();
            File plantilla = FTPUtil.descargarPlantillaTemp(documento.getPlantilla());
            if (plantilla != null && proyecto != null) {

                llenarDatosPlantilla(plantilla, proyecto, documento);
                FileUtils.deleteQuietly(plantilla);

            } else {
                AlertaUtil.mostrarAlertaErrorCargarInformacion();

            }
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    @FXML
    public void accionRegistrarReporteMensual(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_REPORTE_MENSUAL);
    }

    private void llenarDatosPlantilla(File archivoPlantilla, ProyectoDTO proyecto, DocumentoDTO documento) {
        try {
            EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
            FileInputStream fis = new FileInputStream(archivoPlantilla);
            XWPFDocument word = new XWPFDocument(fis);
            fis.close();

            Map<String, String> remplazos = new HashMap<>();

            remplazos.put("{estudiante_nombre}", estudiante.getNombreEstudiante());
            remplazos.put("{estudiante_matricula}", estudiante.getMatricula());

            remplazos.put("{organizacionvinculada_nombre}", proyecto.getOrganizacion().getNombreOV());

            remplazos.put("{responsableproyecto_nombre}", proyecto.getResponsable().getNombreResponsable());

            remplazos.put("{proyecto_nombre}", proyecto.getTituloProyecto());

            POIUtil.reemplazarMarcadores(word, remplazos);
            pdfSelector.mostrarDialogoGuardarDocx(word, documento);

        } catch (IOException ex) {
            LOG.error("Error al procesar plantilla: ", ex);
            AlertaUtil.mostrarAlertaError("Error al procesar la plantilla");
        }
    }

    private ProyectoDTO consultaProyectoAsingado() {
        EstudianteDTO estudiante;
        estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        ProyectoAsignadoDTO proyectoAsignado = null;
        try {

            proyectoAsignado = interfazProyectoAsignadoDAO.obtenerProyectoAsignadoPorMatricula(matricula);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
        if (Objects.nonNull(proyectoAsignado)) {
            return proyectoAsignado.getProyecto();
        } else {
            AlertaUtil.mostrarAlerta("Informativo", "Aún no cuentas con un proyecto asignado", Alert.AlertType.WARNING);
            return null;
        }
    }
}
