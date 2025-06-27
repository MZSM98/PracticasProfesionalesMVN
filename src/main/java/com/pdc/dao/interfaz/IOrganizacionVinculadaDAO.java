package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.util.List;

public interface IOrganizacionVinculadaDAO {
    
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException;
    public boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException;
    public boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException;
    public OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException;
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException;
    public int contarOrganizaciones() throws SQLException;
}
