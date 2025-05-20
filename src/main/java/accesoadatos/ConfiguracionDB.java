
package accesoadatos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;


public class ConfiguracionDB {
    
    private static final String PROPERTIES_FILE = "resources/properties/db_config.properties";
    private static final Logger LOG = Logger.getLogger(ConfiguracionDB.class);
    
    public static Properties loadProperties() {
        
        Properties properties = new Properties();
        try (InputStream input = ConfiguracionDB.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                LOG.error("No se encontró el archivo db_config.properties: " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            LOG.error("Error al cargar db_config.properties: ", e);
        }
        
        return properties;
    }
}
