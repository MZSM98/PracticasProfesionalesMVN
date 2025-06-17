package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IOrganizacionVinculadaDAO {
    
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    public boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    public boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    public OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException, IOException;
}
