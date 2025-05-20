package logica.interfaces;

import accesoadatos.dto.ResponsableOVDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface InterfazResponsableOrganizacionVinculadaDAO {
    
    boolean insertarResponsableOV(ResponsableOVDTO responsable) throws SQLException, IOException;
    
    boolean eliminarResponsableOV(String rfc) throws SQLException, IOException;
    
    boolean editarResponsableOV(ResponsableOVDTO responsable) throws SQLException, IOException;
    
    ResponsableOVDTO buscarResponsableOV(String rfc) throws SQLException, IOException;
    
}
