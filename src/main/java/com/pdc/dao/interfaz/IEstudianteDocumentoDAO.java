package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import java.io.IOException;
import java.sql.SQLException;

public interface IEstudianteDocumentoDAO {
    
    public boolean insertarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException, IOException;
    public boolean editarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException, IOException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorMatricula(String matriculaEstudiante) throws SQLException, IOException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorNombreArchivoYMatricula(String nombreArchivo, String matriculaEstudiante) throws SQLException, IOException;
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorIdDocumento(int idDocumento) throws SQLException, IOException;

}