package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;

public interface IResponsableOrganizacionVinculadaDAO {
    
    boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    
    boolean eliminarResponsableOV(String rfc) throws SQLException, IOException;
    
    boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    
    ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfc) throws SQLException, IOException;
    
}
