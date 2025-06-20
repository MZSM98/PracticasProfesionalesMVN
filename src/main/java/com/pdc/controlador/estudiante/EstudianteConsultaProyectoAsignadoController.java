package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EstudianteConsultaProyectoAsignadoController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(EstudianteConsultaProyectoAsignadoController.class);

    @FXML
    private TextField textDescripcion;

    @FXML
    private TextField textEstadoDelProyecto;

    @FXML
    private TextField textFechaFinal;

    @FXML
    private TextField textFechaInicio;

    @FXML
    private TextField textOrganizacionVinculada;

    @FXML
    private TextField textPeriodoEscolar;

    @FXML
    private TextField textResponsable;

    @FXML
    private TextField textTitulo;
    
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        consultaProyectoAsingado();
    }
    
    private void consultaProyectoAsingado(){
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
        if(Objects.nonNull(proyectoAsignado)){
            
            ProyectoDTO proyecto = proyectoAsignado.getProyecto();
            llenarDatosVista(proyecto);
        }else{
            AlertaUtil.mostrarAlerta("Informativo", "Aún no cuentas con un proyecto asignado", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void accionCerrar(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCacheVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }
    
    private void llenarDatosVista(ProyectoDTO proyecto){
        textTitulo.setText(proyecto.getTituloProyecto());
        textDescripcion.setText(proyecto.getDescripcionProyecto());
        textOrganizacionVinculada.setText(proyecto.getOrganizacion().getNombreOV());
        textEstadoDelProyecto.setText(proyecto.getEstadoProyecto());
        textFechaInicio.setText(proyecto.getFechaInicio().toString());
        textFechaFinal.setText(proyecto.getFechaFinal().toString());
        textResponsable.setText(proyecto.getResponsable().getNombreResponsable());
        textPeriodoEscolar.setText(proyecto.getPeriodoEscolar().getNombrePeriodoEscolar());
    }
    
}
