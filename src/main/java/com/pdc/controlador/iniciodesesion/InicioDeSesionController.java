package com.pdc.controlador.iniciodesesion;

import com.pdc.dao.implementacion.AcademicoEvaluadorDAOImpl;
import com.pdc.dao.implementacion.CoordinadorDAOImpl;
import com.pdc.dao.implementacion.EstudianteDAOImpl;
import com.pdc.dao.implementacion.ProfesorExperienciaEducativaDAOImpl;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.pdc.dao.implementacion.UsuarioDAOImpl;
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.ICoordinadorDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.validador.InicioDeSesionValidador;
import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.RestriccionCamposUtil;
import com.pdc.dao.interfaz.IUsuarioDAO;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import com.pdc.utileria.GmailUtil;
import java.util.Objects;

public class InicioDeSesionController implements Initializable {
    
    public static final String NO_EXISTE_TIPO_USUARIO = "No existe tipo de usuario";
    private static final Integer COORDINADOR = 1;
    private static final Integer ACADEMICO_EVALUADOR = 2;
    private static final Integer PROFESOR_EE = 3;
    private static final Integer ESTUDIANTE = 4;
    
    private static final Logger LOG = Logger.getLogger(InicioDeSesionController.class);

    @FXML
    private Button botonSalir;

    @FXML
    private TextField textUsuario;
    
    @FXML
    private PasswordField textContrasena;

    @FXML
    private Button botonIniciarSesion;

    private IUsuarioDAO interfazUsuarioDAO;
    private ICoordinadorDAO interfazCordinadorDao;
    private IProfesorExperienciaEducativaDAO interfazProfesorEEDAO;
    private IAcademicoEvaluadorDAO interfazAcademicoEvaluadorDAO;
    private IEstudianteDAO interfazEstudianteDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        botonIniciarSesion.setDefaultButton(Boolean.TRUE);
        interfazUsuarioDAO = new UsuarioDAOImpl();
        interfazCordinadorDao = new CoordinadorDAOImpl();
        interfazProfesorEEDAO = new ProfesorExperienciaEducativaDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazAcademicoEvaluadorDAO = new AcademicoEvaluadorDAOImpl();
        
        aplicarRestriccionesLongitudACampos();
        GmailUtil.configurarCorreo();
    }    

    @FXML
    void cerrarPrograma(ActionEvent event) {
        
        Stage currentStage = (Stage) botonSalir.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void iniciarSesion(ActionEvent event){
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(textUsuario.getText().trim());
        usuarioDTO.setContrasena(textContrasena.getText().trim());
        
        if(!validarCamposInicioSesion(usuarioDTO)){
            limpiarCampos();
            return;
        }
        try {
            
            UsuarioDTO usuarioBusqueda = interfazUsuarioDAO.buscarUsuario(usuarioDTO.getUsuario());
            if(Objects.isNull(usuarioBusqueda)){
                AlertaUtil.mostrarAlertaError("Usuario y/o contraseña incorrectos.");
                limpiarCampos();
                return;
            }
            
            usuarioDTO.setSalt(usuarioBusqueda.getSalt());

            if (interfazUsuarioDAO.autenticarUsuario(usuarioDTO)) {
                
                ManejadorDeSesion.iniciarSesion(obtenerTipoUsuario(usuarioBusqueda));
                abrirMenuPrincipal(usuarioBusqueda.getTipoUsuario());
            } else {

                AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, ConstantesUtil.ALERTA_CREDENCIALES_INVALIDAS, Alert.AlertType.WARNING);
            }
        }catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_BD, sqle);
            AlertaUtil.mostrarAlertaBaseDatos();
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA, ioe);
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }
    

    private void abrirMenuPrincipal(TipoUsuarioDTO tipoUsuario){
        
        if (COORDINADOR.equals(tipoUsuario.getIdTipo())) {  
            
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL);
        } else if (ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())) {
            
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_MENU_PRINCIPAL);
        } else if (PROFESOR_EE.equals(tipoUsuario.getIdTipo())) {
            
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.PROFESOREE_MENU_PRINCIPAL);
        } else if (ESTUDIANTE.equals(tipoUsuario.getIdTipo())) {
            
            ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
        } else {
            
            AlertaUtil.mostrarAlerta(AlertaUtil.ADVERTENCIA, NO_EXISTE_TIPO_USUARIO, Alert.AlertType.WARNING);
        }
    }
    
    private UsuarioDTO obtenerTipoUsuario(UsuarioDTO usuario) throws SQLException, IOException{ 
        
        final String numeroDeTrabajador;
        numeroDeTrabajador = usuario.getUsuario();
        UsuarioDTO usuarioFinal;
        
        if (COORDINADOR.equals(usuario.getTipoUsuario().getIdTipo())) {
            
            usuarioFinal = interfazCordinadorDao.buscarCoordinador(numeroDeTrabajador);
        } else if (ACADEMICO_EVALUADOR.equals(usuario.getTipoUsuario().getIdTipo())) {
            
            usuarioFinal = interfazAcademicoEvaluadorDAO.buscarAcademicoEvaluador(numeroDeTrabajador);
        } else if (PROFESOR_EE.equals(usuario.getTipoUsuario().getIdTipo())) {
            
            usuarioFinal = interfazProfesorEEDAO.buscarProfesorEE(numeroDeTrabajador);
        } else if (ESTUDIANTE.equals(usuario.getTipoUsuario().getIdTipo())) {
            
            usuarioFinal = interfazEstudianteDAO.buscarEstudiante(usuario.getUsuario());
        } else {
            
            throw new IllegalArgumentException(NO_EXISTE_TIPO_USUARIO);
        }
        
        usuarioFinal.setUsuario(usuario.getUsuario());
        usuarioFinal.setContrasena(usuario.getContrasena());
        usuarioFinal.setTipoUsuario(usuario.getTipoUsuario());
        usuarioFinal.setSalt(usuario.getSalt());
        
        return usuarioFinal;
    }
    
    private boolean validarCamposInicioSesion(UsuarioDTO usuarioDTO) {
               
        try {
            
            InicioDeSesionValidador.validarInicioDeSesion(usuarioDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.LOG_DATOS_NO_VALIDOS,iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ALERTA_DATOS_NO_VALIDOS, iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        } 
    }

    private void limpiarCampos(){
        
        textUsuario.clear();
        textContrasena.clear();
    }
    
    private void aplicarRestriccionesLongitudACampos(){
        
        RestriccionCamposUtil.aplicarRestriccionLongitud(textContrasena, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textUsuario, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
    }

}
