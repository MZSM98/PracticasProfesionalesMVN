package logica.dao;

import logica.interfaces.InterfazEstudianteDAO;
import accesoadatos.dto.EstudianteDTO;
import accesoadatos.ConexionBD;
import accesoadatos.dto.UsuarioDTO;
import grafica.utils.ContrasenaUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import logica.interfaces.InterfazPeriodoEscolarDAO;
import logica.interfaces.InterfazSeccionDAO;
import logica.interfaces.InterfazTipoUsuarioDAO;
import logica.interfaces.InterfazUsuarioDAO;

public class EstudianteDAO implements InterfazEstudianteDAO {

    private static final Integer ESTUDIANTE = 4;
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private InterfazSeccionDAO interfazSeccionDAO;
    private InterfazPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    private InterfazUsuarioDAO interfazUsuarioDAO;
    private InterfazTipoUsuarioDAO interfazTipoUsuarioDAO;
    
    public EstudianteDAO(){
        interfazSeccionDAO = new SeccionDAO();
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAO();
        interfazUsuarioDAO = new UsuarioDAO();
        interfazTipoUsuarioDAO = new TipoUsuarioDAO();
    }

    @Override
    public boolean insertarEstudiante(EstudianteDTO estudiante) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO estudiante (matricula, nombreEstudiante, periodoEscolar,seccionEstudiante, avanceCrediticio, promedio) VALUES (?, ?, ?, ?, ?, ?)";
        boolean insercionExitosa = false;
        
        try {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(estudiante.getMatricula());
            usuario.setTipoUsuario(interfazTipoUsuarioDAO.buscarTipoUsuario(ESTUDIANTE));
            usuario.setContrasena(ContrasenaUtil.creaContrasenaPorDefecto(estudiante));
            interfazUsuarioDAO.insertarUsuario(usuario);
            
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, estudiante.getMatricula());
            declaracionPreparada.setString(2, estudiante.getNombreEstudiante());            
            declaracionPreparada.setInt(3, estudiante.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setInt(4, estudiante.getSeccionEstudiante().getIdSeccion());
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
        String actualizarSQL = "UPDATE estudiante SET nombreEstudiante = ?, periodoEscolar = ?, promedio = ?, seccionEstudiante = ?, avanceCrediticio = ? WHERE matricula = ?";
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, estudiante.getNombreEstudiante());            
            declaracionPreparada.setInt(2, estudiante.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setDouble(3, estudiante.getPromedio());
            declaracionPreparada.setInt(4, estudiante.getSeccionEstudiante().getIdSeccion());
            declaracionPreparada.setInt(5, estudiante.getAvanceCrediticio());  
            declaracionPreparada.setString(6, estudiante.getMatricula());
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
                int idSeccion = resultadoDeOperacion.getInt("seccionEstudiante");
                estudiante.setSeccionEstudiante(interfazSeccionDAO.buscarSeccion(idSeccion)); 
                int idPeriodoEscolar = resultadoDeOperacion.getInt("periodoEscolar");
                estudiante.setPeriodoEscolar(interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar));
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

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(CONSULTA_SQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while(resultadoDeOperacion.next()) {
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
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }

        return listaEstudiantes;
    }
}
