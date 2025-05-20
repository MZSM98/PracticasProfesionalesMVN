package logica.interfaces;

import accesoadatos.dto.CredencialesIngresoDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface InterfazCredencialesIngresoDAO {
    
    boolean insertarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    
    boolean eliminarCredencialesIngreso(String usuario) throws SQLException, IOException;
    
    boolean editarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException;
    
    CredencialesIngresoDTO buscarCredencialesIngreso(String usuario) throws SQLException, IOException;
}
