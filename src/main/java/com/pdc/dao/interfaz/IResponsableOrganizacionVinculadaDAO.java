package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IResponsableOrganizacionVinculadaDAO {
    
    public boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    public boolean eliminarResponsableOV(String rfc) throws SQLException, IOException;
    public boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException;
    public ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfcMoral) throws SQLException, IOException;
    public List<ResponsableOrganizacionVinculadaDTO> listarResponsablesPorOrganizacion(String rfcMoral) throws SQLException, IOException;
}
