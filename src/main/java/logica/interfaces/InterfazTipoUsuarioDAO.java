package logica.interfaces;

import accesoadatos.dto.TipoUsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface InterfazTipoUsuarioDAO {
    
        List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException, IOException;
        TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException, IOException;
}
