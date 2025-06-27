package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;
import com.pdc.dao.interfaz.IUsuarioDAO;

public class UsuarioDAOImpl implements IUsuarioDAO {
    
    @Override
    public UsuarioDTO buscarUsuario(String usuario) throws SQLException {
        
        String consultaSQL = "SELECT usuario, tipousuario, salt FROM usuario WHERE usuario = ?";
        UsuarioDTO usuarioDevuelto = null;

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, usuario);
            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                usuarioDevuelto = new UsuarioDTO();
                usuarioDevuelto.setUsuario(resultadoDeOperacion.getString("usuario"));
                TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("tipousuario"));
                usuarioDevuelto.setTipoUsuario(tipoUsuario);
                usuarioDevuelto.setSalt(resultadoDeOperacion.getString("salt"));
            }
        }
        return usuarioDevuelto;    
    }

    @Override
    public boolean insertarUsuario(UsuarioDTO usuario) throws SQLException {
        
        String insertarSQL = "INSERT INTO usuario (usuario, contrasena, tipousuario, salt) VALUES (?, SHA2(?, 256), ?, ?)";
        String salt = generateSalt();

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, usuario.getUsuario());
            declaracionPreparada.setString(2, usuario.getContrasena().concat(salt));
            declaracionPreparada.setInt(3, usuario.getTipoUsuario().getIdTipo());
            declaracionPreparada.setString(4, salt);
            declaracionPreparada.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean editarUsuario(UsuarioDTO usuario) throws SQLException {
        
        String actualizarSQL = "UPDATE usuario SET contrasena = SHA2(?, 256), salt = ? WHERE usuario = ?";
        String nuevoSalt = generateSalt();

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, usuario.getContrasena().concat(nuevoSalt));
            declaracionPreparada.setString(2, nuevoSalt); 
            declaracionPreparada.setString(3, usuario.getUsuario()); 

            int filasAfectadas = declaracionPreparada.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean autenticarUsuario(UsuarioDTO usuario) throws SQLException {
        
        String consultaSQL = "SELECT usuario, tipousuario FROM usuario WHERE usuario = ? AND contrasena = SHA2(?, 256)";
        UsuarioDTO usuarioDevuelto = null;

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, usuario.getUsuario());
            declaracionPreparada.setString(2, usuario.getContrasena().concat(usuario.getSalt()));
            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                usuarioDevuelto = new UsuarioDTO();
                usuarioDevuelto.setUsuario(resultadoDeOperacion.getString("usuario"));
                TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("tipousuario"));
                usuarioDevuelto.setTipoUsuario(tipoUsuario);
            }
        }
        return Objects.nonNull(usuarioDevuelto);
    }
    
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
