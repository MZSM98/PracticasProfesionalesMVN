package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.DocumentoDAOImpl;
import com.pdc.dao.implementacion.EstudianteDocumentoDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteDocumentoDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteDocumentoDTO;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class EstudianteRegistroAutoevaluacionController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteRegistroSolicitudProyectoController.class);

    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    private IEstudianteDocumentoDAO interfazEstudianteDocumentoDAO;
    private IDocumentoDAO interfazDocumentoDAO;
    FileSelectorUtil pdfSelector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazEstudianteDocumentoDAO = new EstudianteDocumentoDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        pdfSelector = new FileSelectorUtil();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }

    @FXML
    private void accionDescargarAutoevaluacion(ActionEvent event) {
        Integer idDocumento = DocumentoEnum.AUTOEVALUACION_ALUMNO.getId();

        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento = null;

        try {
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorIdDocumento(idDocumento);
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }

        if (Objects.nonNull(estudianteDocumento)) {
            String archivoSolicitud = matricula.concat(SEPARADOR).concat(estudianteDocumento.getDocumento().getFormatoNombre());
            FTPUtil.configurar();
            File archivo = FTPUtil.descargarPlantillaTemp(archivoSolicitud);
            pdfSelector.mostrarDialogoGuardarPdf(archivo, estudianteDocumento.getNombreArchivo());
        } else {
            AlertaUtil.mostrarAlerta("Información", "No se ha cargado ninguna solicitud.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void accionDescargarPlantilla(ActionEvent event) {
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
    private void accionRegistrarAutoevaluacion(ActionEvent event) {
        Integer idDocumento = DocumentoEnum.AUTOEVALUACION_ALUMNO.getId();
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        String matricula = estudiante.getMatricula();
        EstudianteDocumentoDTO estudianteDocumento;
        DocumentoDTO documento;
        try {
            estudianteDocumento = interfazEstudianteDocumentoDAO.obtenerEstudianteDocumentoPorIdDocumento(idDocumento);
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

    private void guardarDocumentoEstudiante(DocumentoDTO documento, EstudianteDTO estudiante) throws SQLException, IOException {
        FTPUtil.configurar();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String destino = estudiante.getMatricula().concat(SEPARADOR).concat(documento.getFormatoNombre());
        FTPUtil.cargarArchivo(origen, destino);
        EstudianteDocumentoDTO estudianteDocumento = new EstudianteDocumentoDTO();
        estudianteDocumento.setRuta(estudiante.getMatricula().concat(SEPARADOR));
        estudianteDocumento.setEstudiante(estudiante);
        estudianteDocumento.setDocumento(documento);
        estudianteDocumento.setNombreArchivo(documento.getFormatoNombre());
        interfazEstudianteDocumentoDAO.insertarEstudianteDocumento(estudianteDocumento);
    }
    
    private void actualizarDocumentoEstudiante(DocumentoDTO documento, EstudianteDocumentoDTO estudianteDocumento, String matricula) throws SQLException, IOException {
        FTPUtil.configurar();
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String destino = matricula.concat(SEPARADOR).concat(documento.getFormatoNombre());
        FTPUtil.cargarArchivo(origen, destino);
        interfazEstudianteDocumentoDAO.editarEstudianteDocumento(estudianteDocumento);
    }

}
