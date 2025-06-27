package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;

public class OrganizacionVinculadaDAOImpl implements IOrganizacionVinculadaDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada = null;
    private ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException {
        
        String insertarSQL = "INSERT INTO organizacionvinculada (rfcMoral, nombreOV, direccionOV, telefonoOV) VALUES (?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, organizacionVinculada.getRfcMoral());
            declaracionPreparada.setString(2, organizacionVinculada.getNombreOV());
            declaracionPreparada.setString(3, organizacionVinculada.getDireccionOV());
            declaracionPreparada.setString(4, organizacionVinculada.getTelefonoOV());
            
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
            
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException {
        
        final String eliminarSQL = "DELETE FROM organizacionvinculada WHERE rfcMoral = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, rfcMoral);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException {
        final String actualizarSQL = "UPDATE organizacionvinculada SET nombreOV = ?, direccionOV = ?, telefonoOV = ?, estadoOV = ? WHERE rfcMoral = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, organizacionVinculada.getNombreOV());
            declaracionPreparada.setString(2, organizacionVinculada.getDireccionOV());
            declaracionPreparada.setString(3, organizacionVinculada.getTelefonoOV());
            declaracionPreparada.setString(4, organizacionVinculada.getEstadoOV());
            declaracionPreparada.setString(5, organizacionVinculada.getRfcMoral());
                        
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
            
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException {
        
        final String consultaSQL = "SELECT rfcMoral, nombreOV, direccionOV, telefonoOV, estadoOV FROM organizacionvinculada WHERE rfcMoral = ?";
        OrganizacionVinculadaDTO organizacionVinculada;
        organizacionVinculada = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, rfcMoral);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                organizacionVinculada = new OrganizacionVinculadaDTO();
                organizacionVinculada.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                organizacionVinculada.setNombreOV(resultadoDeOperacion.getString("nombreOV"));
                organizacionVinculada.setDireccionOV(resultadoDeOperacion.getString("direccionOV"));
                organizacionVinculada.setTelefonoOV(resultadoDeOperacion.getString("telefonoOV"));
                organizacionVinculada.setEstadoOV(resultadoDeOperacion.getString("estadoOV"));
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return organizacionVinculada;
    }
    
    @Override
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException {
        
        final String consultaTodoSQL = "SELECT * FROM organizacionvinculada";
        List<OrganizacionVinculadaDTO> listaOrganizacionVinculada = new ArrayList<>();

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            while(resultadoDeOperacion.next()) {
                
                OrganizacionVinculadaDTO organizacionVinculada = new OrganizacionVinculadaDTO();
                organizacionVinculada.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                organizacionVinculada.setNombreOV(resultadoDeOperacion.getString("nombreOV"));
                organizacionVinculada.setTelefonoOV(resultadoDeOperacion.getString("telefonoOV"));
                organizacionVinculada.setDireccionOV(resultadoDeOperacion.getString("direccionOV"));
                organizacionVinculada.setEstadoOV(resultadoDeOperacion.getString("estadoOV"));
                listaOrganizacionVinculada.add(organizacionVinculada);                
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }

        return listaOrganizacionVinculada;
    }
    
    @Override
    public int contarOrganizaciones() throws SQLException {
    
        final String contarSQL = "SELECT COUNT(*) FROM organizacionvinculada";
        int totalOrganizaciones = 0;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                totalOrganizaciones = resultado.getInt(1);
            }
        }
    
    return totalOrganizaciones;
}
}
