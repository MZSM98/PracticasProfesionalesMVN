package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProyectoDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IProyectoDAO {
    
    boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException, IOException;
    boolean eliminarProyecto(int proyectoID) throws SQLException, IOException;
    boolean editarProyecto(ProyectoDTO proyecto) throws SQLException, IOException;
    List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException, IOException;
    List<ProyectoDTO> listarProyectos() throws SQLException, IOException;
    ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException, IOException;
    
}