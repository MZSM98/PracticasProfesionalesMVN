package logica.interfaces;

import accesoadatos.dto.SeccionDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ISeccionDAO {
    
    public SeccionDTO buscarSeccion(Integer idSeccion) throws SQLException, IOException;
    public List<SeccionDTO> listarSecciones() throws SQLException, IOException;
    
}
