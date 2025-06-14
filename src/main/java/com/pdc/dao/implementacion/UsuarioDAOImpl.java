
package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;
import com.pdc.dao.interfaz.IUsuarioDAO;


public class UsuarioDAOImpl implements IUsuarioDAO{
    
    @Override
    public UsuarioDTO buscarUsuario(String usuario) throws SQLException, IOException{
        String consultaSQL = "SELECT usuario, tipousuario, salt  FROM usuario WHERE usuario = ?";
        UsuarioDTO usuarioDevuelto = null;
        Connection conexionBD = null;
        PreparedStatement declaracionPreparada = null;
        ResultSet resultadoDeOperacion = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, usuario);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                usuarioDevuelto = new UsuarioDTO();
                usuarioDevuelto.setUsuario(resultadoDeOperacion.getString("usuario"));
                TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("tipousuario"));
                usuarioDevuelto.setTipoUsuario(tipoUsuario);
                usuarioDevuelto.setSalt(resultadoDeOperacion.getString("salt"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return usuarioDevuelto;    }

    @Override
    public boolean insertarUsuario(UsuarioDTO usuario) throws SQLException, IOException{
        String insertarSQL = "INSERT INTO usuario (usuario, contrasena, tipousuario, salt) VALUES (?, SHA2(?, 256), ?, ?)";
        boolean insercionExitosa = false;
        Connection conexionBD = null;
        PreparedStatement declaracionPreparada = null;
        String salt = generateSalt();
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, usuario.getUsuario());
            declaracionPreparada.setString(2, usuario.getContrasena().concat(salt));
            declaracionPreparada.setInt(3, usuario.getTipoUsuario().getIdTipo());
            declaracionPreparada.setString(4, salt);
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();  
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean editarUsuario(UsuarioDTO usuario) throws SQLException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarUsuario(UsuarioDTO usuario) throws SQLException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean autenticarUsuario(UsuarioDTO usuario) throws SQLException, IOException {
        String consultaSQL = "SELECT usuario, tipousuario  FROM usuario WHERE usuario = ? AND contrasena = SHA2(?, 256) AND tipousuario = ?";
        UsuarioDTO usuarioDevuelto = null;
        Connection conexionBD = null;
        PreparedStatement declaracionPreparada = null;
        ResultSet resultadoDeOperacion = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, usuario.getUsuario());
            declaracionPreparada.setString(2, usuario.getContrasena().concat(usuario.getSalt()));
            declaracionPreparada.setInt(3, usuario.getTipoUsuario().getIdTipo());
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                usuarioDevuelto = new UsuarioDTO();
                usuarioDevuelto.setUsuario(resultadoDeOperacion.getString("usuario"));
                TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("tipousuario"));
                usuarioDevuelto.setTipoUsuario(tipoUsuario);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
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
