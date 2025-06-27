package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.CoordinadorDTO;
import java.sql.SQLException;

public interface ICoordinadorDAO {

    public boolean insertarCoordinador(CoordinadorDTO coordinador) throws SQLException;
    public boolean eliminarCoordinador(String numeroDeTrabajador) throws SQLException;
    public boolean editarCoordinador(CoordinadorDTO coordinador) throws SQLException;
    public CoordinadorDTO buscarCoordinador(String numeroDeTrabajador) throws SQLException;
}