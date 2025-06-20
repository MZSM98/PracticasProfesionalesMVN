
package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CoordinadorProyectoAsignadoController implements Initializable {
    
    private static final Logger LOG = LogManager.getLogger(CoordinadorProyectoAsignadoController.class);

    @FXML
    private TableColumn<ProyectoAsignadoDTO, String> columnaAlumno;

    @FXML
    private TableColumn<ProyectoAsignadoDTO, String> columnaProyecto;
    
    @FXML
    private TableView<ProyectoAsignadoDTO> tablaProyectoAsignado;
    
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        configurarTablaProyectoAsignado();
        llenarTablaProyectoAsignado();
    }    
    
    @FXML
    private void accionAsignarProyecto(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_ASIGNAR_PROYECTO);
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
    }

    @FXML
    private void accionReasignarProyecto(ActionEvent event) {
        ProyectoAsignadoDTO proyectoAsginado = tablaProyectoAsignado.getSelectionModel().getSelectedItem();
        if(Objects.nonNull(proyectoAsginado)){
            try {
                ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.COORDINADOR_REASIGNAR_PROYECTO);
                CoordinadorReasignarProyectoController controller = ManejadorDeVistas.getInstancia().obtenerControlador(ManejadorDeVistas.Vista.COORDINADOR_REASIGNAR_PROYECTO);
                controller.asignarDatosVentana(proyectoAsginado);
                ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_REASIGNAR_PROYECTO);
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }else{
            AlertaUtil.mostrarAlerta("Advertencia", "Debe seleccionar un registro", Alert.AlertType.WARNING);
        }

    }

    private void configurarTablaProyectoAsignado() {
        columnaAlumno.setCellValueFactory(cellData -> {
            EstudianteDTO estudiante = cellData.getValue().getEstudiante();
            if (estudiante != null) {
                String matricula = estudiante.getMatricula();
                String nombre = estudiante.getNombreEstudiante();
                return new SimpleStringProperty(matricula + " - " + nombre);
            }
            return new SimpleStringProperty("");
        });

        columnaProyecto.setCellValueFactory(cellData -> {
            ProyectoDTO proyecto = cellData.getValue().getProyecto();
            if (proyecto != null) {
                return new SimpleStringProperty(proyecto.getTituloProyecto());
            }
            return new SimpleStringProperty("");
        });
    }
    
    private void llenarTablaProyectoAsignado(){
        try {
            tablaProyectoAsignado.setItems(FXCollections.observableArrayList(interfazProyectoAsignadoDAO.listaProyectoAsignado()));
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
   
}
