    
package com.pdc.dao.implementacion;
import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
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
import com.pdc.dao.interfaz.IAcademicoEvaluadorDAO;
import com.pdc.dao.interfaz.IUsuarioDAO;

public class AcademicoEvaluadorDAOImpl implements IAcademicoEvaluadorDAO {
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    
    private IUsuarioDAO interfazUsuarioDAO;
    
    public AcademicoEvaluadorDAOImpl(){
        interfazUsuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public boolean insertarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException{
        
        String insertarSQL = "INSERT INTO academicoevaluador (numeroDeTrabajador, nombreAcademico) VALUES (?,?)";
        boolean insercionExitosa = false;
        
        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(academicoEvaluador.getNumeroDeTrabajador());
            usuario.setTipoUsuario(academicoEvaluador.getTipoUsuario());
            usuario.setContrasena(ContrasenaUtil.crearContrasenaPorDefecto(academicoEvaluador));
            interfazUsuarioDAO.insertarUsuario(usuario);
            
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, academicoEvaluador.getNumeroDeTrabajador());
            declaracionPreparada.setString(2, academicoEvaluador.getNombreAcademico());
            declaracionPreparada.executeUpdate();
            
            insercionExitosa = true;
            
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();  
            if (conexionBD != null) conexionBD.close();
        
        }
        
        return insercionExitosa;       
    }

    @Override
    public boolean eliminarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException, IOException{

        String eliminarSQL = "DELETE FROM academicoevaluador WHERE numeroDeTrabajador = ?";
        boolean eliminacionExitosa = false;
        
        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;          

        } finally {

            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }

        return eliminacionExitosa;
    }

    

    @Override
    public boolean editarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException{
        String actualizarSQL = "UPDATE academicoevaluador SET nombreAcademico = ? WHERE numeroDeTrabajador = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, academicoEvaluador.getNombreAcademico());
            declaracionPreparada.setString(2, academicoEvaluador.getNumeroDeTrabajador());
            declaracionPreparada.executeUpdate();
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }

        return actualizacionExitosa;
        
    }
    
    @Override
    public AcademicoEvaluadorDTO buscarAcademicoEvaluador (String numeroDeTrabajador) throws SQLException, IOException{
        String consultaSQL = "SELECT numeroDeTrabajador, nombreAcademico FROM academicoevaluador WHERE numeroDeTrabajador = ?";
        AcademicoEvaluadorDTO academico = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, numeroDeTrabajador);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                academico = new AcademicoEvaluadorDTO();
                academico.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                academico.setNombreAcademico(resultadoDeOperacion.getString("nombreAcademico"));
                
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }

        return academico;
    }

    @Override
    public List<AcademicoEvaluadorDTO> listarAcademicoEvaluador() throws SQLException, IOException {
        String consultaSQL = "SELECT numeroDeTrabajador, nombreAcademico FROM academicoevaluador";
        List<AcademicoEvaluadorDTO> academicos = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while(resultadoDeOperacion.next()) {
                AcademicoEvaluadorDTO academico = new AcademicoEvaluadorDTO();
                academico.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                academico.setNombreAcademico(resultadoDeOperacion.getString("nombreAcademico"));
                academicos.add(academico);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }

        return academicos;    
    }


}
