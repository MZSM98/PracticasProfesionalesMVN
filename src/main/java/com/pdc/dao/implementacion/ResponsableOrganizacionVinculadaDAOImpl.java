package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;

public class ResponsableOrganizacionVinculadaDAOImpl implements IResponsableOrganizacionVinculadaDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO responsableov (rfc, puesto, nombreResponsable) VALUES (?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, responsable.getRfc());
            declaracionPreparada.setString(2, responsable.getCargo());
            declaracionPreparada.setString(3, responsable.getNombreResponsable());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarResponsableOV(String rfc) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM responsableov WHERE rfc = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, rfc);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException, IOException {
        String actualizarSQL = "UPDATE responsableov SET puesto = ?, nombreResponsable = ? WHERE rfc = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, responsable.getCargo());
            declaracionPreparada.setString(2, responsable.getNombreResponsable());
            declaracionPreparada.setString(3, responsable.getRfc());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfc) throws SQLException, IOException {
        String consultaSQL = "SELECT rfc, puesto, nombreResponsable FROM responsableov WHERE rfc = ?";
        ResponsableOrganizacionVinculadaDTO responsable = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, rfc);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                responsable = new ResponsableOrganizacionVinculadaDTO();
                responsable.setRfc(resultadoDeOperacion.getString("rfc"));
                responsable.setCargo(resultadoDeOperacion.getString("puesto"));
                responsable.setNombreResponsable(resultadoDeOperacion.getString("nombreResponsable"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return responsable;
    }
}
