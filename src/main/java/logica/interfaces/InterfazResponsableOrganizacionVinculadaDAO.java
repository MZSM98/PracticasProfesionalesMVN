package logica.interfaces;

import accesoadatos.dto.ResponsableOrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface InterfazResponsableOrganizacionVinculadaDAO {
    
    boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    
    boolean eliminarResponsableOV(String rfc) throws SQLException, IOException;
    
    boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    
    ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfc) throws SQLException, IOException;
    
}
