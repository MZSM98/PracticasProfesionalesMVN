package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteDocumentoDAOImpl implements IEstudianteDocumentoDAO {

    private final IEstudianteDAO interfazEstudianteDAO;
    private final IDocumentoDAO interfazDocumentoDAO;

    public EstudianteDocumentoDAOImpl() {
        
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
    }

    @Override
    public boolean insertarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException {
        
        String insertarSQL = "INSERT INTO estudiantedocumento (matriculaestudiante, iddocumento, ruta, nombrearchivo) VALUES (?, ?, ?, ?)";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, estudianteDocumento.getEstudiante().getMatricula());
            declaracionPreparada.setInt(2, estudianteDocumento.getDocumento().getIdDocumento());
            declaracionPreparada.setString(3, estudianteDocumento.getRuta());
            declaracionPreparada.setString(4, estudianteDocumento.getNombreArchivo());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException {
        
        String actualizarSQL = "UPDATE estudiantedocumento SET matriculaestudiante = ?, iddocumento = ?, ruta = ?, nombrearchivo = ? WHERE idregistro = ?";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, estudianteDocumento.getEstudiante().getMatricula());
            declaracionPreparada.setInt(2, estudianteDocumento.getDocumento().getIdDocumento());
            declaracionPreparada.setString(3, estudianteDocumento.getRuta());
            declaracionPreparada.setString(4, estudianteDocumento.getNombreArchivo());
            declaracionPreparada.setInt(5, estudianteDocumento.getIdRegistro());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorMatricula(String matriculaEstudiante) throws SQLException {
        
        String consultaSQL = "SELECT idregistro, matriculaestudiante, iddocumento, ruta, nombrearchivo FROM estudiantedocumento WHERE matriculaestudiante = ?";
        EstudianteDocumentoDTO estudianteDocumento = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, matriculaEstudiante);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                estudianteDocumento = new EstudianteDocumentoDTO();
                estudianteDocumento.setIdRegistro(resultadoDeOperacion.getInt("idregistro"));

                String matricula = resultadoDeOperacion.getString("matriculaestudiante");
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteDocumento.setEstudiante(estudiante);

                Integer idDocumentoResult = resultadoDeOperacion.getInt("iddocumento");
                DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(idDocumentoResult);
                estudianteDocumento.setDocumento(documento);

                estudianteDocumento.setRuta(resultadoDeOperacion.getString("ruta"));
                estudianteDocumento.setNombreArchivo(resultadoDeOperacion.getString("nombrearchivo"));
            }
        }

        return estudianteDocumento;
    }

    @Override
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorNombreArchivoYMatricula(String nombreArchivo, String matriculaEstudiante) throws SQLException {
        
        String consultaSQL = "SELECT idregistro, matriculaestudiante, iddocumento, ruta, nombrearchivo FROM estudiantedocumento WHERE nombrearchivo = ? AND matriculaestudiante = ?";
        EstudianteDocumentoDTO estudianteDocumento = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, nombreArchivo);
            declaracionPreparada.setString(2, matriculaEstudiante);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                estudianteDocumento = new EstudianteDocumentoDTO();
                estudianteDocumento.setIdRegistro(resultadoDeOperacion.getInt("idregistro"));

                String matricula = resultadoDeOperacion.getString("matriculaestudiante");
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteDocumento.setEstudiante(estudiante);

                Integer idDocumentoResult = resultadoDeOperacion.getInt("iddocumento");
                DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(idDocumentoResult);
                estudianteDocumento.setDocumento(documento);

                estudianteDocumento.setRuta(resultadoDeOperacion.getString("ruta"));
                estudianteDocumento.setNombreArchivo(resultadoDeOperacion.getString("nombrearchivo"));
            }
        }

        return estudianteDocumento;
    }

    @Override
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorIdDocumento(int idDocumento) throws SQLException {
        
        String consultaSQL = "SELECT idregistro, matriculaestudiante, iddocumento, ruta, nombrearchivo FROM estudiantedocumento WHERE iddocumento = ?";
        EstudianteDocumentoDTO estudianteDocumento = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idDocumento);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                estudianteDocumento = new EstudianteDocumentoDTO();
                estudianteDocumento.setIdRegistro(resultadoDeOperacion.getInt("idregistro"));

                String matricula = resultadoDeOperacion.getString("matriculaestudiante");
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteDocumento.setEstudiante(estudiante);

                Integer idDocumentoResult = resultadoDeOperacion.getInt("iddocumento");
                DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(idDocumentoResult);
                estudianteDocumento.setDocumento(documento);

                estudianteDocumento.setRuta(resultadoDeOperacion.getString("ruta"));
                estudianteDocumento.setNombreArchivo(resultadoDeOperacion.getString("nombrearchivo"));
            }
        }

        return estudianteDocumento;
    }
}