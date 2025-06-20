package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProyectoDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IProyectoDAO {
    
    public boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException, IOException;
    public boolean eliminarProyecto(int proyectoID) throws SQLException, IOException;
    public boolean editarProyecto(ProyectoDTO proyecto) throws SQLException, IOException;
    public List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException, IOException;
    public List<ProyectoDTO> listarProyectos() throws SQLException, IOException;
    public ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException, IOException;
    public List<ProyectoDTO> listarProyectosPorOv(String rfcMoral) throws SQLException, IOException;
    public List<ProyectoDTO> listarProyectosConVacantesDisponibles() throws SQLException, IOException;
    
}