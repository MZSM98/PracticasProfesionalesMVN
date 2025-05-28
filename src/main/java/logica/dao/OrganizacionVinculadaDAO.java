package logica.dao;

import logica.interfaces.InterfazOrganizacionVinculadaDAO;
import accesoadatos.dto.OrganizacionVinculadaDTO;
import accesoadatos.ConexionBD;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrganizacionVinculadaDAO implements InterfazOrganizacionVinculadaDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada = null;
    private ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException, IOException {
        
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
    public boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException {
        
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
    public boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException, IOException {
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
    public OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException, IOException {
        
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
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException, IOException {
        
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
}
