package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.CoordinadorDTO;
import java.io.IOException;
import java.sql.SQLException;

public interface ICoordinadorDAO {

    public boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    public boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
    public boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException, IOException;
    public CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException, IOException;
}