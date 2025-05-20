package logica.dao;

import logica.interfaces.InterfazAutoEvaluacionDAO;
import accesoadatos.dto.AutoEvaluacionDTO;
import accesoadatos.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class AutoEvaluacionDAO implements InterfazAutoEvaluacionDAO {
    
    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;
    
    @Override
    public boolean insertarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO autoevaluacion (fechaDeEvaluacion, puntaje, totalHoras) VALUES (?, ?, ?)";
        boolean insercionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setDate(1, autoEvaluacion.getFechaDeEvaluacion());
            declaracionPreparada.setInt(2, autoEvaluacion.getPuntaje());
            declaracionPreparada.setInt(3, autoEvaluacion.getTotalHoras());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();  
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM autoevaluacion WHERE fechaDeEvaluacion = ?";
        boolean eliminacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setDate(1, fechaEvaluacion);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarAutoEvaluacion(AutoEvaluacionDTO autoEvaluacion) throws SQLException, IOException {
        String actualizarSQL = "UPDATE autoevaluacion SET puntaje = ?, totalHoras = ? WHERE fechaDeEvaluacion = ?";
        boolean actualizacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setInt(1, autoEvaluacion.getPuntaje());
            declaracionPreparada.setInt(2, autoEvaluacion.getTotalHoras());
            declaracionPreparada.setDate(3, autoEvaluacion.getFechaDeEvaluacion());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public AutoEvaluacionDTO buscarAutoEvaluacion(Date fechaEvaluacion) throws SQLException, IOException {
        String consultaSQL = "SELECT fechaDeEvaluacion, puntaje, totalHoras FROM autoevaluacion WHERE fechaDeEvaluacion = ?";
        AutoEvaluacionDTO autoEvaluacion = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setDate(1, fechaEvaluacion);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                autoEvaluacion = new AutoEvaluacionDTO();
                autoEvaluacion.setFechaDeEvaluacion(resultadoDeOperacion.getDate("fechaDeEvaluacion"));
                autoEvaluacion.setPuntaje(resultadoDeOperacion.getInt("puntaje"));
                autoEvaluacion.setTotalHoras(resultadoDeOperacion.getInt("totalHoras"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return autoEvaluacion;
    }

}