package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProyectoAsignadoDAOImpl implements IProyectoAsignadoDAO {

    private static final String ID_PROYECTO_ASIGNADO = "idproyectoasignado";
    private static final String ID_PROYECTO = "idproyecto";
    private static final String MATRICULA_ESTUDIANTE = "matriculaestudiante";

    private final IProyectoDAO interfazProyectoDAO;
    private final IEstudianteDAO interfazEstudianteDAO;

    public ProyectoAsignadoDAOImpl() {
        
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
    }

    @Override
    public boolean insertarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException {
        
        String insertarSQL = "INSERT INTO proyectoasignado (idproyecto, matriculaestudiante) VALUES (?, ?)";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            declaracionPreparada.setInt(1, proyectoAsignado.getProyecto().getProyectoID());
            declaracionPreparada.setString(2, proyectoAsignado.getEstudiante().getMatricula());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorID(int proyectoAsignadoID) throws SQLException {
        
        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE idproyectoasignado = ?";
        ProyectoAsignadoDTO proyectoAsignado = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, proyectoAsignadoID);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                proyectoAsignado = new ProyectoAsignadoDTO();
                proyectoAsignado.setIdProyectoAsignado(resultadoDeOperacion.getInt(ID_PROYECTO_ASIGNADO));

                Integer idProyecto = resultadoDeOperacion.getInt(ID_PROYECTO);
                ProyectoDTO proyecto = interfazProyectoDAO.obtenerProyectoPorID(idProyecto);
                proyectoAsignado.setProyecto(proyecto);

                String matriculaEstudiante = resultadoDeOperacion.getString(MATRICULA_ESTUDIANTE);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matriculaEstudiante);
                proyectoAsignado.setEstudiante(estudiante);
            }
        }

        return proyectoAsignado;
    }

    @Override
    public List<ProyectoAsignadoDTO> listaProyectoAsignado() throws SQLException {
        
        String consultaTodoSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado";
        List<ProyectoAsignadoDTO> listaProyectosAsignados = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaTodoSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                ProyectoAsignadoDTO proyectoAsignado = new ProyectoAsignadoDTO();
                proyectoAsignado.setIdProyectoAsignado(resultadoDeOperacion.getInt(ID_PROYECTO_ASIGNADO));

                Integer idProyecto = resultadoDeOperacion.getInt(ID_PROYECTO);
                ProyectoDTO proyecto = interfazProyectoDAO.obtenerProyectoPorID(idProyecto);
                proyectoAsignado.setProyecto(proyecto);

                String matriculaEstudiante = resultadoDeOperacion.getString(MATRICULA_ESTUDIANTE);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matriculaEstudiante);
                proyectoAsignado.setEstudiante(estudiante);

                listaProyectosAsignados.add(proyectoAsignado);
            }
        }

        return listaProyectosAsignados;
    }

    @Override
    public boolean editarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException {
        
        String actualizarSQL = "UPDATE proyectoasignado SET idproyecto = ?, matriculaestudiante = ? WHERE idproyectoasignado = ?";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setInt(1, proyectoAsignado.getProyecto().getProyectoID());
            declaracionPreparada.setString(2, proyectoAsignado.getEstudiante().getMatricula());
            declaracionPreparada.setInt(3, proyectoAsignado.getIdProyectoAsignado());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public List<ProyectoAsignadoDTO> listaProyectoAsignadoPorProyectoID(int proyectoID) throws SQLException {
        
        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE idproyecto = ?";
        List<ProyectoAsignadoDTO> listaProyectosAsignados = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, proyectoID);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                ProyectoAsignadoDTO proyectoAsignado = new ProyectoAsignadoDTO();
                proyectoAsignado.setIdProyectoAsignado(resultadoDeOperacion.getInt(ID_PROYECTO_ASIGNADO));

                Integer idProyecto = resultadoDeOperacion.getInt(ID_PROYECTO);
                ProyectoDTO proyecto = interfazProyectoDAO.obtenerProyectoPorID(idProyecto);
                proyectoAsignado.setProyecto(proyecto);

                String matriculaEstudiante = resultadoDeOperacion.getString(MATRICULA_ESTUDIANTE);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matriculaEstudiante);
                proyectoAsignado.setEstudiante(estudiante);

                listaProyectosAsignados.add(proyectoAsignado);
            }
        }

        return listaProyectosAsignados;
    }

    @Override
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorMatricula(String matriculaEstudiante) throws SQLException {
        
        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE matriculaestudiante = ?";
        ProyectoAsignadoDTO proyectoAsignado = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, matriculaEstudiante);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                proyectoAsignado = new ProyectoAsignadoDTO();
                proyectoAsignado.setIdProyectoAsignado(resultadoDeOperacion.getInt(ID_PROYECTO_ASIGNADO));

                Integer idProyecto = resultadoDeOperacion.getInt(ID_PROYECTO);
                ProyectoDTO proyecto = interfazProyectoDAO.obtenerProyectoPorID(idProyecto);
                proyectoAsignado.setProyecto(proyecto);

                String matricula = resultadoDeOperacion.getString(MATRICULA_ESTUDIANTE);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                proyectoAsignado.setEstudiante(estudiante);
            }
        }

        return proyectoAsignado;
    }
    
    @Override
    public int contarProyectosAsignados() throws SQLException {
        
        String contarSQL = "SELECT COUNT(*) FROM proyectoasignado";
        int totalProyectosAsignados = 0;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                
                totalProyectosAsignados = resultado.getInt(1);
            }
        }
    
        return totalProyectosAsignados;
    }
}
