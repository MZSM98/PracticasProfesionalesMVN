package logica.interfaces;

import accesoadatos.dto.EstudianteDTO;

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface InterfazEstudianteDAO {
    
    boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException;
    
    boolean eliminarEstudiante(String matricula) throws SQLException, IOException;
    
    boolean editarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException;
    
    EstudianteDTO buscarEstudiante(String matricula) throws SQLException, IOException;
    
    public List<EstudianteDTO> listarEstudiantes() throws SQLException, IOException;
}
