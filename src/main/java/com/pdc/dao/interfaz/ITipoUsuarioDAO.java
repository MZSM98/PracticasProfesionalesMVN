package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.TipoUsuarioDTO;
import java.sql.SQLException;
import java.util.List;

public interface ITipoUsuarioDAO {
    
    public List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException;
    public TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException;
}
