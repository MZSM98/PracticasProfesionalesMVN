package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IProyectoDAO;

public class ProyectoDAOImpl implements IProyectoDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException, IOException {
        
        String insertarSQL = "INSERT INTO proyecto (titulo, periodoEscolar, descripcion, rfcMoral) VALUES (?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, proyecto.getTituloProyecto());
            declaracionPreparada.setString(2, proyecto.getPeriodoEscolar());
            declaracionPreparada.setString(3, proyecto.getDescripcionProyecto());
            declaracionPreparada.setString(4, proyecto.getRfcMoral());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarProyecto(int proyectoID) throws SQLException, IOException {
        
        String eliminarSQL = "DELETE FROM proyecto WHERE proyectoID = ?";
        boolean eliminacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setInt(1, proyectoID);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarProyecto(ProyectoDTO proyecto) throws SQLException, IOException {
        
        String actualizarSQL = "UPDATE proyecto SET titulo = ?, periodoEscolar = ?, descripcion = ?, rfcMoral = ?, estadoProyecto = ? WHERE proyectoID = ?";
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, proyecto.getTituloProyecto());
            declaracionPreparada.setString(2, proyecto.getPeriodoEscolar());
            declaracionPreparada.setString(3, proyecto.getDescripcionProyecto());
            declaracionPreparada.setString(4, proyecto.getRfcMoral());
            declaracionPreparada.setString(5, proyecto.getEstadoProyecto());
            declaracionPreparada.setInt(6, proyecto.getProyectoID());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException, IOException {
        
        String consultaSQL = "SELECT proyectoID, titulo, periodoEscolar, descripcion, rfcMoral, estadoProyecto FROM proyecto WHERE titulo LIKE ?";
        List<ProyectoDTO> proyectosEncontrados = new ArrayList<>();

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, "%" + titulo + "%");
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt("proyectoID"));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString("titulo"));
                proyecto.setPeriodoEscolar(resultadoDeOperacion.getString("periodoEscolar"));
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString("descripcion"));
                proyecto.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString("estadoProyecto"));
                proyectosEncontrados.add(proyecto);
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return proyectosEncontrados;
    }
    
    @Override
    public List<ProyectoDTO> listarProyectos() throws SQLException, IOException {
        
        String consultaTodoSQL = "SELECT proyectoID, titulo, periodoEscolar, descripcion, rfcMoral, estadoProyecto FROM proyecto";
        List<ProyectoDTO> listaProyectos = new ArrayList<>();

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt("proyectoID"));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString("titulo"));
                proyecto.setPeriodoEscolar(resultadoDeOperacion.getString("periodoEscolar"));
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString("descripcion"));
                proyecto.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString("estadoProyecto"));
                listaProyectos.add(proyecto);
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return listaProyectos;
    }
    
    public ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException, IOException {
        
        String consultaSQL = "SELECT proyectoID, titulo, periodoEscolar, descripcion, rfcMoral, estadoProyecto FROM proyecto WHERE proyectoID = ?";
        ProyectoDTO proyecto = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, proyectoID);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt("proyectoID"));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString("titulo"));
                proyecto.setPeriodoEscolar(resultadoDeOperacion.getString("periodoEscolar"));
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString("descripcion"));
                proyecto.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString("estadoProyecto"));
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return proyecto;
    }
}