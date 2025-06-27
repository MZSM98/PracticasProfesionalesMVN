package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProyectoDTO;
import java.sql.SQLException;
import java.util.List;

public interface IProyectoDAO {
    
    public boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException;
    public boolean eliminarProyecto(int proyectoID) throws SQLException;
    public boolean editarProyecto(ProyectoDTO proyecto) throws SQLException;
    public List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException;
    public List<ProyectoDTO> listarProyectos() throws SQLException;
    public ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException;
    public List<ProyectoDTO> listarProyectosPorOv(String rfcMoral) throws SQLException;
    public List<ProyectoDTO> listarProyectosConVacantesDisponibles() throws SQLException;
    public int contarProyectos() throws SQLException;
}