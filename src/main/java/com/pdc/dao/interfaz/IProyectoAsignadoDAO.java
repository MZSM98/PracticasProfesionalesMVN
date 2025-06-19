package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IProyectoAsignadoDAO {
    
    public boolean insertarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException, IOException;
    public boolean editarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException, IOException;
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorID(int proyectoAsignadoID) throws SQLException, IOException;
    public List<ProyectoAsignadoDTO> listaProyectoAsignado() throws SQLException, IOException;    
    public List<ProyectoAsignadoDTO> listaProyectoAsignadoPorProyectoID(int proyectoID) throws SQLException, IOException;    
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorMatricula(String matriculaEstudiante) throws SQLException, IOException;
}
