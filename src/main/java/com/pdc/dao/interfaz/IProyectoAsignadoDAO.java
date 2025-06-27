package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import java.sql.SQLException;
import java.util.List;

public interface IProyectoAsignadoDAO {
    
    public boolean insertarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException;
    public boolean editarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException;
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorID(int proyectoAsignadoID) throws SQLException;
    public List<ProyectoAsignadoDTO> listaProyectoAsignado() throws SQLException;    
    public List<ProyectoAsignadoDTO> listaProyectoAsignadoPorProyectoID(int proyectoID) throws SQLException;    
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorMatricula(String matriculaEstudiante) throws SQLException;
    public int contarProyectosAsignados ()throws SQLException;
}
