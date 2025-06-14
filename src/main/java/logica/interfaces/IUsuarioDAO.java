package logica.interfaces;

import accesoadatos.dto.UsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;

public interface IUsuarioDAO {
    
    public UsuarioDTO buscarUsuario(String usuario) throws SQLException, IOException;
    public boolean insertarUsuario(UsuarioDTO usuario) throws SQLException, IOException;
    public boolean editarUsuario(UsuarioDTO usuario) throws SQLException, IOException;
    public boolean eliminarUsuario(UsuarioDTO usuario) throws SQLException, IOException;
    public boolean autenticarUsuario(UsuarioDTO usuario) throws SQLException, IOException;

}
