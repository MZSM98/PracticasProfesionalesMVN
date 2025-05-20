    
package logica.dao;
import logica.interfaces.InterfazAcademicoEvaluadorDAO;
import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AcademicoEvaluadorDAO implements InterfazAcademicoEvaluadorDAO {
    
    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;
       
    @Override
    public boolean insertarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluador) throws SQLException, IOException{
        
        String insertarSQL = "INSERT INTO academicoevaluador (numeroDeTrabajador, nombreAcademico) VALUES (?,?)";
        boolean insercionExitosa = false;
        
        try {
            
            conexionBD = new ConexionBD().getConexionBD();
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
            
            conexionBD = new ConexionBD().getConexionBD();
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
            conexionBD = new ConexionBD().getConexionBD();
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
            conexionBD = new ConexionBD().getConexionBD();
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
            conexionBD = new ConexionBD().getConexionBD();
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
