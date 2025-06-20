package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteDocumentoDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.modelo.dto.EstudianteDocumentoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteDocumentoDAOImpl implements IEstudianteDocumentoDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IEstudianteDAO interfazEstudianteDAO;
    private final IDocumentoDAO interfazDocumentoDAO;
    
    public static final String ID_REGISTRO = "idregistro";
    public static final String MATRICULA_ESTUDIANTE = "matriculaestudiante";
    public static final String ID_DOCUMENTO = "iddocumento";
    public static final String RUTA = "ruta";
    public static final String NOMBRE_ARCHIVO = "nombrearchivo";
    
    public EstudianteDocumentoDAOImpl(){
        interfazEstudianteDAO = new EstudianteDAOImpl();
        interfazDocumentoDAO = new DocumentoDAOImpl();
    }
    
    @Override
    public boolean insertarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException, IOException {
        
        String insertarSQL = "INSERT INTO estudiantedocumento (matriculaestudiante, iddocumento, ruta, nombrearchivo) VALUES (?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, estudianteDocumento.getEstudiante().getMatricula());
            declaracionPreparada.setInt(2, estudianteDocumento.getDocumento().getIdDocumento());
            declaracionPreparada.setString(3, estudianteDocumento.getRuta());
            declaracionPreparada.setString(4, estudianteDocumento.getNombreArchivo());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }
    
    @Override
    public boolean editarEstudianteDocumento(EstudianteDocumentoDTO estudianteDocumento) throws SQLException, IOException {
        
        String actualizarSQL = "UPDATE estudiantedocumento SET matriculaestudiante = ?, iddocumento = ?, ruta = ?, nombrearchivo = ? WHERE idregistro = ?";
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, estudianteDocumento.getEstudiante().getMatricula());
            declaracionPreparada.setInt(2, estudianteDocumento.getDocumento().getIdDocumento());
            declaracionPreparada.setString(3, estudianteDocumento.getRuta());
            declaracionPreparada.setString(4, estudianteDocumento.getNombreArchivo());
            declaracionPreparada.setInt(5, estudianteDocumento.getIdRegistro());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }
    
    @Override
    public EstudianteDocumentoDTO obtenerEstudianteDocumentoPorMatricula(String matriculaEstudiante) throws SQLException, IOException {
        
        String consultaSQL = "SELECT idregistro, matriculaestudiante, iddocumento, ruta, nombrearchivo FROM estudiantedocumento WHERE matriculaestudiante = ?";
        EstudianteDocumentoDTO estudianteDocumento = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, matriculaEstudiante);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                estudianteDocumento = new EstudianteDocumentoDTO();
                estudianteDocumento.setIdRegistro(resultadoDeOperacion.getInt(ID_REGISTRO));
                
                String matricula = resultadoDeOperacion.getString(MATRICULA_ESTUDIANTE);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteDocumento.setEstudiante(estudiante);
                
                Integer idDocumentoResult = resultadoDeOperacion.getInt(ID_DOCUMENTO);
                DocumentoDTO documento = interfazDocumentoDAO.buscarDocumento(idDocumentoResult);
                estudianteDocumento.setDocumento(documento);
                
                estudianteDocumento.setRuta(resultadoDeOperacion.getString(RUTA));
                estudianteDocumento.setNombreArchivo(resultadoDeOperacion.getString(NOMBRE_ARCHIVO));
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return estudianteDocumento;
    }
}