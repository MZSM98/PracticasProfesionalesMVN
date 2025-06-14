package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.CredencialesIngresoDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface ICredencialesIngresoDAO {
    
    boolean insertarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    
    boolean eliminarCredencialesIngreso(String usuario) throws SQLException, IOException;
    
    boolean editarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    
    CredencialesIngresoDTO buscarCredencialesIngreso(String usuario) throws SQLException, IOException;
}
