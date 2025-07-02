package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IEstudianteEvaluacionDAO;
import com.pdc.modelo.dto.EstudianteEvaluacionDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteEvaluacionDAOImpl implements IEstudianteEvaluacionDAO {

    private static final String COLUMNA_IDVALUACION = "idvaluacion";
    private static final String COLUMNA_MATRICULA = "matricula";
    private static final String COLUMNA_NUMERO_TRABAJADOR = "numeroDeTrabajador";
    private static final String COLUMNA_PROYECTO_ID = "proyectoID";
    private static final String COLUMNA_CRITERIO_UNO = "criteriouno";
    private static final String COLUMNA_CRITERIO_DOS = "criteriodos";
    private static final String COLUMNA_CRITERIO_TRES = "criteriotres";
    private static final String COLUMNA_CRITERIO_CUATRO = "criteriocuatro";

    @Override
    public boolean insertarEstudianteEvaluacion(EstudianteEvaluacionDTO estudianteEvaluacion) throws SQLException {
        
        String insertarSQL = "INSERT INTO estudianteevaluacion (matricula, numeroDeTrabajador, proyectoID, "
                + "criteriouno, criteriodos, criteriotres, criteriocuatro) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, estudianteEvaluacion.getMatricula());
            declaracionPreparada.setString(2, estudianteEvaluacion.getNumeroDeTrabajador());
            declaracionPreparada.setInt(3, estudianteEvaluacion.getProyectoID());
            declaracionPreparada.setDouble(4, estudianteEvaluacion.getCriterioUno());
            declaracionPreparada.setDouble(5, estudianteEvaluacion.getCriterioDos());
            declaracionPreparada.setDouble(6, estudianteEvaluacion.getCriterioTres());
            declaracionPreparada.setDouble(7, estudianteEvaluacion.getCriterioCuatro());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarEstudianteEvaluacion(EstudianteEvaluacionDTO estudianteEvaluacion) throws SQLException {
        
        String actualizarSQL = "UPDATE estudianteevaluacion SET matricula = ?, numeroDeTrabajador = ?, "
                + "proyectoID = ?, criteriouno = ?, criteriodos = ?, criteriotres = ?, criteriocuatro = ? "
                + "WHERE idvaluacion = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, estudianteEvaluacion.getMatricula());
            declaracionPreparada.setString(2, estudianteEvaluacion.getNumeroDeTrabajador());
            declaracionPreparada.setInt(3, estudianteEvaluacion.getProyectoID());
            declaracionPreparada.setDouble(4, estudianteEvaluacion.getCriterioUno());
            declaracionPreparada.setDouble(5, estudianteEvaluacion.getCriterioDos());
            declaracionPreparada.setDouble(6, estudianteEvaluacion.getCriterioTres());
            declaracionPreparada.setDouble(7, estudianteEvaluacion.getCriterioCuatro());
            declaracionPreparada.setInt(8, estudianteEvaluacion.getIdvaluacion());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public List<EstudianteEvaluacionDTO> listarEstudianteEvaluaciones() throws SQLException {
        
        String consultaSQL = "SELECT idvaluacion, matricula, numeroDeTrabajador, proyectoID, "
                + "criteriouno, criteriodos, criteriotres, criteriocuatro FROM estudianteevaluacion";
        List<EstudianteEvaluacionDTO> listaEvaluaciones = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                EstudianteEvaluacionDTO evaluacion = new EstudianteEvaluacionDTO();
                evaluacion.setIdvaluacion(resultadoDeOperacion.getInt(COLUMNA_IDVALUACION));
                evaluacion.setMatricula(resultadoDeOperacion.getString(COLUMNA_MATRICULA));
                evaluacion.setNumeroDeTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_TRABAJADOR));
                evaluacion.setProyectoID(resultadoDeOperacion.getInt(COLUMNA_PROYECTO_ID));
                evaluacion.setCriterioUno(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_UNO));
                evaluacion.setCriterioDos(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_DOS));
                evaluacion.setCriterioTres(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_TRES));
                evaluacion.setCriterioCuatro(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_CUATRO));

                listaEvaluaciones.add(evaluacion);
            }
        }

        return listaEvaluaciones;
    }

    @Override
    public EstudianteEvaluacionDTO obtenerEstudianteEvaluacionPorId(int idvaluacion) throws SQLException {
        
        String consultaSQL = "SELECT idvaluacion, matricula, numeroDeTrabajador, proyectoID, "
                + "criteriouno, criteriodos, criteriotres, criteriocuatro FROM estudianteevaluacion "
                + "WHERE idvaluacion = ?";
        EstudianteEvaluacionDTO evaluacion = null;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idvaluacion);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                evaluacion = new EstudianteEvaluacionDTO();
                evaluacion.setIdvaluacion(resultadoDeOperacion.getInt(COLUMNA_IDVALUACION));
                evaluacion.setMatricula(resultadoDeOperacion.getString(COLUMNA_MATRICULA));
                evaluacion.setNumeroDeTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_TRABAJADOR));
                evaluacion.setProyectoID(resultadoDeOperacion.getInt(COLUMNA_PROYECTO_ID));
                evaluacion.setCriterioUno(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_UNO));
                evaluacion.setCriterioDos(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_DOS));
                evaluacion.setCriterioTres(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_TRES));
                evaluacion.setCriterioCuatro(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_CUATRO));
            }
        }

        return evaluacion;
    }

    @Override
    public int obtenerSiguienteId() throws SQLException {
        
        String consultaSQL = "SELECT COALESCE("
                + "(SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES "
                + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'estudianteevaluacion'), "
                + "COALESCE((SELECT MAX(idvaluacion) + 1 FROM estudianteevaluacion), 1)"
                + ") AS siguiente_id";
        int siguienteId = 1;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                siguienteId = resultadoDeOperacion.getInt("siguiente_id");
            }
        }

        return siguienteId;
    }

    @Override
    public EstudianteEvaluacionDTO obtenerEstudianteEvaluacionPorMatricula(String matricula) throws SQLException {
        
        String consultaSQL = "SELECT idvaluacion, matricula, numeroDeTrabajador, proyectoID, "
                + "criteriouno, criteriodos, criteriotres, criteriocuatro FROM estudianteevaluacion "
                + "WHERE matricula = ?";
        EstudianteEvaluacionDTO evaluacion = null;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, matricula);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                evaluacion = new EstudianteEvaluacionDTO();
                evaluacion.setIdvaluacion(resultadoDeOperacion.getInt(COLUMNA_IDVALUACION));
                evaluacion.setMatricula(resultadoDeOperacion.getString(COLUMNA_MATRICULA));
                evaluacion.setNumeroDeTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_TRABAJADOR));
                evaluacion.setProyectoID(resultadoDeOperacion.getInt(COLUMNA_PROYECTO_ID));
                evaluacion.setCriterioUno(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_UNO));
                evaluacion.setCriterioDos(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_DOS));
                evaluacion.setCriterioTres(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_TRES));
                evaluacion.setCriterioCuatro(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_CUATRO));
            }
        }

        return evaluacion;
    }

    @Override
    public List<EstudianteEvaluacionDTO> listarEvaluacionesPorProfesor(String numeroDeTrabajador) throws SQLException {
        
        String consultaSQL = "SELECT ee.idvaluacion, ee.matricula, ee.numeroDeTrabajador, ee.proyectoID, "
                + "ee.criteriouno, ee.criteriodos, ee.criteriotres, ee.criteriocuatro "
                + "FROM estudianteevaluacion ee "
                + "INNER JOIN estudiante e ON ee.matricula = e.matricula "
                + "INNER JOIN estudianteexperienciaeducativa eee ON e.matricula = eee.matricula "
                + "INNER JOIN experienciaeducativa ex ON eee.nrc = ex.nrc "
                + "WHERE ex.numeroDeTrabajador = ?";
        List<EstudianteEvaluacionDTO> listaEvaluaciones = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, numeroDeTrabajador);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                EstudianteEvaluacionDTO evaluacion = new EstudianteEvaluacionDTO();
                evaluacion.setIdvaluacion(resultadoDeOperacion.getInt(COLUMNA_IDVALUACION));
                evaluacion.setMatricula(resultadoDeOperacion.getString(COLUMNA_MATRICULA));
                evaluacion.setNumeroDeTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_TRABAJADOR));
                evaluacion.setProyectoID(resultadoDeOperacion.getInt(COLUMNA_PROYECTO_ID));
                evaluacion.setCriterioUno(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_UNO));
                evaluacion.setCriterioDos(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_DOS));
                evaluacion.setCriterioTres(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_TRES));
                evaluacion.setCriterioCuatro(resultadoDeOperacion.getInt(COLUMNA_CRITERIO_CUATRO));

                listaEvaluaciones.add(evaluacion);
            }
        }

        return listaEvaluaciones;
    }
}
