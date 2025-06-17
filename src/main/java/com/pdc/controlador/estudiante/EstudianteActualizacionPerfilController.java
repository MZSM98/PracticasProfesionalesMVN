package com.pdc.controlador.estudiante;

import com.pdc.dao.implementacion.UsuarioDAOImpl;
import com.pdc.dao.interfaz.IUsuarioDAO;
import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.AlertaUtil;
import com.pdc.utileria.manejador.ManejadorDeSesion;
import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EstudianteActualizacionPerfilController implements Initializable {
    private static final Logger LOG = LogManager.getLogger(EstudianteActualizacionPerfilController.class);
    
    @FXML
    private PasswordField textConfirmeNuevaContrasena;

    @FXML
    private PasswordField textNuevaContrasena;

    @FXML
    private PasswordField textContrasenaActual;
    
    private IUsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAOImpl();
    }        
    
    @FXML
    void guardarPerfil(ActionEvent event) {
        UsuarioDTO usuarioSesion = ManejadorDeSesion.getUsuario();

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuario(usuarioSesion.getUsuario());
        usuarioSesion.setContrasena(textContrasenaActual.getText());
        try {
            if(usuarioDAO.autenticarUsuario(usuarioSesion)){
                if(textNuevaContrasena.getText().equals(textConfirmeNuevaContrasena.getText())){
                    usuario.setContrasena(textConfirmeNuevaContrasena.getText());
                    usuarioDAO.editarUsuario(usuario);
                    AlertaUtil.mostrarAlertaActualizacionExitosa();
                    volverAMenuPrincipal(event);
                }else{
                    AlertaUtil.mostrarAlerta("Advertencia","La nueva contraseña debe coincidir en ambos campos.", Alert.AlertType.ERROR);
                }
            }else{
                AlertaUtil.mostrarAlerta("Advertencia","Debe ingresar la contraseña actual.", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    @FXML
    void volverAMenuPrincipal(ActionEvent event) {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ESTUDIANTE_MENU_PRINCIPAL);
    }
    
}
