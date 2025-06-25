package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.UsuarioDTO;

import java.io.IOException;
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
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IUsuarioDAO interfazUsuarioDAO;
    
    public ProfesorExperienciaEducativaDAOImpl(){
        interfazUsuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public boolean insertarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO profesoree (numeroDeTrabajador, nombreProfesor, seccion) VALUES (?, ?, ?)";
        boolean insercionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(profesor.getNumeroTrabajador());
            usuario.setTipoUsuario(profesor.getTipoUsuario());
            usuario.setContrasena(ContrasenaUtil.crearContrasenaPorDefecto(profesor));
            interfazUsuarioDAO.insertarUsuario(usuario);
            
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, profesor.getNumeroTrabajador());
            declaracionPreparada.setString(2, profesor.getNombreProfesor());
            declaracionPreparada.setString(3, profesor.getSeccion());
            declaracionPreparada.executeUpdate();
            
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();  
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM profesoree WHERE numeroDeTrabajador = ?";
        boolean eliminacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, numeroTrabajador);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException, IOException {
        String actualizarSQL = "UPDATE profesoree SET nombreProfesor = ?, seccion = ? WHERE numeroDeTrabajador = ?";
        boolean actualizacionExitosa = false;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, profesor.getNombreProfesor());
            declaracionPreparada.setString(2, profesor.getSeccion());
            declaracionPreparada.setString(3, profesor.getNumeroTrabajador());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public ProfesorExperienciaEducativaDTO buscarProfesorEE(String numeroTrabajador) throws SQLException, IOException {
        String consultaSQL = "SELECT numeroDeTrabajador, nombreProfesor, seccion FROM profesoree WHERE numeroDeTrabajador = ?";
        ProfesorExperienciaEducativaDTO profesor = null;
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, numeroTrabajador);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            if (resultadoDeOperacion.next()) {
                profesor = new ProfesorExperienciaEducativaDTO();
                profesor.setNumeroTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                profesor.setNombreProfesor(resultadoDeOperacion.getString("nombreProfesor"));
                profesor.setSeccion(resultadoDeOperacion.getString("seccion"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return profesor;
    }

    @Override
    public List<ProfesorExperienciaEducativaDTO> listaProfesorEE() throws SQLException, IOException {
        String consultaSQL = "SELECT numeroDeTrabajador, nombreProfesor, seccion FROM profesoree";
        List<ProfesorExperienciaEducativaDTO> profesores = new ArrayList<>();
        
        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();
            
            while (resultadoDeOperacion.next()) {
                ProfesorExperienciaEducativaDTO profesor = new ProfesorExperienciaEducativaDTO();
                profesor.setNumeroTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                profesor.setNombreProfesor(resultadoDeOperacion.getString("nombreProfesor"));
                profesor.setSeccion(resultadoDeOperacion.getString("seccion"));
                profesores.add(profesor);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return profesores;
    }
}