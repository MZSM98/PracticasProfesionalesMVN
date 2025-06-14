package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.TipoUsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITipoUsuarioDAO {
    
        List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException, IOException;
        TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException, IOException;
}
