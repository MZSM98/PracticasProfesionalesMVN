package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.PeriodoEscolarDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IPeriodoEscolarDAO {
    
    public PeriodoEscolarDTO buscarPeriodoEscolar(Integer idPeriodoEscolar)  throws SQLException, IOException ;
    public List<PeriodoEscolarDTO> listarPeriodos() throws SQLException, IOException ;
    
}
