package com.pdc.utileria;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PDFSelectorUtil {
    
    private static final Logger LOG = LogManager.getLogger(PDFSelectorUtil.class);

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