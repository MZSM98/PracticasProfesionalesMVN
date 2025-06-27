package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IExperienciaEducativa;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaEducativaDAOImpl implements IExperienciaEducativa {
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IProfesorExperienciaEducativaDAO interfazProfesorExperienciaEducativaDAO;
    
    public static final String NRC = "nrc";
    public static final String NOMBRE = "nombre";
    public static final String NUMERO_DE_TRABAJADOR = "numeroDeTrabajador";
    
    public ExperienciaEducativaDAOImpl() {
        
        interfazProfesorExperienciaEducativaDAO = new ProfesorExperienciaEducativaDAOImpl();
    }
    
    @Override
    public ExperienciaEducativaDTO obtenerExperienciaEducativaPorNRC(String nrc) throws SQLException {
        
        String consultaSQL = "SELECT nrc, nombre, numeroDeTrabajador FROM experienciaeducativa WHERE nrc = ?";
        ExperienciaEducativaDTO experienciaEducativa = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, nrc);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                String numeroDeTrabajador = resultadoDeOperacion.getString(NUMERO_DE_TRABAJADOR);
                ProfesorExperienciaEducativaDTO profesor;
                profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
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
        return experienciaEducativa;
    }
    
    @Override
    public List<ExperienciaEducativaDTO> listarExperienciaEducativa() throws SQLException {
        
        String consultaTodoSQL = "SELECT nrc, nombre, numeroDeTrabajador FROM experienciaeducativa";
        List<ExperienciaEducativaDTO> listaExperienciasEducativas = new ArrayList<>();
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            while (resultadoDeOperacion.next()) {
                
                ExperienciaEducativaDTO experienciaEducativa;
                experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                
                String numeroDeTrabajador;
                numeroDeTrabajador = resultadoDeOperacion.getString(NUMERO_DE_TRABAJADOR);
                ProfesorExperienciaEducativaDTO profesor;
                profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
                
                listaExperienciasEducativas.add(experienciaEducativa);
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
        return listaExperienciasEducativas;
    }
    
    @Override
    public ExperienciaEducativaDTO buscarExperienciaEducativaPorProfesor(String numeroDeTrabajador) throws SQLException{
        
        String consultaSQL = "SELECT nrc, nombre FROM experienciaeducativa WHERE numeroDeTrabajador = ?";
        ExperienciaEducativaDTO experienciaEducativa;
        experienciaEducativa = null;
        
        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                
                ProfesorExperienciaEducativaDTO profesor;
                profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
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
        return experienciaEducativa;
    }
    
    @Override
    public boolean asignarProfesorAExperienciaEducativa(String nrc, String numeroDeTrabajador) throws SQLException {
        String actualizarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = ? WHERE nrc = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            declaracionPreparada.setString(2, nrc);
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }
    
    @Override
    public boolean reasignarProfesorExperienciaEducativa(String nrcAnterior, String nrcNuevo, String numeroDeTrabajador) throws SQLException {
        
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            conexionBD.setAutoCommit(false);
            if (nrcAnterior != null && !nrcAnterior.equals(nrcNuevo)) {
                
                String liberarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = NULL WHERE nrc = ?";
                declaracionPreparada = conexionBD.prepareStatement(liberarSQL);
                declaracionPreparada.setString(1, nrcAnterior);
                declaracionPreparada.executeUpdate();
                declaracionPreparada.close();
            }

            String asignarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = ? WHERE nrc = ?";
            declaracionPreparada = conexionBD.prepareStatement(asignarSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            declaracionPreparada.setString(2, nrcNuevo);
            declaracionPreparada.executeUpdate();

            conexionBD.commit();
            actualizacionExitosa = true;

        } finally {
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.setAutoCommit(true);
                conexionBD.close();
            }
        }
        return actualizacionExitosa;
    }
}