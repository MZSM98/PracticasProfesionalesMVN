package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.UsuarioDTO;
import java.sql.SQLException;

public interface IUsuarioDAO {
    
    public UsuarioDTO buscarUsuario(String usuario) throws SQLException;
    public boolean insertarUsuario(UsuarioDTO usuario) throws SQLException;
    public boolean editarUsuario(UsuarioDTO usuario) throws SQLException;
    public boolean autenticarUsuario(UsuarioDTO usuario) throws SQLException;
}
