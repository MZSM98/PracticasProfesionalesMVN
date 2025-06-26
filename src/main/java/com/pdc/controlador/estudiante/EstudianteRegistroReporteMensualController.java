package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.DocumentoDAOImpl;
import com.pdc.dao.implementacion.EstudianteDocumentoDAOImpl;
import com.pdc.dao.implementacion.EstudianteExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativa;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.enums.DocumentoEnum;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.FTPUtil;
import static com.pdc.utileria.FTPUtil.SEPARADOR;
import com.pdc.utileria.FileSelectorUtil;
import com.pdc.utileria.POIUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class EstudianteRegistroReporteMensualController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteRegistroReporteMensualController.class);

    @FXML
    private ComboBox<String> comboReporteMensual;

    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    private IDocumentoDAO interfazDocumentoDAO;
    private IEstudianteExperienciaEducativa interfazEstudianteExperienciaEducativa;
    private IEstudianteDocumentoDAO interfazEstudianteDocumentoDAO;
    FileSelectorUtil pdfSelector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDocumentoDAO = new EstudianteDocumentoDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        pdfSelector = new FileSelectorUtil();
        interfazEstudianteExperienciaEducativa = new EstudianteExperienciaEducativaDAOImpl();
        llenarComboReporteMensual();
    }

    @FXML
    private void accionGenerarPlantilla(ActionEvent event) {
        String numeroReporte = comboReporteMensual.getSelectionModel().getSelectedItem();

        if (Objects.isNull(numeroReporte)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un reporte.");
            return;
        }
        ProyectoDTO proyecto = consultaProyectoAsingado();
        try {
            DocumentoEnum documentoEnum = DocumentoEnum.REPORTE_PARCIAL;
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(documentoEnum.getId());
            documento.setFormatoNombre(documento.getFormatoNombre().replace(".pdf", numeroReporte.concat(".docx")));

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
    public void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }

    @FXML
    public void accionCargar(ActionEvent event) {
        String numeroReporte = comboReporteMensual.getSelectionModel().getSelectedItem();

        if (Objects.isNull(numeroReporte)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un reporte.");
            return;
        }
        
        Integer idDocumento = DocumentoEnum.REPORTE_PARCIAL.getId();
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento;
        DocumentoDTO documento;
        try {
            documento = interfazDocumentoDAO.buscarDocumento(idDocumento);
            String nombreDocumento = documento.getFormatoNombre().replace(".pdf", numeroReporte.concat(".pdf"));
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorNombreArchivoYMatricula(nombreDocumento, matricula);

            if (Objects.nonNull(estudianteDocumento)) {
                actualizarDocumentoEstudiante(documento, estudianteDocumento);
                AlertaUtil.mostrarAlertaExito("Se actualizó la solicitud.");
            } else {
                guardarDocumentoEstudiante(documento, estudiante);
                AlertaUtil.mostrarAlertaExito("Se actualizó guardo la solicitud.");
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    @FXML
    public void accionDescargar(ActionEvent event) {
        String numeroReporte = comboReporteMensual.getSelectionModel().getSelectedItem();

        if (Objects.isNull(numeroReporte)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un reporte.");
            return;
        }

        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento = null;

        try {
            DocumentoEnum documentoEnum = DocumentoEnum.REPORTE_PARCIAL;
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(documentoEnum.getId());
            String nombreDocumento = documento.getFormatoNombre().replace(".pdf", numeroReporte.concat(".pdf"));
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorNombreArchivoYMatricula(nombreDocumento, matricula);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }

        if (Objects.nonNull(estudianteDocumento)) {
            String archivoSolicitud = matricula.concat(SEPARADOR).concat(estudianteDocumento.getNombreArchivo());
            FTPUtil.configurar();
            File archivo = FTPUtil.descargarPlantillaTemp(archivoSolicitud);
            String nombreDocumento = estudianteDocumento.getNombreArchivo();

            pdfSelector.mostrarDialogoGuardarPdf(archivo, nombreDocumento);
        } else {
            AlertaUtil.mostrarAlerta("Información", "No se ha cargado ninguna solicitud.", Alert.AlertType.INFORMATION);
        }
    }

    private void actualizarDocumentoEstudiante(DocumentoDTO documento, EstudianteDocumentoDTO estudianteDocumento) throws SQLException, IOException {
        FTPUtil.configurar();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String matricula = estudianteDocumento.getEstudiante().getMatricula();
        String destino = matricula.concat(SEPARADOR).concat(estudianteDocumento.getNombreArchivo());
        FTPUtil.cargarArchivo(origen, destino);
        interfazEstudianteDocumentoDAO.editarEstudianteDocumento(estudianteDocumento);
    }

    private void guardarDocumentoEstudiante(DocumentoDTO documento, EstudianteDTO estudiante) throws SQLException, IOException {
        String numeroReporte = comboReporteMensual.getSelectionModel().getSelectedItem();

        FTPUtil.configurar();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String nombreDocumento = documento.getFormatoNombre().replace(".pdf", numeroReporte.concat(".pdf"));

        String destino = estudiante.getMatricula().concat(SEPARADOR).concat(nombreDocumento);
        FTPUtil.cargarArchivo(origen, destino);
        EstudianteDocumentoDTO estudianteDocumento = new EstudianteDocumentoDTO();
        estudianteDocumento.setRuta(estudiante.getMatricula().concat(SEPARADOR));
        estudianteDocumento.setEstudiante(estudiante);
        estudianteDocumento.setDocumento(documento);
        estudianteDocumento.setNombreArchivo(nombreDocumento);
        interfazEstudianteDocumentoDAO.insertarEstudianteDocumento(estudianteDocumento);
    }

    private void llenarComboReporteMensual() {
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        try {
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(DocumentoEnum.REPORTE_PARCIAL.getId());

            Integer limiteArchivos = documento.getLimiteArchivos();
            Date fechaInicial = estudiante.getPeriodoEscolar().getFechaInicioPeriodoEscolar();
            for (int i = 1; i <= limiteArchivos; i++) {
                comboReporteMensual.getItems().add(i + " " + obtenerNombreMes(fechaInicial.toLocalDate(), i));
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        } catch (NullPointerException ex) {
            LOG.error(ex);
        }
    }

    private String obtenerNombreMes(LocalDate fecha, int adicionar) {
        LocalDate fechaMasUnMes = fecha.plusMonths(adicionar);
        return fechaMasUnMes.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
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
        } catch (NullPointerException ex) {
            LOG.error(ex);
        }
        if (Objects.nonNull(proyectoAsignado)) {
            return proyectoAsignado.getProyecto();
        } else {
            AlertaUtil.mostrarAlerta("Informativo", "Aún no cuentas con un proyecto asignado", Alert.AlertType.WARNING);
            return null;
        }
    }

    private void llenarDatosPlantilla(File archivoPlantilla, ProyectoDTO proyecto, DocumentoDTO documento) {
        try {
            EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
            String matricula = estudiante.getMatricula();

            EstudianteExperienciaEducativaDTO experienciaEducativa = interfazEstudianteExperienciaEducativa.obtenerExperienciaAsignadaPorEstudiante(matricula);
            String nombreAcademicoEE = experienciaEducativa.getExperienciaEducativa().getProfesor().getNombreProfesor();

            String numeroReporte = comboReporteMensual.getSelectionModel().getSelectedItem();
            String noReporte = numeroReporte.substring(0, 1);
            String mes = numeroReporte.substring(2);

            Map<String, String> remplazos = new HashMap<>();

            remplazos.put("{no_reporte}", noReporte);
            remplazos.put("{periodo}", estudiante.getPeriodoEscolar().getNombrePeriodoEscolar());
            remplazos.put("{mes}", mes);
            remplazos.put("{nombre_estudiante}", estudiante.getNombreEstudiante());
            remplazos.put("{organizacionvinculada_nombre_responsable}", proyecto.getResponsable().getNombreResponsable());
            remplazos.put("{academico_experiencia_educativa}", nombreAcademicoEE);

            FileInputStream fis = new FileInputStream(archivoPlantilla);
            XWPFDocument word = new XWPFDocument(fis);
            fis.close();
            POIUtil.reemplazarMarcadores(word, remplazos);
            pdfSelector.mostrarDialogoGuardarDocx(word, documento);

        } catch (IOException ex) {
            LOG.error("Error al procesar plantilla: ", ex);
            AlertaUtil.mostrarAlertaError("Error al procesar la plantilla");
        } catch (SQLException ex) {
            LOG.error("Error al procesar plantilla: ", ex);
            AlertaUtil.mostrarAlertaError("Error al procesar la plantilla");
        }
    }
}
