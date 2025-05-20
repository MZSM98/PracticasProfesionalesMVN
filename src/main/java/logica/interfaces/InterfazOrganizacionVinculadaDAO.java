package logica.interfaces;

import accesoadatos.dto.OrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface InterfazOrganizacionVinculadaDAO {
    
    boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    
    boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    
    boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) throws SQLException, IOException;
    
    OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException;
    
    public List listarOrganizacionesVinculadas() throws SQLException, IOException;
    
}
