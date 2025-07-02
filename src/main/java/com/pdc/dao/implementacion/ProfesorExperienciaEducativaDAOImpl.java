package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.UsuarioDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pdc.utileria.ContrasenaUtil;
import com.pdc.dao.interfaz.IUsuarioDAO;
import com.pdc.dao.interfaz.IProfesorExperienciaEducativaDAO;

public class ProfesorExperienciaEducativaDAOImpl implements IProfesorExperienciaEducativaDAO {
    
    private static final String COLUMNA_NUMERO_DE_TRABAJADOR = "numeroDeTrabajador";
    private static final String COLUMNA_NOMBRE_PROFESOR = "nombreProfesor";
    private static final String COLUMNA_SECCION = "seccion";
    
    private final IUsuarioDAO interfazUsuarioDAO;
    
    public ProfesorExperienciaEducativaDAOImpl() {
        
        interfazUsuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public boolean insertarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException {
        
        String insertarSQL = "INSERT INTO profesoree (numeroDeTrabajador, nombreProfesor, seccion) VALUES (?, ?, ?)";
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            conexion.setAutoCommit(false);

            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(profesor.getNumeroTrabajador());
            usuario.setTipoUsuario(profesor.getTipoUsuario());
            usuario.setContrasena(ContrasenaUtil.crearContrasenaPorDefecto(profesor));
            interfazUsuarioDAO.insertarUsuario(usuario);
            
            declaracionPreparada.setString(1, profesor.getNumeroTrabajador());
            declaracionPreparada.setString(2, profesor.getNombreProfesor());
            declaracionPreparada.setString(3, profesor.getSeccion());

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
    public boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException {
        
        String eliminarSQL = "DELETE FROM profesoree WHERE numeroDeTrabajador = ?";
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(eliminarSQL)) {

            declaracionPreparada.setString(1, numeroTrabajador);

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException {
        
        String actualizarSQL = "UPDATE profesoree SET nombreProfesor = ?, seccion = ? WHERE numeroDeTrabajador = ?";
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, profesor.getNombreProfesor());
            declaracionPreparada.setString(2, profesor.getSeccion());
            declaracionPreparada.setString(3, profesor.getNumeroTrabajador());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public ProfesorExperienciaEducativaDTO buscarProfesorEE(String numeroTrabajador) throws SQLException {
        
        String consultaSQL = "SELECT numeroDeTrabajador, nombreProfesor, seccion FROM profesoree WHERE numeroDeTrabajador = ?";
        ProfesorExperienciaEducativaDTO profesor = null;
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, numeroTrabajador);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                
                profesor = new ProfesorExperienciaEducativaDTO();
                profesor.setNumeroTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_DE_TRABAJADOR));
                profesor.setNombreProfesor(resultadoDeOperacion.getString(COLUMNA_NOMBRE_PROFESOR));
                profesor.setSeccion(resultadoDeOperacion.getString(COLUMNA_SECCION));
            }
        }

        return profesor;
    }

    @Override
    public List<ProfesorExperienciaEducativaDTO> listaProfesorEE() throws SQLException {
        
        String consultaSQL = "SELECT numeroDeTrabajador, nombreProfesor, seccion FROM profesoree";
        List<ProfesorExperienciaEducativaDTO> profesores = new ArrayList<>();
        
        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                ProfesorExperienciaEducativaDTO profesor = new ProfesorExperienciaEducativaDTO();
                profesor.setNumeroTrabajador(resultadoDeOperacion.getString(COLUMNA_NUMERO_DE_TRABAJADOR));
                profesor.setNombreProfesor(resultadoDeOperacion.getString(COLUMNA_NOMBRE_PROFESOR));
                profesor.setSeccion(resultadoDeOperacion.getString(COLUMNA_SECCION));
                profesores.add(profesor);
            }
        }

        return profesores;
    }
    
    @Override
    public int contarProfesores() throws SQLException {
        
        String contarSQL = "SELECT COUNT(*) FROM profesoree";
        int totalProfesores = 0;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                
                totalProfesores = resultado.getInt(1);
            }
        }
    
        return totalProfesores;
    }
}
