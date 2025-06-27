package com.pdc.controlador.coordinador;

import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.implementacion.ProfesorExperienciaEducativaDAOImpl;
import com.pdc.dao.implementacion.ProyectoAsignadoDAOImpl;
import com.pdc.dao.implementacion.ProyectoDAOImpl;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

public class CoordiandorConsultaEstadisticaController implements Initializable {
    
    private static final Logger LOG = Logger.getLogger(CoordiandorConsultaEstadisticaController.class);

    @FXML
    private Label labelCantidadOrganizaciones;
    
    @FXML
    private Label labelCantidadProyectosRegistrados;
    
    @FXML
    private Label labelCantidadProyectosAsignados;
    
    @FXML
    private Label labelCantidadEstudiantesRegistrados;
    
    @FXML
    private Label labelCantidadAcademicosRegistrados;
    
    @FXML
    private Label labelCantidadAcademicosEvaluadoresRegistrados;
    
    @FXML
    private Label labelCantidadProfesoresRegistrados;
    
    private IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private IProyectoDAO interfazProyectoDAO;
    private IAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private IProfesorExperienciaEducativaDAO interfazProfesorDAO;
    private IProyectoAsignadoDAO interfazProyectoAsignadoDAO;
    private IEstudianteDAO interfazEstudianteDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazProyectoAsignadoDAO = new ProyectoAsignadoDAOImpl();
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
        interfazProfesorDAO = new ProfesorExperienciaEducativaDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazProyectoDAO = new ProyectoDAOImpl();
        cargarEstadistica();
    }    
    
    private void cargarEstadistica(){
        
        try{
            
            int organizacionesRegistradas;
            organizacionesRegistradas = interfazOrganizacionVinculadaDAO.contarOrganizaciones();

            int proyectosRegistrados;
            proyectosRegistrados = interfazProyectoDAO.contarProyectos();

            int proyectosAsignados;
            proyectosAsignados = interfazProyectoAsignadoDAO.contarProyectosAsignados();

            int academicosEvaluadoresRegistrados;
            academicosEvaluadoresRegistrados = interfazAcademicoEvaluadorDAO.contarAcademicosEvaluador();

            int profesoresRegistrados;
            profesoresRegistrados = interfazProfesorDAO.contarProfesores();

            int estudiantesRegistrados;
            estudiantesRegistrados = interfazEstudianteDAO.contarEstudiantes();

            int academicosTotales;
            academicosTotales = academicosEvaluadoresRegistrados + profesoresRegistrados;
            
            labelCantidadOrganizaciones.setText(String.valueOf(organizacionesRegistradas));
            labelCantidadProyectosRegistrados.setText(String.valueOf(proyectosRegistrados));
            labelCantidadProyectosAsignados.setText(String.valueOf(proyectosAsignados));
            labelCantidadAcademicosEvaluadoresRegistrados.setText(String.valueOf(academicosEvaluadoresRegistrados));
            labelCantidadProfesoresRegistrados.setText(String.valueOf(profesoresRegistrados));
            labelCantidadEstudiantesRegistrados.setText(String.valueOf(estudiantesRegistrados));
            labelCantidadAcademicosRegistrados.setText(String.valueOf(academicosTotales));
        
        }catch (SQLException sqle){
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaErrorCargarInformacion();
        }
        
    }
    
    @FXML
    private void cerrarVentana(){
        
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
    }
}
