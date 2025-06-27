package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteEvaluacionDTO;
import java.sql.SQLException;
import java.util.List;

public interface IEstudianteEvaluacionDAO {
    
    boolean insertarEstudianteEvaluacion(EstudianteEvaluacionDTO estudianteEvaluacion) throws SQLException;
    
    boolean editarEstudianteEvaluacion(EstudianteEvaluacionDTO estudianteEvaluacion) throws SQLException;
    
    List<EstudianteEvaluacionDTO> listarEstudianteEvaluaciones() throws SQLException;
    
    EstudianteEvaluacionDTO obtenerEstudianteEvaluacionPorId(int idvaluacion) throws SQLException;
    
    public int obtenerSiguienteId() throws SQLException;
    
    public EstudianteEvaluacionDTO obtenerEstudianteEvaluacionPorMatricula(String matricula) throws SQLException;

    public List<EstudianteEvaluacionDTO> listarEvaluacionesPorProfesor(String numeroDeTrabajador) throws SQLException;

}