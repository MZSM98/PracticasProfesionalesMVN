package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.ContrasenaUtil;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.dao.interfaz.ISeccionDAO;
import com.pdc.dao.interfaz.IEstudianteDAO;
import com.pdc.dao.interfaz.IUsuarioDAO;
import com.pdc.dao.interfaz.ITipoUsuarioDAO;

public class EstudianteDAOImpl implements IEstudianteDAO {

    private static final Integer ESTUDIANTE = 4;
    private final ISeccionDAO interfazSeccionDAO;
    private final IPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    private final IUsuarioDAO interfazUsuarioDAO;
    private final ITipoUsuarioDAO interfazTipoUsuarioDAO;

    public EstudianteDAOImpl() {
        interfazSeccionDAO = new SeccionDAOImpl();
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAOImpl();
        interfazUsuarioDAO = new UsuarioDAOImpl();
        interfazTipoUsuarioDAO = new TipoUsuarioDAOImpl();
    }

    @Override
    public boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException {
        
        String insertarSQL = "INSERT INTO estudiante (matricula, nombreEstudiante, periodoEscolar,seccionEstudiante, avanceCrediticio, promedio) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            conexion.setAutoCommit(false);

            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(estudiante.getMatricula());
            usuario.setTipoUsuario(interfazTipoUsuarioDAO.buscarTipoUsuario(ESTUDIANTE));
            usuario.setContrasena(ContrasenaUtil.crearContrasenaPorDefecto(estudiante));
            interfazUsuarioDAO.insertarUsuario(usuario);

            declaracionPreparada.setString(1, estudiante.getMatricula());
            declaracionPreparada.setString(2, estudiante.getNombreEstudiante());
            declaracionPreparada.setInt(3, estudiante.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setInt(4, estudiante.getSeccionEstudiante().getIdSeccion());
            declaracionPreparada.setInt(5, estudiante.getAvanceCrediticio());
            declaracionPreparada.setDouble(6, estudiante.getPromedio());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            if (filasAfectadas > 0) {
                conexion.commit();
                return true;
            } else {
                conexion.rollback();
                return false;
            }
        }
    }

    @Override
    public boolean eliminarEstudiante(String matricula) throws SQLException {
        
        String eliminarSQL = "DELETE FROM estudiante WHERE matricula = ?";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(eliminarSQL)) {

            declaracionPreparada.setString(1, matricula);

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarEstudiante(EstudianteDTO estudiante) throws SQLException {
        
        String actualizarSQL = "UPDATE estudiante SET nombreEstudiante = ?, periodoEscolar = ?, promedio = ?, seccionEstudiante = ?, avanceCrediticio = ? WHERE matricula = ?";

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, estudiante.getNombreEstudiante());
            declaracionPreparada.setInt(2, estudiante.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setDouble(3, estudiante.getPromedio());
            declaracionPreparada.setInt(4, estudiante.getSeccionEstudiante().getIdSeccion());
            declaracionPreparada.setInt(5, estudiante.getAvanceCrediticio());
            declaracionPreparada.setString(6, estudiante.getMatricula());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public EstudianteDTO buscarEstudiante(String matricula) throws SQLException {
        
        String consultaSQL = "SELECT matricula, nombreEstudiante, periodoEscolar,seccionEstudiante, avanceCrediticio, promedio  FROM estudiante WHERE matricula = ?";
        EstudianteDTO estudiante = null;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, matricula);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoDeOperacion.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoDeOperacion.getString("nombreEstudiante"));
                int idSeccion = resultadoDeOperacion.getInt("seccionEstudiante");
                estudiante.setSeccionEstudiante(interfazSeccionDAO.buscarSeccion(idSeccion));
                int idPeriodoEscolar = resultadoDeOperacion.getInt("periodoEscolar");
                estudiante.setPeriodoEscolar(interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar));
                estudiante.setAvanceCrediticio(resultadoDeOperacion.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoDeOperacion.getDouble("promedio"));
            }
        }

        return estudiante;
    }

    @Override
    public List<EstudianteDTO> listarEstudiantes() throws SQLException {
        
        final String CONSULTA_SQL = "SELECT * FROM estudiante";
        List<EstudianteDTO> listaEstudiantes = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(CONSULTA_SQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                EstudianteDTO estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoDeOperacion.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoDeOperacion.getString("nombreEstudiante"));
                int idSeccion = resultadoDeOperacion.getInt("seccionEstudiante");
                estudiante.setSeccionEstudiante(interfazSeccionDAO.buscarSeccion(idSeccion));
                int idPeriodoEscolar = resultadoDeOperacion.getInt("periodoEscolar");
                estudiante.setPeriodoEscolar(interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar));
                estudiante.setAvanceCrediticio(resultadoDeOperacion.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoDeOperacion.getDouble("promedio"));

                listaEstudiantes.add(estudiante);
            }
        }

        return listaEstudiantes;
    }

    @Override
    public List<EstudianteDTO> listarEstudiantesSinProyectoAsignado() throws SQLException {
        
        String consultaSQL = "SELECT e.matricula, e.nombreEstudiante, e.periodoEscolar, e.seccionEstudiante, e.avanceCrediticio, e.promedio "
                + "FROM estudiante e "
                + "LEFT JOIN proyectoasignado pa ON e.matricula = pa.matriculaestudiante "
                + "WHERE pa.matriculaestudiante IS NULL";

        List<EstudianteDTO> listaEstudiantesSinProyecto = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                EstudianteDTO estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoDeOperacion.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoDeOperacion.getString("nombreEstudiante"));

                int idSeccion = resultadoDeOperacion.getInt("seccionEstudiante");
                estudiante.setSeccionEstudiante(interfazSeccionDAO.buscarSeccion(idSeccion));

                int idPeriodoEscolar = resultadoDeOperacion.getInt("periodoEscolar");
                estudiante.setPeriodoEscolar(interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar));

                estudiante.setAvanceCrediticio(resultadoDeOperacion.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoDeOperacion.getDouble("promedio"));

                listaEstudiantesSinProyecto.add(estudiante);
            }
        }

        return listaEstudiantesSinProyecto;
    }
    
    @Override
    public int contarEstudiantes() throws SQLException {
        
        final String contarSQL = "SELECT COUNT(*) FROM estudiante";
        int totalEstudiantes = 0;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                totalEstudiantes = resultado.getInt(1);
            }
        }
    
        return totalEstudiantes;
    }
    
    @Override
    public List<EstudianteDTO> listarEstudiantesAsignadosPorProfesor(String numeroDeTrabajador) throws SQLException {
        
        String consultaSQL = "SELECT e.matricula, e.nombreEstudiante, e.periodoEscolar, e.seccionEstudiante, e.avanceCrediticio, e.promedio "
                + "FROM estudiante e "
                + "INNER JOIN estudianteexperienciaeducativa eee ON e.matricula = eee.matricula "
                + "INNER JOIN experienciaeducativa ee ON eee.nrc = ee.nrc "
                + "WHERE ee.numeroDeTrabajador = ?";

        List<EstudianteDTO> listaEstudiantesAsignados = new ArrayList<>();

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, numeroDeTrabajador);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                EstudianteDTO estudiante = new EstudianteDTO();
                estudiante.setMatricula(resultadoDeOperacion.getString("matricula"));
                estudiante.setNombreEstudiante(resultadoDeOperacion.getString("nombreEstudiante"));

                int idSeccion = resultadoDeOperacion.getInt("seccionEstudiante");
                estudiante.setSeccionEstudiante(interfazSeccionDAO.buscarSeccion(idSeccion));

                int idPeriodoEscolar = resultadoDeOperacion.getInt("periodoEscolar");
                estudiante.setPeriodoEscolar(interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar));

                estudiante.setAvanceCrediticio(resultadoDeOperacion.getInt("avanceCrediticio"));
                estudiante.setPromedio(resultadoDeOperacion.getDouble("promedio"));

                listaEstudiantesAsignados.add(estudiante);
            }
        }
        return listaEstudiantesAsignados;
    }
}