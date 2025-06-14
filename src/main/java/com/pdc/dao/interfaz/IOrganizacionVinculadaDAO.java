package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IOrganizacionVinculadaDAO {
    
    boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException, IOException;
}
