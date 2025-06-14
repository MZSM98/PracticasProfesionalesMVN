
package com.pdc.dao.interfaz;
import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IAcademicoEvaluadorDAO {

    boolean insertarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException;
    
    boolean eliminarAcademicoEvaluador (String numeroDeTrabajador) throws SQLException, IOException;
    
    boolean editarAcademicoEvaluador (AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException;
    
    AcademicoEvaluadorDTO buscarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException, IOException;
    
    List<AcademicoEvaluadorDTO> listarAcademicoEvaluador() throws SQLException, IOException;
    
}
