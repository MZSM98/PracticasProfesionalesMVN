package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativa;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteExperienciaEducativaDAOImpl implements IEstudianteExperienciaEducativa {
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IEstudianteDAO interfazEstudianteDAO; 
    private final IExperienciaEducativa interfazExperienciaEducativa;
    
    public static final String ID_EXPERIENCIA_ASIGNADA = "idexperienciaasignada";
    public static final String NRC = "nrc";
    public static final String MATRICULA = "matricula";
    
    public EstudianteExperienciaEducativaDAOImpl() {
        interfazExperienciaEducativa = new ExperienciaEducativaDA();
        interfazEstudianteDAO = new EstudianteDAOImpl();
    }
    
    @Override
    public boolean insertarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO estudianteexperienciaeducativa (nrc, matricula) VALUES (?, ?)";
        boolean insercionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, estudianteAsignado.getExperienciaEducativa());
            declaracionPreparada.setString(2, estudianteAsignado.getEstudiante());
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
    public boolean editarExperienciaAsignado(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException, IOException {
        String actualizarSQL = "UPDATE estudianteexperienciaeducativa SET nrc = ?, matricula = ? WHERE idexperienciaasignada = ?";
        boolean actualizacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, estudianteAsignado.getNrc());
            declaracionPreparada.setString(2, estudianteAsignado.getMatricula());
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
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorID(Integer idExperienciaAsignada) throws SQLException, IOException {
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
                estudianteAsignado.setExperienciaEducativa(resultadoDeOperacion.getString(NRC));
                estudianteAsignado.setMatricula(resultadoDeOperacion.getString(MATRICULA));
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
    public List<EstudianteExperienciaEducativaDTO> listarExperienciaAsignada() throws SQLException, IOException {
        String consultaTodoSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa";
        List<EstudianteExperienciaEducativaDTO> listaEstudiantesAsignados = new ArrayList<>();
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            while (resultadoDeOperacion.next()) {
                EstudianteExperienciaEducativaDTO estudianteAsignado = new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));
                estudianteAsignado.setNrc(resultadoDeOperacion.getString(NRC));
                estudianteAsignado.setMatricula(resultadoDeOperacion.getString(MATRICULA));
                
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
}