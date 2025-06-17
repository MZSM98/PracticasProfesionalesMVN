package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.TipoUsuarioDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITipoUsuarioDAO {
    
    public List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException, IOException;
    public TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException, IOException;
}
