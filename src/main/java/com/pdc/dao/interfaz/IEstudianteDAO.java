package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteDTO;
import java.sql.SQLException;
import java.util.List;

public interface IEstudianteDAO {
    
    public boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException;
    public boolean eliminarEstudiante(String matricula) throws SQLException;
    public boolean editarEstudiante(EstudianteDTO estudiante) throws SQLException;
    public EstudianteDTO buscarEstudiante(String matricula) throws SQLException;
    public List<EstudianteDTO> listarEstudiantes() throws SQLException;
    public List<EstudianteDTO> listarEstudiantesSinProyectoAsignado() throws SQLException;
    public int contarEstudiantes() throws SQLException;
    public List<EstudianteDTO> listarEstudiantesAsignadosPorProfesor(String numeroDeTrabajador) throws SQLException;
}
