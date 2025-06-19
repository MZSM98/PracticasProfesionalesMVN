package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IProyectoAsignadoDAO;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.modelo.dto.ProyectoAsignadoDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProyectoAsignadoDAOImpl implements IProyectoAsignadoDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IProyectoDAO interfazProyectoDAO;
    private final IEstudianteDAO interfazEstudianteDAO;

    public static final String ID_PROYECTO_ASIGNADO = "idproyectoasignado";
    public static final String ID_PROYECTO = "idproyecto";
    public static final String MATRICULA_ESTUDIANTE = "matriculaestudiante";

    public ProyectoAsignadoDAOImpl() {
        interfazProyectoDAO = new ProyectoDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
    }

    @Override
    public boolean insertarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException, IOException {

        String insertarSQL = "INSERT INTO proyectoasignado (idproyecto, matriculaestudiante) VALUES (?, ?)";
        boolean insercionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setInt(1, proyectoAsignado.getProyecto().getProyectoID());
            declaracionPreparada.setString(2, proyectoAsignado.getEstudiante().getMatricula());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return insercionExitosa;
    }

    @Override
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorID(int proyectoAsignadoID) throws SQLException, IOException {

        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE idproyectoasignado = ?";
        ProyectoAsignadoDTO proyectoAsignado = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, proyectoAsignadoID);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

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
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyectoAsignado;
    }

    @Override
    public List<ProyectoAsignadoDTO> listaProyectoAsignado() throws SQLException, IOException {

        String consultaTodoSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado";
        List<ProyectoAsignadoDTO> listaProyectosAsignados = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

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
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return listaProyectosAsignados;
    }

    @Override
    public boolean editarProyectoAsignado(ProyectoAsignadoDTO proyectoAsignado) throws SQLException, IOException {

        String actualizarSQL = "UPDATE proyectoasignado SET idproyecto = ?, matriculaestudiante = ? WHERE idproyectoasignado = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setInt(1, proyectoAsignado.getProyecto().getProyectoID());
            declaracionPreparada.setString(2, proyectoAsignado.getEstudiante().getMatricula());
            declaracionPreparada.setInt(3, proyectoAsignado.getIdProyectoAsignado());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return actualizacionExitosa;
    }

    @Override
    public List<ProyectoAsignadoDTO> listaProyectoAsignadoPorProyectoID(int proyectoID) throws SQLException, IOException {

        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE idproyecto = ?";
        List<ProyectoAsignadoDTO> listaProyectosAsignados = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, proyectoID);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

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
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return listaProyectosAsignados;
    }

    @Override
    public ProyectoAsignadoDTO obtenerProyectoAsignadoPorMatricula(String matriculaEstudiante) throws SQLException, IOException {

        String consultaSQL = "SELECT idproyectoasignado, idproyecto, matriculaestudiante FROM proyectoasignado WHERE matriculaestudiante = ?";
        ProyectoAsignadoDTO proyectoAsignado = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, matriculaEstudiante);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

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
        } finally {
            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyectoAsignado;
    }
}
