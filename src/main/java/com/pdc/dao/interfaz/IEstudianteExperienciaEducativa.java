
package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import java.sql.SQLException;
import java.util.List;


public interface IEstudianteExperienciaEducativa {

    public boolean insertarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException;
    public boolean editarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException;
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorID(Integer idExperienciaAsignada) throws SQLException;
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorEstudiante(String matricula) throws SQLException;
    public List<EstudianteExperienciaEducativaDTO> listarExperienciaAsignada() throws SQLException; 
    public List<EstudianteExperienciaEducativaDTO> listaExperienciaAsignadaPorNRC(String nrcExperiencia) throws SQLException;
}
