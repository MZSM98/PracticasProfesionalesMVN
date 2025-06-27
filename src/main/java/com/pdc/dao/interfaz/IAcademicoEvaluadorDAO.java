package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import java.sql.SQLException;
import java.util.List;

public interface IAcademicoEvaluadorDAO {

    public boolean insertarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException;
    public boolean eliminarAcademicoEvaluador (String numeroDeTrabajador) throws SQLException;
    public boolean editarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException;
    public AcademicoEvaluadorDTO buscarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException;
    public List<AcademicoEvaluadorDTO> listarAcademicoEvaluador() throws SQLException;
}
