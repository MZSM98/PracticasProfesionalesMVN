package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import java.sql.SQLException;

public interface IEstudianteDocumentoDAO {
    
    public boolean insertarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException;
    public boolean editarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorMatricula(String matriculaEstudiante) throws SQLException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorNombreArchivoYMatricula(String nombreArchivo, String matriculaEstudiante) throws SQLException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorIdDocumento(int idDocumento) throws SQLException;

}