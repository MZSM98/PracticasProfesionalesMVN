package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.AutoEvaluacionDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;

public interface IAutoEvaluacionDAO {

    boolean insertarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException;
    
    boolean eliminarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException;
    
    boolean editarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException;
    
    AutoEvaluacionDTO buscarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException;
    
}