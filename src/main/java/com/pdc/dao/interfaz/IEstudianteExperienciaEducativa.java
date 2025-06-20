
package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface IEstudianteExperienciaEducativa {

    public boolean insertarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException, IOException;
    public boolean editarExperienciaAsignado(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException, IOException;
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorID(Integer idExperienciaAsignada) throws SQLException, IOException;
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorEstudiante(String matricula) throws SQLException, IOException;
    public List<EstudianteExperienciaEducativaDTO> listarExperienciaAsignada() throws SQLException, IOException;    
}
