
package logica.interfaces;

import accesoadatos.dto.CoordinadorDTO;

import java.io.IOException;
import java.sql.SQLException;


public interface InterfazCoordinadorDAO {

    boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    
    boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
    
    boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    
    CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
    
}