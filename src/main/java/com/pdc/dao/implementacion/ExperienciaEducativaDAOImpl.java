package com.pdc.dao.implementacion;

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
import com.pdc.dao.interfaz.IExperienciaEducativaDAO;

public class ExperienciaEducativaDAOImpl implements IExperienciaEducativaDAO {
    
    private static final String NRC = "nrc";
    private static final String NOMBRE = "nombre";
    private static final String NUMERO_DE_TRABAJADOR = "numeroDeTrabajador";
    
    private final IProfesorExperienciaEducativaDAO interfazProfesorExperienciaEducativaDAO;
    
    public ExperienciaEducativaDAOImpl() {
        
        interfazProfesorExperienciaEducativaDAO = new ProfesorExperienciaEducativaDAOImpl();
    }
    
    @Override
    public ExperienciaEducativaDTO obtenerExperienciaEducativaPorNRC(String nrc) throws SQLException {
        
        String consultaSQL = "SELECT nrc, nombre, numeroDeTrabajador FROM experienciaeducativa WHERE nrc = ?";
        ExperienciaEducativaDTO experienciaEducativa = null;
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, nrc);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                String numeroDeTrabajador = resultadoDeOperacion.getString(NUMERO_DE_TRABAJADOR);
                ProfesorExperienciaEducativaDTO profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
            }
        }

        return experienciaEducativa;
    }
    
    @Override
    public List<ExperienciaEducativaDTO> listarExperienciaEducativa() throws SQLException {
        
        String consultaTodoSQL = "SELECT nrc, nombre, numeroDeTrabajador FROM experienciaeducativa";
        List<ExperienciaEducativaDTO> listaExperienciasEducativas = new ArrayList<>();
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaTodoSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                ExperienciaEducativaDTO experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                
                String numeroDeTrabajador = resultadoDeOperacion.getString(NUMERO_DE_TRABAJADOR);
                ProfesorExperienciaEducativaDTO profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
                
                listaExperienciasEducativas.add(experienciaEducativa);
            }
        }

        return listaExperienciasEducativas;
    }
    
    @Override
    public ExperienciaEducativaDTO buscarExperienciaEducativaPorProfesor(String numeroDeTrabajador) throws SQLException {
        
        String consultaSQL = "SELECT nrc, nombre FROM experienciaeducativa WHERE numeroDeTrabajador = ?";
        ExperienciaEducativaDTO experienciaEducativa = null;
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, numeroDeTrabajador);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                experienciaEducativa = new ExperienciaEducativaDTO();
                experienciaEducativa.setNrc(resultadoDeOperacion.getString(NRC));
                experienciaEducativa.setNombre(resultadoDeOperacion.getString(NOMBRE));
                
                ProfesorExperienciaEducativaDTO profesor = interfazProfesorExperienciaEducativaDAO.buscarProfesorEE(numeroDeTrabajador);
                experienciaEducativa.setProfesor(profesor);
            }
        }

        return experienciaEducativa;
    }
    
    @Override
    public boolean asignarProfesorAExperienciaEducativa(String nrc, String numeroDeTrabajador) throws SQLException {
        
        String actualizarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = ? WHERE nrc = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, numeroDeTrabajador);
            declaracionPreparada.setString(2, nrc);

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }
    
    @Override
    public boolean reasignarProfesorExperienciaEducativa(String nrcAnterior, String nrcNuevo, String numeroDeTrabajador) throws SQLException {
        
        try (Connection conexion = ConexionBD.getInstancia().conectar()) {

            conexion.setAutoCommit(false);

            if (nrcAnterior != null && !nrcAnterior.equals(nrcNuevo)) {
                
                String liberarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = NULL WHERE nrc = ?";
                try (PreparedStatement declaracionPreparada = conexion.prepareStatement(liberarSQL)) {
                    
                    declaracionPreparada.setString(1, nrcAnterior);
                    declaracionPreparada.executeUpdate();
                }
            }

            String asignarSQL = "UPDATE experienciaeducativa SET numeroDeTrabajador = ? WHERE nrc = ?";
            try (PreparedStatement declaracionPreparada = conexion.prepareStatement(asignarSQL)) {
                
                declaracionPreparada.setString(1, numeroDeTrabajador);
                declaracionPreparada.setString(2, nrcNuevo);

                int filasAfectadas = declaracionPreparada.executeUpdate();

                if (filasAfectadas > 0) {
                    
                    conexion.commit();
                    return true;
                } else {
                    
                    conexion.rollback();
                    return false;
                }
            }
        }
    }
}
