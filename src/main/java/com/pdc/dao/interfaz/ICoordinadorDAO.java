
package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.CoordinadorDTO;

import java.io.IOException;
import java.sql.SQLException;


public interface ICoordinadorDAO {

    boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    
    boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
    
    boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    
    CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
    
}