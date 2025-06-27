package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.SeccionDTO;
import java.sql.SQLException;
import java.util.List;

public interface ISeccionDAO {
    
    public SeccionDTO buscarSeccion(Integer idSeccion) throws SQLException;
    public List<SeccionDTO> listarSecciones() throws SQLException;
}
