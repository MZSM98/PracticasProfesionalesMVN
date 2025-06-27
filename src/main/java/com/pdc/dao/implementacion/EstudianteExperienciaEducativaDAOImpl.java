package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.EstudianteExperienciaEducativaDTO;
import com.pdc.modelo.dto.ExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IEstudianteExperienciaEducativaDAO;
import com.pdc.dao.interfaz.IExperienciaEducativaDAO;

public class EstudianteExperienciaEducativaDAOImpl implements IEstudianteExperienciaEducativaDAO {
    
    private static final String ID_EXPERIENCIA_ASIGNADA = "idexperienciaasignada";
    private static final String NRC = "nrc";
    private static final String MATRICULA = "matricula";
    
    private final IEstudianteDAO interfazEstudianteDAO; 
    private final IExperienciaEducativaDAO interfazExperienciaEducativaDAO;

    public EstudianteExperienciaEducativaDAOImpl() {
        
        interfazExperienciaEducativaDAO = new ExperienciaEducativaDAOImpl();
        interfazEstudianteDAO = new EstudianteDAOImpl();
    }

    @Override
    public boolean insertarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException {
        
        String insertarSQL = "INSERT INTO estudianteexperienciaeducativa (nrc, matricula) VALUES (?, ?)";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, estudianteAsignado.getExperienciaEducativa().getNrc());
            declaracionPreparada.setString(2, estudianteAsignado.getEstudiante().getMatricula());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarExperienciaAsignada(EstudianteExperienciaEducativaDTO estudianteAsignado) throws SQLException {
        
        String actualizarSQL = "UPDATE estudianteexperienciaeducativa SET nrc = ?, matricula = ? WHERE idexperienciaasignada = ?";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, estudianteAsignado.getExperienciaEducativa().getNrc());
            declaracionPreparada.setString(2, estudianteAsignado.getEstudiante().getMatricula());
            declaracionPreparada.setInt(3, estudianteAsignado.getIdExperienciaAsignada());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorID(Integer idExperienciaAsignada) throws SQLException {
        
        String consultaSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa WHERE idexperienciaasignada = ?";
        EstudianteExperienciaEducativaDTO estudianteAsignado = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idExperienciaAsignada);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                estudianteAsignado = new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));

                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                estudianteAsignado.setExperienciaEducativa(experienciaEducativa);

                String matricula = resultadoDeOperacion.getString(MATRICULA);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteAsignado.setEstudiante(estudiante);
            }
        }

        return estudianteAsignado;
    }

    @Override
    public List<EstudianteExperienciaEducativaDTO> listarExperienciaAsignada() throws SQLException {
        
        String consultaTodoSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa";
        List<EstudianteExperienciaEducativaDTO> listaEstudiantesAsignados = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaTodoSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                EstudianteExperienciaEducativaDTO estudianteAsignado = new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));

                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                estudianteAsignado.setExperienciaEducativa(experienciaEducativa);

                String matricula = resultadoDeOperacion.getString(MATRICULA);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteAsignado.setEstudiante(estudiante);

                listaEstudiantesAsignados.add(estudianteAsignado);
            }
        }

        return listaEstudiantesAsignados;
    }

    @Override
    public EstudianteExperienciaEducativaDTO obtenerExperienciaAsignadaPorEstudiante(String matriculaEstudiante) throws SQLException {
        
        String consultaSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa WHERE matricula = ?";
        EstudianteExperienciaEducativaDTO experienciaAsignada = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, matriculaEstudiante);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                experienciaAsignada = new EstudianteExperienciaEducativaDTO();
                experienciaAsignada.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));

                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                experienciaAsignada.setExperienciaEducativa(experienciaEducativa);

                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matriculaEstudiante);
                experienciaAsignada.setEstudiante(estudiante);
            }
        }

        return experienciaAsignada;
    }

    @Override
    public List<EstudianteExperienciaEducativaDTO> listaExperienciaAsignadaPorNRC(String nrcExperiencia) throws SQLException {
        
        String consultaSQL = "SELECT idexperienciaasignada, nrc, matricula FROM estudianteexperienciaeducativa WHERE nrc = ?";
        List<EstudianteExperienciaEducativaDTO> listaEstudiantesAsignados = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, nrcExperiencia);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                EstudianteExperienciaEducativaDTO estudianteAsignado = new EstudianteExperienciaEducativaDTO();
                estudianteAsignado.setIdExperienciaAsignada(resultadoDeOperacion.getInt(ID_EXPERIENCIA_ASIGNADA));

                String nrc = resultadoDeOperacion.getString(NRC);
                ExperienciaEducativaDTO experienciaEducativa = interfazExperienciaEducativaDAO.obtenerExperienciaEducativaPorNRC(nrc);
                estudianteAsignado.setExperienciaEducativa(experienciaEducativa);

                String matricula = resultadoDeOperacion.getString(MATRICULA);
                EstudianteDTO estudiante = interfazEstudianteDAO.buscarEstudiante(matricula);
                estudianteAsignado.setEstudiante(estudiante);

                listaEstudiantesAsignados.add(estudianteAsignado);
            }
        }

        return listaEstudiantesAsignados;
    }
}
