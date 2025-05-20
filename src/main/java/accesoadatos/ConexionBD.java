package accesoadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;


public class ConexionBD {
    
    private static ConexionBD instance;
    private final Connection conexionBD;
    private static final Logger LOG = Logger.getLogger(ConexionBD.class);
    
    public ConexionBD() throws SQLException {
       
        Properties props = ConfiguracionDB.loadProperties();
        String URL = props.getProperty("db.url");
        String USER = props.getProperty("db.user");
        String PASSWORD = props.getProperty("db.password");
        try {
        
            Class.forName("com.mysql.cj.jdbc.Driver"); 

        } catch (ClassNotFoundException e) {
            
            LOG.error("No se encontró el driver para la base de datos");
            throw new SQLException("Driver de base de datos no encontrado", e);
        }
        
        this.conexionBD = DriverManager.getConnection(URL, USER, PASSWORD);  
        
    }
    
    public static ConexionBD getInstance() throws SQLException {
        if (instance == null || instance.getConexionBD().isClosed()) {
            instance = new ConexionBD();
        }
        
        return instance;
    }
    
    public Connection getConexionBD() {
        return conexionBD;
    }
}
