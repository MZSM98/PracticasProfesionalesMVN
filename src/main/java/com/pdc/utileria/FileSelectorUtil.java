package com.pdc.utileria;

import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class FileSelectorUtil {

    private static final Logger LOG = LogManager.getLogger(FileSelectorUtil.class);

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;

    public String seleccionarArchivoPDF(Stage parentStage) {
        try {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Seleccionar archivo PDF");

            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            String userHome = System.getProperty("user.home");
            File initialDirectory = new File(userHome + "/Documents");
            if (initialDirectory.exists()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }

            File selectedFile = fileChooser.showOpenDialog(parentStage);

            if (selectedFile != null) {
                long fileSize = selectedFile.length();

                if (fileSize > MAX_FILE_SIZE) {
                    AlertaUtil.mostrarAlertaError("El archivo seleccionado excede el límite de 20MB");
                    return null;
                }

                if (!selectedFile.exists() || !selectedFile.canRead()) {
                    AlertaUtil.mostrarAlertaError("No se puede acceder al archivo seleccionado.");
                    return null;
                }

                return selectedFile.getAbsolutePath();
            }

        } catch (Exception e) {
            AlertaUtil.mostrarAlertaError("Ocurrió un error al seleccionar el archivo");
            LOG.error(e);
        }

        return null;
    }

    public void mostrarDialogoGuardarPdf(File archivoTemporal, String nombreArchivo) {
        
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

    public void mostrarDialogoGuardarDocx(XWPFDocument document, DocumentoDTO documento) {
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

    private String formatearTamaño(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        }
    }
}
