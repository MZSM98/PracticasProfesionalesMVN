package logica.interfaces;

import accesoadatos.dto.ProfesorEEDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface InterfazProfesorEEDAO {
    
    boolean insertarProfesorEE(ProfesorEEDTO profesor) throws SQLException, IOException;
    
    boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    
    boolean editarProfesorEE(ProfesorEEDTO profesor) throws SQLException, IOException;
    
    ProfesorEEDTO buscarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    
}