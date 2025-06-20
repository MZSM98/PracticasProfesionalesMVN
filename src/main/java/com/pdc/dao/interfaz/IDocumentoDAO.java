package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.DocumentoDTO;
import java.io.IOException;
import java.sql.SQLException;

public interface IDocumentoDAO {
    
    public DocumentoDTO buscarDocumento(int idDocumento) throws SQLException, IOException;
    
}