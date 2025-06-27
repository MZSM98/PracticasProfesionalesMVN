package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativaDAO;
import com.pdc.dao.interfaz.IExperienciaEducativaDAO;

public class EstudianteExperienciaEducativaDAOImpl implements IEstudianteExperienciaEducativaDAO {
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IEstudianteDAO interfazEstudianteDAO; 
    private final IExperienciaEducativaDAO interfazExperienciaEducativaDAO;
    
    public static final String ID_EXPERIENCIA_ASIGNADA = "idexperienciaasignada";
    public static final String NRC = "nrc";
    public static final String MATRICULA = "matricula";
    
    public EstudianteExperienciaEducativaDAOImpl() {
        
        interfazExperienciaEducativaDAO = new ExperienciaEducativaDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
    }
    
    @Override
    public boolean insertarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException {
        
        String insertarSQL = "INSERT INTO estudianteexperienciaeducativa (nrc, matricula) VALUES (?, ?)";
        boolean insercionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, estudianteAsignado.getExperienciaEducativa().getNrc());
            declaracionPreparada.setString(2, estudianteAsignado.getEstudiante().getMatricula());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return insercionExitosa;
    }
    
    @Override
    public boolean editarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException {
        String actualizarSQL = "UPDATE estudianteexperienciaeducativa SET nrc = ?, matricula = ? WHERE idexperienciaasignada = ?";
        boolean actualizacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            
            declaracionPreparada.setString(1, estudianteAsignado.getExperienciaEducativa().getNrc());
            declaracionPreparada.setString(2, estudianteAsignado.getEstudiante().getMatricula());
            declaracionPreparada.setInt(3, estudianteAsignado.getIdExperienciaAsignada());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return actualizacionExitosa;
    }
    
    @Override
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorID(Integer idExperienciaAsignada) throws SQLException {
        
        String consultaSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa WHERE idexperienciaasignada = ?";
        EstudianteExperienciaEducativaDTO estudianteAsignado = null;
        
        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, idExperienciaAsignada);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                estudianteAsignado = new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));
                
                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa;
                experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                experienciaEducativa.setNrc(nrc);
                
                String matricula = resultadoDeOperacion.getString(MATRICULA);
                EstudianteDTO estudiante;
                estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudiante.setMatricula(matricula);
            }
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return estudianteAsignado;
    }
    
    @Override
    public List<EstudianteExperienciaEducativaDTO> listarExperienciaAsignada() throws SQLException {
        
        String consultaTodoSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa";
        List<EstudianteExperienciaEducativaDTO> listaEstudiantesAsignados;
        listaEstudiantesAsignados = new ArrayList<>();
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            while (resultadoDeOperacion.next()) {
                
                EstudianteExperienciaEducativaDTO estudianteAsignado;
                estudianteAsignado= new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));
                
                String nrc;
                nrc= resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa;
                experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                estudianteAsignado.setExperienciaEducativa(experienciaEducativa);
                
                String matricula;
                matricula = resultadoDeOperacion.getString(MATRICULA);
                EstudianteDTO estudiante;
                estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteAsignado.setEstudiante(estudiante);
                
                listaEstudiantesAsignados.add(estudianteAsignado);
            }
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return listaEstudiantesAsignados;
    }
    
    @Override
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorEstudiante(String matriculaEstudiante) throws SQLException{
        
        String consultaSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa WHERE matricula = ?";
        EstudianteExperienciaEducativaDTO experienciaAsignada = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, matriculaEstudiante);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                experienciaAsignada = new EstudianteExperienciaEducativaDTO();
                experienciaAsignada.setIdExperienciaAsignada((Integer)resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));

                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                experienciaAsignada.setExperienciaEducativa(experienciaEducativa);

                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matriculaEstudiante);
                experienciaAsignada.setEstudiante(estudiante);
            }
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return experienciaAsignada;
    }

}