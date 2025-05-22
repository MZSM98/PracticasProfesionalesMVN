package logica.dao;

import logica.interfaces.InterfazEstudianteDAO;
import accesoadatos.dto.EstudianteDTO;
import accesoadatos.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO implements InterfazEstudianteDAO {

    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO estudiante (matricula, nombreEstudiante, periodoEscolar,seccionEstudiante, avanceCrediticio, promedio) VALUES (?, ?, ?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, estudiante.getMatricula());
            declaracionPreparada.setString(2, estudiante.getNombreEstudiante());            
            declaracionPreparada.setString(3, estudiante.getPeriodoEscolar());
            declaracionPreparada.setString(4, estudiante.getPeriodoEscolar());
            declaracionPreparada.setInt(5, estudiante.getAvanceCrediticio());           
            declaracionPreparada.setDouble(6, estudiante.getPromedio());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarEstudiante(String matricula) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM estudiante WHERE matricula = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, matricula);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException {
        String actualizarSQL = "UPDATE estudiante SET nombreEstudiante = ?, periodoEscolar = ?, promedio = ?, seccionEstudiante = ?, avanceCrediticio = ?, promedio = ? WHERE matricula = ?";
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, estudiante.getMatricula());
            declaracionPreparada.setString(2, estudiante.getNombreEstudiante());            
            declaracionPreparada.setString(3, estudiante.getPeriodoEscolar());
            declaracionPreparada.setString(4, estudiante.getPeriodoEscolar());
            declaracionPreparada.setInt(5, estudiante.getAvanceCrediticio());           
            declaracionPreparada.setDouble(6, estudiante.getPromedio());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public EstudianteDTO buscarEstudiante(String matricula) throws SQLException, IOException {
        String consultaSQL = "SELECT matricula, nombreEstudiante, periodoEscolar,seccionEstudiante, avanceCrediticio, promedio  FROM estudiante WHERE matricula = ?";
        EstudianteDTO estudiante = null;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, matricula);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoDeOperacion.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoDeOperacion.getString("nombreEstudiante"));
                estudiante.setSeccionEstudiante(resultadoDeOperacion.getString("seccionEstudiante"));                
                estudiante.setPeriodoEscolar(resultadoDeOperacion.getString("periodoEscolar"));
                estudiante.setAvanceCrediticio(resultadoDeOperacion.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoDeOperacion.getDouble("promedio"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return estudiante;
    }
    
    @Override
    public List<EstudianteDTO> listarEstudiantes() throws SQLException, IOException {
        final String CONSULTA_SQL = "SELECT * FROM estudiante";
        List<EstudianteDTO> listaEstudiantes = new ArrayList<>();

        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultadoEstudiantes = null;

        try {
            conexion = new ConexionBD().getConexionBD();
            preparedStatement = conexion.prepareStatement(CONSULTA_SQL);
            resultadoEstudiantes = preparedStatement.executeQuery();

            while(resultadoEstudiantes.next()) {
                EstudianteDTO estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoEstudiantes.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoEstudiantes.getString("nombreEstudiante"));
                estudiante.setPeriodoEscolar(resultadoEstudiantes.getString("periodoEscolar"));
                estudiante.setSeccionEstudiante(resultadoEstudiantes.getString("seccionEstudiante"));
                estudiante.setAvanceCrediticio(resultadoEstudiantes.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoEstudiantes.getDouble("promedio"));

                listaEstudiantes.add(estudiante);
            }
        } finally {
            if (resultadoEstudiantes != null) resultadoEstudiantes.close();
            if (preparedStatement != null) preparedStatement.close();
            if (conexion != null) conexion.close();
        }

        return listaEstudiantes;
    }
}
