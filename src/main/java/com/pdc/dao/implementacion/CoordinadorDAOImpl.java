package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.CoordinadorDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pdc.dao.interfaz.ICoordinadorDAO;

public class CoordinadorDAOImpl implements ICoordinadorDAO {
    
    @Override
    public boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException {
        
        String insertarSQL = "INSERT INTO coordinador (numeroDeTrabajador, nombreCoordinador, telefono) VALUES (?, ?, ?)";
        
        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {
            
            declaracionPreparada.setString(1, coordinador.getNumeroDeTrabajador());
            declaracionPreparada.setString(2, coordinador.getNombreCoordinador());
            declaracionPreparada.setString(3, coordinador.getTelefono());
            
            int filasAfectadas = declaracionPreparada.executeUpdate();
            
            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException {
        
        String eliminarSQL = "DELETE FROM coordinador WHERE numeroDeTrabajador = ?";
        
        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(eliminarSQL)) {
            
            declaracionPreparada.setString(1, numeroDeTrabajador);
            
            int filasAfectadas = declaracionPreparada.executeUpdate();
            
            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException {
        
        String actualizarSQL = "UPDATE coordinador SET nombreCoordinador = ?, telefono = ? WHERE numeroDeTrabajador = ?";
        
        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {
            
            declaracionPreparada.setString(1, coordinador.getNombreCoordinador());
            declaracionPreparada.setString(2, coordinador.getTelefono());
            declaracionPreparada.setString(3, coordinador.getNumeroDeTrabajador());
            
            int filasAfectadas = declaracionPreparada.executeUpdate();
            
            return filasAfectadas > 0;
        }
    }

    @Override
    public CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException {
        
        String consultaSQL = "SELECT numeroDeTrabajador, nombreCoordinador, telefono FROM coordinador WHERE numeroDeTrabajador = ?";
        CoordinadorDTO coordinador = null;
        
        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {
            
            declaracionPreparada.setString(1, numeroDeTrabajador);
            
            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                coordinador = new CoordinadorDTO();
                coordinador.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                coordinador.setNombreCoordinador(resultadoDeOperacion.getString("nombreCoordinador"));
                coordinador.setTelefono(resultadoDeOperacion.getString("telefono"));
            }
        }
        return coordinador;
    }
}