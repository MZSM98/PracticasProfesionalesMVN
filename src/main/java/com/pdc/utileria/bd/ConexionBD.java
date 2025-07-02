package com.pdc.utileria.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;


public class ConexionBD {
    
    private static ConexionBD instanciaConexionBaseDatos;
    private final Connection conexionBaseDatos;
    private static final Logger LOG = Logger.getLogger(ConexionBD.class);
    
    public ConexionBD() throws SQLException {
       
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
        
        this.conexionBaseDatos = DriverManager.getConnection(url, user, password);  
        
    }
    
    public static ConexionBD getInstanciaConexionBaseDatos() throws SQLException {
        
        if (instanciaConexionBaseDatos == null || instanciaConexionBaseDatos.getConexionBaseDatos().isClosed()) {
            instanciaConexionBaseDatos = new ConexionBD();
        }
        
        return instanciaConexionBaseDatos;
    }
    
    public Connection getConexionBaseDatos() {
        
        return conexionBaseDatos;
    }
}
