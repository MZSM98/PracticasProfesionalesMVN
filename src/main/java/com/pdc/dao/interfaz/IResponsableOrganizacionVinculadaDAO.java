package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.util.List;

public interface IResponsableOrganizacionVinculadaDAO {
    
    public boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException;
    public boolean eliminarResponsableOV(String rfc) throws SQLException;
    public boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException;
    public ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfcMoral) throws SQLException;
    public List<ResponsableOrganizacionVinculadaDTO> listarResponsablesPorOrganizacion(String rfcMoral) throws SQLException;
}
