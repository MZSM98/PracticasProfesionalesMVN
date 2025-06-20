package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.DocumentoDAOImpl;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.enums.DocumentoEnum;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.FTPUtil;
import com.pdc.utileria.POIUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class EstudianteGeneraPlantillaSolicitudProyectoController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteGeneraPlantillaSolicitudProyectoController.class);

    @FXML
    private ComboBox<ProyectoDTO> comboProyecto;
    private IProyectoDAO interfazProyectoDAO;
    private IDocumentoDAO interfazDocumentoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
        llenarComboProyecto();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_REGISTRO_SOLICITUD_PROYECTO);
    }

    @FXML
    private void accionGenerar(ActionEvent event) {
        ProyectoDTO proyecto = comboProyecto.getSelectionModel().getSelectedItem();
        if (Objects.isNull(proyecto)) {
            AlertaUtil.mostrarAlertaError("Debe seleccionar un proyecto.");
            return;
        }

        Integer idDocumento = DocumentoEnum.SOLICITUD_PRACTICAS.getId();
        try {
            
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(idDocumento);
            FTPUtil.configurar();
            File plantilla = FTPUtil.descargarPlantillaTemp(documento.getPlantilla());
            if (plantilla != null) {
                
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

    private void llenarComboProyecto() {
        try {
            comboProyecto.setItems(FXCollections.observableArrayList(interfazProyectoDAO.listarProyectosConVacantesDisponibles()));
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    private void llenarDatosPlantilla(File archivoPlantilla, ProyectoDTO proyecto, DocumentoDTO documento) {
        try {
            FileInputStream fis = new FileInputStream(archivoPlantilla);
            XWPFDocument word = new XWPFDocument(fis);
            fis.close();

            Map<String, String> remplazos = new HashMap<>();

            remplazos.put("{organizacionvinculada_nombre}", proyecto.getOrganizacion().getNombreOV());
            remplazos.put("{organizacionvinculada_direccion}", proyecto.getOrganizacion().getDireccionOV());
            remplazos.put("{organizacionvinculada_telefono}", proyecto.getOrganizacion().getTelefonoOV());

            remplazos.put("{responsableproyecto_nombre}", proyecto.getResponsable().getNombreResponsable());
            remplazos.put("{responsableproyecto_cargo}", proyecto.getResponsable().getCargo());
            remplazos.put("{responsableproyecto_correo}", proyecto.getResponsable().getCorreoResponsable());

            remplazos.put("{proyecto_nombre}", proyecto.getTituloProyecto());
            remplazos.put("{proyecto_descripcion}", proyecto.getDescripcionProyecto());
            remplazos.put("{proyecto_vacantes}", proyecto.getVacantes() + " estudiantes");

            remplazos.put("{cronograma_agosto_febrero}", proyecto.getCronogramaMesUno());
            remplazos.put("{cronograma_ septiembre_marzo}", proyecto.getCronogramaMesDos());
            remplazos.put("{cronograma_octubre_abril}", proyecto.getCronogramaMesTres());
            remplazos.put("{cronograma_noviembre_mayo}", proyecto.getCronogramaMesCuatro());
            
            POIUtil.reemplazarMarcadores(word, remplazos);
            mostrarDialogoGuardar(word, documento);

        } catch (IOException ex) {
            LOG.error("Error al procesar plantilla: ", ex);
            AlertaUtil.mostrarAlertaError("Error al procesar la plantilla");
        }
    }

    private void mostrarDialogoGuardar(XWPFDocument document, DocumentoDTO documento) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar documento");
        fileChooser.setInitialFileName(documento.getFormatoNombre().replace("pdf", "docx"));

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos Word (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        File archivoDestino = fileChooser.showSaveDialog(stage);

        if (archivoDestino != null) {
            try {
                FileOutputStream fos = new FileOutputStream(archivoDestino);
                document.write(fos);
                fos.close();
                document.close();

                AlertaUtil.mostrarAlertaExito("Documento guardado exitosamente");
                
            } catch (IOException ex) {
                LOG.error("Error al guardar documento: ", ex);
                AlertaUtil.mostrarAlertaError("Error al guardar el documento");
            }
        } else {
            try {
                document.close();
            } catch (IOException ex) {
                LOG.error("Error al cerrar documento: ", ex);
            }
        }
    }
}
