package utileriadbtest;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.utileria.bd.ConfiguracionBD;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ConexionBDTest {

    private ConexionBD conexionBD;
    private Properties mockProperties;

    @Before
    public void setUp() {
        conexionBD = ConexionBD.getInstancia();
        
        mockProperties = new Properties();
        mockProperties.setProperty("db.url", "jdbc:mysql://localhost:3306/testdb");
        mockProperties.setProperty("db.user", "testuser");
        mockProperties.setProperty("db.password", "testpass");
    }

    @Test(expected = SQLInvalidAuthorizationSpecException.class)
    public void testCredencialesIncorrectas_UnitTest() throws SQLException {
        SQLException originalException = new SQLException("Access denied for user", "28000", 1045);
        
        try (MockedStatic<ConfiguracionBD> configMock = Mockito.mockStatic(ConfiguracionBD.class);
             MockedStatic<DriverManager> driverMock = Mockito.mockStatic(DriverManager.class)) {
            
            configMock.when(ConfiguracionBD::loadProperties).thenReturn(mockProperties);
            driverMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                     .thenThrow(originalException);
            
            conexionBD.conectar();
        }
    }

    @Test(expected = SQLNonTransientConnectionException.class)
    public void testServidorNoDisponible_UnitTest() throws SQLException {
        SQLException originalException = new SQLException("Communications link failure", "08001", 0);
        
        try (MockedStatic<ConfiguracionBD> configMock = Mockito.mockStatic(ConfiguracionBD.class);
             MockedStatic<DriverManager> driverMock = Mockito.mockStatic(DriverManager.class)) {
            
            configMock.when(ConfiguracionBD::loadProperties).thenReturn(mockProperties);
            driverMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                     .thenThrow(originalException);
            
            conexionBD.conectar();
        }
    }
}