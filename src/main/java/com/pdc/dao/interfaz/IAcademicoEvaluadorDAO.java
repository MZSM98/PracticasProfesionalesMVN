package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IAcademicoEvaluadorDAO {

    public boolean insertarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException;
    public boolean eliminarAcademicoEvaluador (String numeroDeTrabajador) throws SQLException, IOException;
    public boolean editarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException;
    public AcademicoEvaluadorDTO buscarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException, IOException;
    public List<AcademicoEvaluadorDTO> listarAcademicoEvaluador() throws SQLException, IOException;
}
