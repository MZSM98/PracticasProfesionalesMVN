package com.pdc.controlador.iniciodesesion;

import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.pdc.dao.implementacion.TipoUsuarioDAOImpl;
import com.pdc.dao.implementacion.UsuarioDAOImpl;
import com.pdc.validador.InicioDeSesionValidador;

import org.apache.log4j.Logger;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.ConstantesUtil;
import com.pdc.utileria.RestriccionCamposUtil;
import com.pdc.dao.interfaz.IUsuarioDAO;
import com.pdc.dao.interfaz.ITipoUsuarioDAO;
import com.pdc.utileria.manejador.ManejadorDeVistas;

public class InicioDeSesionController implements Initializable {
    
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
    private ComboBox<TipoUsuarioDTO> comboTipoUsuario;
    
    @FXML
    private Button botonIniciarSesion;

    private IUsuarioDAO interfazUsuarioDAO;
    private ITipoUsuarioDAO interfazTipoUsuarioDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonIniciarSesion.setDefaultButton(Boolean.TRUE);
        interfazUsuarioDAO = new UsuarioDAOImpl();
        interfazTipoUsuarioDAO = new TipoUsuarioDAOImpl();
        poblarComboTipoUsuario();
        aplicarRestriccionesLongitudACampos();
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
        usuarioDTO.setTipoUsuario(comboTipoUsuario.getValue());
        
        if(!validarCamposInicioSesion(usuarioDTO)){
            
            return;
        }
        try {
            
            UsuarioDTO usuarioBusqueda = interfazUsuarioDAO.buscarUsuario(usuarioDTO.getUsuario());
            usuarioDTO.setSalt(usuarioBusqueda.getSalt());

            if (interfazUsuarioDAO.autenticarUsuario(usuarioDTO)) {

                abrirMenuPrincipal(obtenerRecursoVetana(usuarioDTO.getTipoUsuario()));
                
            } else {

                AlertaUtil.mostrarAlerta(ConstantesUtil.ADVERTENCIA, ConstantesUtil.ALERTA_CREDENCIALES_INVALIDAS, Alert.AlertType.WARNING);
            }
        }catch (IllegalArgumentException ioe){
            
            LOG.error(ConstantesUtil.LOG_DATOS_NO_VALIDOS, ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_DATOS_INVALIDOS, Alert.AlertType.WARNING); 
        }catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD,sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_VENTANA,ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_VENTANA, Alert.AlertType.ERROR);
        }
    }
    

    private void abrirMenuPrincipal(ManejadorDeVistas.Vista vista){ 
        ManejadorDeVistas.getInstancia().cambiarVista(vista);
    }
    
    private void poblarComboTipoUsuario(){
        
        List<TipoUsuarioDTO> listaTipoUsuario;
        
        try {
            
            listaTipoUsuario = interfazTipoUsuarioDAO.listaTipoUsuario();
            ObservableList<TipoUsuarioDTO> listaObservableTipoUsuario = FXCollections.observableArrayList(listaTipoUsuario);
            comboTipoUsuario.setItems(listaObservableTipoUsuario);
        } catch (SQLException sqle) {
            
            LOG.error(ConstantesUtil.ALERTA_ERROR_BD,sqle);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_BD, Alert.AlertType.ERROR);
        } catch (IOException ioe) {
            
            LOG.error(ConstantesUtil.LOG_ERROR_CARGAR_INFORMACION,ioe);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ERROR, ConstantesUtil.ALERTA_ERROR_CARGAR_INFORMACION, Alert.AlertType.WARNING);
        }
    }
    

    private ManejadorDeVistas.Vista obtenerRecursoVetana(TipoUsuarioDTO tipoUsuario){
        
        if (COORDINADOR.equals(tipoUsuario.getIdTipo())) {
            return ManejadorDeVistas.Vista.COORDINADOR_MENU_PRINCIPAL;
        } else if (ACADEMICO_EVALUADOR.equals(tipoUsuario.getIdTipo())) {
            return ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_MENU_PRINCIPAL;
        } else if (PROFESOR_EE.equals(tipoUsuario.getIdTipo())) {
            return ManejadorDeVistas.Vista.PROFESOREE_MENU_PRINCIPAL;
        } else if (ESTUDIANTE.equals(tipoUsuario.getIdTipo())) {
            return ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL;
        } else {
            throw new IllegalArgumentException("No existe tipo de usuario");
        }
    }

    private boolean validarCamposInicioSesion(UsuarioDTO usuarioDTO) {
               
        try {
            
            InicioDeSesionValidador.validarInicioDeSesion(usuarioDTO);
            return true;
        } catch (IllegalArgumentException iae) {
            
            LOG.error (ConstantesUtil.LOG_DATOS_NO_VALIDOS,iae);
            AlertaUtil.mostrarAlerta(ConstantesUtil.ALERTA_DATOS_INVALIDOS, iae.getMessage(), Alert.AlertType.WARNING);
            return false;
        } 
    }

    private void aplicarRestriccionesLongitudACampos(){
        
        RestriccionCamposUtil.aplicarRestriccionLongitud(textContrasena, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
        RestriccionCamposUtil.aplicarRestriccionLongitud(textUsuario, ConstantesUtil.RESTRICCION_LONGITUD_TEXTFIELD);
    }

}
