package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.CredencialesIngresoDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface ICredencialesIngresoDAO {
    
    public boolean insertarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    public boolean eliminarCredencialesIngreso(String usuario) throws SQLException, IOException;
    public boolean editarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    public CredencialesIngresoDTO buscarCredencialesIngreso(String usuario) throws SQLException, IOException;
}
