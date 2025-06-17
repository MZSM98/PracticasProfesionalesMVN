package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.AutoEvaluacionDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;

public interface IAutoEvaluacionDAO {

    public boolean insertarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException;
    public boolean eliminarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException;
    public boolean editarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException;
    public AutoEvaluacionDTO buscarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException;
}