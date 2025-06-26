package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IExperienciaEducativa;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.io.IOException;
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
    public ExperienciaEducativaDTO obtenerExperienciaEducativaPorNRC(String nrc) throws SQLException, IOException {
        
        String consultaSQL = "SELECT nrc, nombre FROM experienciaeducativa WHERE nrc = ?";
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
    public List<ExperienciaEducativaDTO> listarExperienciaEducativa() throws SQLException, IOException {
        
        String consultaTodoSQL = "SELECT nrc, nombre FROM experienciaeducativa";
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
    public ExperienciaEducativaDTO buscarExperienciaEducativaPorProfesor(String numeroDeTrabajador) throws SQLException, IOException{
        
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
}