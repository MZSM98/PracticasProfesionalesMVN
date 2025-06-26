package com.pdc.controlador.estudiante;

import static com.pdc.utileria.FTPUtil.SEPARADOR;
import com.pdc.dao.implementacion.DocumentoDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.enums.DocumentoEnum;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.FTPUtil;
import com.pdc.utileria.FileSelectorUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EstudianteRegistroReporteMensualController implements Initializable {
    private static final Logger LOG = LogManager.getLogger(EstudianteRegistroReporteMensualController.class);

    @FXML
    private ComboBox<String> comboReporteMensual;
    
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    private IDocumentoDAO interfazDocumentoDAO;
    FileSelectorUtil pdfSelector;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazDocumentoDAO = new DocumentoDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        pdfSelector = new FileSelectorUtil();
        llenarComboReporteMensual();
    }
    
    @FXML
    public void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_GESTION_REPORTE_MENSUAL);
    }

    @FXML
    public void accionCargar(ActionEvent event) {
        String reporte = comboReporteMensual.getSelectionModel().getSelectedItem();
        if(Objects.nonNull(reporte)){
            try {
                cargarArchivo(reporte);
            } catch (IOException ex) {
                LOG.error(ex);
            } catch (SQLException ex) {
                LOG.error(ex);
            }

        }else{
            AlertaUtil.mostrarAlertaError("Debe seleccionar un reporte para continuar.");
        }
    }

    @FXML
    public void accionDescargar(ActionEvent event) {
        String reporte = comboReporteMensual.getSelectionModel().getSelectedItem();
        if(Objects.nonNull(reporte)){
            
        }else{
            AlertaUtil.mostrarAlertaError("Debe seleccionar un reporte para continuar.");
        }
    }
    
    private void llenarComboReporteMensual(){
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        try {
            DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(DocumentoEnum.REPORTE_PARCIAL.getId());
            Integer limiteArchivos = documento.getLimiteArchivos();
            Date fechaInicial = estudiante.getPeriodoEscolar().getFechaInicioPeriodoEscolar();
            for (int i = 1; i <= limiteArchivos; i++) {
                comboReporteMensual.getItems().add(i + " " +obtenerNombreMes(fechaInicial.toLocalDate(), i));
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
    
    private String obtenerNombreMes(LocalDate fecha, int adicionar) {
        LocalDate fechaMasUnMes = fecha.plusMonths(adicionar);
        return fechaMasUnMes.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
    }
    
    public void cargarArchivo(String reporte) throws IOException, SQLException{
        EstudianteDTO estudiante = (EstudianteDTO) ManejadorDeSesion.getUsuario();
        Integer numeroReporte = Integer.parseInt(reporte.substring(0, 0));
        DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(DocumentoEnum.REPORTE_PARCIAL.getId());
        String nombreReporte = documento.getFormatoNombre().replace("#", numeroReporte.toString());
        Stage escenarioPadre = ManejadorDeVistas.getInstancia().obtenerEscenarioPrincipal();
        String origen = pdfSelector.seleccionarArchivoPDF(escenarioPadre);
        String destino = estudiante.getMatricula().concat(SEPARADOR).concat(nombreReporte);
        FTPUtil.cargarArchivo(origen, destino);
    }
}
