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

    Connection conexionBD;
    PreparedStatement declaracionPreparada = null;
    ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException, IOException {
        
        String INSERTAR_SQL = "INSERT INTO organizacionvinculada (rfcMoral, nombreOV, direccionOV, telefonoOV) VALUES (?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(INSERTAR_SQL);
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
        
        final String ELIMINAR_SQL = "DELETE FROM organizacionvinculada WHERE rfcMoral = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(ELIMINAR_SQL);
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
        final String ACTUALIZAR_SQL = "UPDATE organizacionvinculada SET nombreOV = ?, direccionOV = ?, telefonoOV = ?, estadoOV = ? WHERE rfcMoral = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(ACTUALIZAR_SQL);
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
        
        final String CONSULTA_SQL = "SELECT rfcMoral, nombreOV, direccionOV, telefonoOV, estadoOV FROM organizacionvinculada WHERE rfcMoral = ?";
        OrganizacionVinculadaDTO organizacionVinculada;
        organizacionVinculada = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(CONSULTA_SQL);
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
        
        final String CONSULTATODO_SQL = "SELECT * FROM organizacionvinculada";
        List<OrganizacionVinculadaDTO> listaOrganizacionVinculada = new ArrayList<>();

        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultadoOrganizacionVinculada = null;

        try {
            
            conexion = new ConexionBD().getConexionBD();
            preparedStatement = conexion.prepareStatement(CONSULTATODO_SQL);
            resultadoOrganizacionVinculada = preparedStatement.executeQuery();
            while(resultadoOrganizacionVinculada.next()) {
                
                OrganizacionVinculadaDTO organizacionVinculada = new OrganizacionVinculadaDTO();
                organizacionVinculada.setRfcMoral(resultadoOrganizacionVinculada.getString("rfcMoral"));
                organizacionVinculada.setNombreOV(resultadoOrganizacionVinculada.getString("nombreOV"));
                organizacionVinculada.setTelefonoOV(resultadoOrganizacionVinculada.getString("telefonoOV"));
                organizacionVinculada.setDireccionOV(resultadoOrganizacionVinculada.getString("direccionOV"));
                organizacionVinculada.setEstadoOV(resultadoOrganizacionVinculada.getString("estadoOV"));
                listaOrganizacionVinculada.add(organizacionVinculada);                
            }
        } finally {
            
            if (resultadoOrganizacionVinculada != null) resultadoOrganizacionVinculada.close();
            if (preparedStatement != null) preparedStatement.close();
            if (conexion != null) conexion.close();
        }

        return listaOrganizacionVinculada;
    }
}
