
package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.CoordinadorDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pdc.dao.interfaz.ICoordinadorDAO;

public class CoordinadorDAOImpl implements ICoordinadorDAO {
    
    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;
    
    @Override
    public boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException {
        String insertarSQL = "INSERT INTO coordinador (numeroDeTrabajador, nombreCoordinador, telefono) VALUES (?, ?, ?)";
        boolean insercionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, coordinador.getNumeroDeTrabajador());
            declaracionPreparada.setString(2, coordinador.getNombreCoordinador());
            declaracionPreparada.setString(3, coordinador.getTelefono());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();  
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException {
        String eliminarSQL = "DELETE FROM coordinador WHERE numeroDeTrabajador = ?";
        boolean eliminacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException {
        String actualizarSQL = "UPDATE coordinador SET nombreCoordinador = ?, telefono = ? WHERE numeroDeTrabajador = ?";
        boolean actualizacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, coordinador.getNombreCoordinador());
            declaracionPreparada.setString(2, coordinador.getTelefono());
            declaracionPreparada.setString(3, coordinador.getNumeroDeTrabajador());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException {
        String consultaSQL = "SELECT numeroDeTrabajador, nombreCoordinador, telefono FROM coordinador WHERE numeroDeTrabajador = ?";
        CoordinadorDTO coordinador = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                coordinador = new CoordinadorDTO();
                coordinador.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                coordinador.setNombreCoordinador(resultadoDeOperacion.getString("nombreCoordinador"));
                coordinador.setTelefono(resultadoDeOperacion.getString("telefono"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return coordinador;
    }
}
