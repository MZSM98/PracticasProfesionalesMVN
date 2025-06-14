package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.CredencialesIngresoDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pdc.dao.interfaz.ICredencialesIngresoDAO;

public class CredencialesIngresoDAOImpl implements ICredencialesIngresoDAO {

    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO credencialesingreso (usuario, contrasena) VALUES (?, ?)";
        boolean insercionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, credenciales.getUsuario());
            declaracionPreparada.setString(2, credenciales.getContrasena());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarCredencialesIngreso(String usuario) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM credencialesingreso WHERE usuario = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, usuario);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarCredencialesIngreso(CredencialesIngresoDTO credenciales) throws SQLException, IOException {
        String actualizarSQL = "UPDATE credencialesingreso SET contrasena = ? WHERE usuario = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, credenciales.getContrasena());
            declaracionPreparada.setString(2, credenciales.getUsuario());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public CredencialesIngresoDTO buscarCredencialesIngreso(String usuario) throws SQLException, IOException {
        String consultaSQL = "SELECT usuario, contrasena FROM credencialesingreso WHERE usuario = ?";
        CredencialesIngresoDTO credenciales = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, usuario);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                credenciales = new CredencialesIngresoDTO();
                credenciales.setUsuario(resultadoDeOperacion.getString("usuario"));
                credenciales.setContrasena(resultadoDeOperacion.getString("contrasena"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return credenciales;
    }
}
