package com.pdc.utileria.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConexionBD {

    private static ConexionBD instanciaConexionBaseDatos;
    private static final Logger LOG = Logger.getLogger(ConexionBD.class);

    private ConexionBD() {
        
    }

    public Connection conectar() throws SQLException {
        Properties props = ConfiguracionBD.loadProperties();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("No se encontró el driver para la base de datos");
            throw new SQLException("Driver de base de datos no encontrado", e);
        }
<<<<<<< HEAD
        
        this.conexionBaseDatos = DriverManager.getConnection(url, user, password);  
        
    }
    
    public static ConexionBD getInstanciaConexionBaseDatos() throws SQLException {
        
        if (instanciaConexionBaseDatos == null || instanciaConexionBaseDatos.getConexionBaseDatos().isClosed()) {
            instanciaConexionBaseDatos = new ConexionBD();
=======

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            categorizarExcepcion(e);
            return null;
        }
    }

    private void categorizarExcepcion(SQLException e) throws SQLException {
        if (e.getErrorCode() == 1045 && "28000".equals(e.getSQLState())) {
            throw new SQLInvalidAuthorizationSpecException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
        if ("08001".equals(e.getSQLState()) || "08S01".equals(e.getSQLState())
                || e.getMessage().toLowerCase().contains("communications link failure")) {
            throw new SQLNonTransientConnectionException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
        throw e;
    }

    public static ConexionBD getInstancia() {
        synchronized (ConexionBD.class) {
            if (instanciaConexionBaseDatos == null) {
                instanciaConexionBaseDatos = new ConexionBD();
            }
>>>>>>> e1e8a4d2fe1ed54be4d82b94b811988debf47b3b
        }
        return instanciaConexionBaseDatos;
    }
}
