
package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import java.sql.SQLException;
import java.util.List;


public interface IExperienciaEducativa {
    
    public ExperienciaEducativaDTO obtenerExperienciaEducativaPorNRC(String nrc) throws SQLException;
    public List<ExperienciaEducativaDTO> listarExperienciaEducativa() throws SQLException;
    public ExperienciaEducativaDTO buscarExperienciaEducativaPorProfesor(String numeroDeTrabajador) throws SQLException;
    public boolean asignarProfesorAExperienciaEducativa(String nrc, String numeroDeTrabajador) throws SQLException;
    public boolean reasignarProfesorExperienciaEducativa(String nrcAnterior, String nrcNuevo, String numeroDeTrabajador) throws SQLException;
}
