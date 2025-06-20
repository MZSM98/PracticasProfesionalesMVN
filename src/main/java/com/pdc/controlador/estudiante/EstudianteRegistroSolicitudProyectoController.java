package com.pdc.controlador.estudiante;
import com.pdc.dao.implementacion.DocumentoDAOImpl;
import static com.pdc.utileria.FTPUtil.SEPARADOR;
import com.pdc.dao.implementacion.EstudianteDocumentoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteDocumentoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import com.pdc.modelo.enums.DocumentoEnum;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.FTPUtil;
import com.pdc.utileria.PDFSelectorUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EstudianteRegistroSolicitudProyectoController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteRegistroSolicitudProyectoController.class);

    private IEstudianteDocumentoDAO interfazEstudianteDocumentoDAO;
    private IDocumentoDAO interfazDocumentoDAO;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDocumentoDAO = new EstudianteDocumentoDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
    }

    @FXML
    private void accionDescargarPlantilla(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GENERA_PLANTILLA_SOLICITUD);

    }

    @FXML
    private void accionDescargarSolicitud(ActionEvent event) {
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento = null;

        try {
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorMatricula(matricula);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }

        if (Objects.nonNull(estudianteDocumento)) {
            String archivoSolicitud = matricula.concat(SEPARADOR).concat(estudianteDocumento.getDocumento().getNombreDocumento());
            FTPUtil.configurar();
            File archivo = FTPUtil.descargarPlantillaTemp(archivoSolicitud);
            mostrarDialogoGuardar(archivo, estudianteDocumento.getDocumento().getNombreDocumento());
        } else {
            AlertaUtil.mostrarAlerta("Información", "No se ha cargado ninguna solicitud.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void accionRegistrarSolicitud(ActionEvent event) {
        Integer idDocumento = DocumentoEnum.SOLICITUD_PRACTICAS.getId();
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento;
        DocumentoDTO documento;
        try {
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorMatricula(matricula);
            documento = interfazDocumentoDAO.buscarDocumento(idDocumento);

            if (Objects.nonNull(estudianteDocumento)) {
                actualizarDocumentoEstudiante(documento, estudianteDocumento, matricula);
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

    private void actualizarDocumentoEstudiante(DocumentoDTO documento, EstudianteDocumentoDTO estudianteDocumento, String matricula) throws SQLException, IOException {
        FTPUtil.configurar();
        PDFSelectorUtil pdfSelector = new PDFSelectorUtil();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String destino = matricula.concat(SEPARADOR).concat(documento.getFormatoNombre());
        FTPUtil.cargarArchivo(origen, destino);
        interfazEstudianteDocumentoDAO.editarEstudianteDocumento(estudianteDocumento);
    }

    private void guardarDocumentoEstudiante(DocumentoDTO documento, EstudianteDTO estudiante) throws SQLException, IOException {
        FTPUtil.configurar();
        PDFSelectorUtil pdfSelector = new PDFSelectorUtil();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String destino = estudiante.getMatricula().concat(SEPARADOR).concat(documento.getFormatoNombre());
        FTPUtil.cargarArchivo(origen, destino);
        EstudianteDocumentoDTO estudianteDocumento = new EstudianteDocumentoDTO();
        estudianteDocumento.setRuta(estudiante.getMatricula().concat(SEPARADOR));
        estudianteDocumento.setEstudiante(estudiante);
        estudianteDocumento.setDocumento(documento);
        estudianteDocumento.setNombreArchivo(documento.getNombreDocumento());
        interfazEstudianteDocumentoDAO.insertarEstudianteDocumento(estudianteDocumento);
    }

    @FXML
    void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }

    private void mostrarDialogoGuardar(File archivoTemporal, String nombreArchivo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar documento");
        fileChooser.setInitialFileName(nombreArchivo);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        File archivoDestino = fileChooser.showSaveDialog(stage);

        if (archivoDestino != null) {
            try {
                Files.copy(archivoTemporal.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                AlertaUtil.mostrarAlertaExito("Documento guardado exitosamente");
            } catch (IOException ex) {
                LOG.error("Error al guardar documento: ", ex);
                AlertaUtil.mostrarAlertaError("Error al guardar el documento");
            } finally {
                try {
                    Files.deleteIfExists(archivoTemporal.toPath());
                } catch (IOException ex) {
                    LOG.warn("No se pudo eliminar el archivo temporal: " + archivoTemporal.getAbsolutePath(), ex);
                }
            }
        } else {
            try {
                Files.deleteIfExists(archivoTemporal.toPath());
            } catch (IOException ex) {
                LOG.warn("No se pudo eliminar el archivo temporal: " + archivoTemporal.getAbsolutePath(), ex);
            }
        }
    }
}
