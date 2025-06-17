package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IEstudianteDAO {
    
    public boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException;
    public boolean eliminarEstudiante(String matricula) throws SQLException, IOException;
    public boolean editarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException;
    public EstudianteDTO buscarEstudiante(String matricula) throws SQLException, IOException;
    public List<EstudianteDTO> listarEstudiantes() throws SQLException, IOException;
}
