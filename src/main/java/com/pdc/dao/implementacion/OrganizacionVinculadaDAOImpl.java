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

    private static final String COLUMNA_RFC_MORAL = "rfcMoral";
    private static final String COLUMNA_NOMBRE_OV = "nombreOV";
    private static final String COLUMNA_DIRECCION_OV = "direccionOV";
    private static final String COLUMNA_TELEFONO_OV = "telefonoOV";
    private static final String COLUMNA_ESTADO_OV = "estadoOV";
    private static final String COLUMNA_ESTADO = "estado";
    private static final String COLUMNA_CIUDAD = "ciudad";
    private static final String COLUMNA_CORREO = "correo";
    private static final String COLUMNA_SECTOR = "sector";

    @Override
    public boolean insertarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException {
        
        String insertarSQL = "INSERT INTO organizacionvinculada (rfcMoral, nombreOV, direccionOV, telefonoOV, estado, ciudad, correo, sector) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, organizacionVinculada.getRfcMoral());
            declaracionPreparada.setString(2, organizacionVinculada.getNombreOV());
            declaracionPreparada.setString(3, organizacionVinculada.getDireccionOV());
            declaracionPreparada.setString(4, organizacionVinculada.getTelefonoOV());
            declaracionPreparada.setString(5, organizacionVinculada.getEstado());
            declaracionPreparada.setString(6, organizacionVinculada.getCiudad());
            declaracionPreparada.setString(7, organizacionVinculada.getCorreo());
            declaracionPreparada.setString(8, organizacionVinculada.getSector());
            
            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean eliminarOrganizacionVinculada(String rfcMoral) throws SQLException {
        
        String eliminarSQL = "DELETE FROM organizacionvinculada WHERE rfcMoral = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(eliminarSQL)) {

            declaracionPreparada.setString(1, rfcMoral);

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculada) throws SQLException {
        
        String actualizarSQL = "UPDATE organizacionvinculada SET nombreOV = ?, direccionOV = ?, telefonoOV = ?, estadoOV = ?, estado = ?, ciudad = ?, correo = ?, sector = ? WHERE rfcMoral = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, organizacionVinculada.getNombreOV());
            declaracionPreparada.setString(2, organizacionVinculada.getDireccionOV());
            declaracionPreparada.setString(3, organizacionVinculada.getTelefonoOV());
            declaracionPreparada.setString(4, organizacionVinculada.getEstadoOV());
            declaracionPreparada.setString(5, organizacionVinculada.getEstado());
            declaracionPreparada.setString(6, organizacionVinculada.getCiudad());
            declaracionPreparada.setString(7, organizacionVinculada.getCorreo());
            declaracionPreparada.setString(8, organizacionVinculada.getSector());
            declaracionPreparada.setString(9, organizacionVinculada.getRfcMoral());
                        
            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public OrganizacionVinculadaDTO buscarOrganizacionVinculada(String rfcMoral) throws SQLException {
        
        String consultaSQL = "SELECT rfcMoral, nombreOV, direccionOV, telefonoOV, estadoOV, estado, ciudad, correo, sector FROM organizacionvinculada WHERE rfcMoral = ?";
        OrganizacionVinculadaDTO organizacionVinculada = null;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, rfcMoral);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                organizacionVinculada = new OrganizacionVinculadaDTO();
                organizacionVinculada.setRfcMoral(resultadoDeOperacion.getString(COLUMNA_RFC_MORAL));
                organizacionVinculada.setNombreOV(resultadoDeOperacion.getString(COLUMNA_NOMBRE_OV));
                organizacionVinculada.setDireccionOV(resultadoDeOperacion.getString(COLUMNA_DIRECCION_OV));
                organizacionVinculada.setTelefonoOV(resultadoDeOperacion.getString(COLUMNA_TELEFONO_OV));
                organizacionVinculada.setEstadoOV(resultadoDeOperacion.getString(COLUMNA_ESTADO_OV));
                organizacionVinculada.setEstado(resultadoDeOperacion.getString(COLUMNA_ESTADO));
                organizacionVinculada.setCiudad(resultadoDeOperacion.getString(COLUMNA_CIUDAD));
                organizacionVinculada.setCorreo(resultadoDeOperacion.getString(COLUMNA_CORREO));
                organizacionVinculada.setSector(resultadoDeOperacion.getString(COLUMNA_SECTOR));
            }
        }

        return organizacionVinculada;
    }
    
    @Override
    public List<OrganizacionVinculadaDTO> listarOrganizacionesVinculadas() throws SQLException {
        
        String consultaTodoSQL = "SELECT rfcMoral, nombreOV, direccionOV, telefonoOV, estadoOV, estado, ciudad, correo, sector FROM organizacionvinculada";
        List<OrganizacionVinculadaDTO> listaOrganizacionVinculada = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaTodoSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                OrganizacionVinculadaDTO organizacionVinculada = new OrganizacionVinculadaDTO();
                organizacionVinculada.setRfcMoral(resultadoDeOperacion.getString(COLUMNA_RFC_MORAL));
                organizacionVinculada.setNombreOV(resultadoDeOperacion.getString(COLUMNA_NOMBRE_OV));
                organizacionVinculada.setTelefonoOV(resultadoDeOperacion.getString(COLUMNA_TELEFONO_OV));
                organizacionVinculada.setDireccionOV(resultadoDeOperacion.getString(COLUMNA_DIRECCION_OV));
                organizacionVinculada.setEstadoOV(resultadoDeOperacion.getString(COLUMNA_ESTADO_OV));
                organizacionVinculada.setEstado(resultadoDeOperacion.getString(COLUMNA_ESTADO));
                organizacionVinculada.setCiudad(resultadoDeOperacion.getString(COLUMNA_CIUDAD));
                organizacionVinculada.setCorreo(resultadoDeOperacion.getString(COLUMNA_CORREO));
                organizacionVinculada.setSector(resultadoDeOperacion.getString(COLUMNA_SECTOR));
                listaOrganizacionVinculada.add(organizacionVinculada);                
            }
        }

        return listaOrganizacionVinculada;
    }
    
    @Override
    public int contarOrganizaciones() throws SQLException {
        
        String contarSQL = "SELECT COUNT(*) FROM organizacionvinculada";
        int totalOrganizaciones = 0;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                
                totalOrganizaciones = resultado.getInt(1);
            }
        }
    
        return totalOrganizaciones;
    }
}