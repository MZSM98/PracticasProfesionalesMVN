
package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface IExperienciaEducativa {
    
    public ExperienciaEducativaDTO obtenerExperienciaEducativaPorNRC(String nrc) throws SQLException, IOException;
    public List<ExperienciaEducativaDTO> listarExperienciaEducativa() throws SQLException, IOException; 
}
