    
package com.pdc.dao.implementacion;
import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.UsuarioDTO;
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
    
    private final IUsuarioDAO interfazUsuarioDAO;
    
    public AcademicoEvaluadorDAOImpl(){
        interfazUsuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public boolean insertarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluador) throws SQLException {
        
        String insertarSQL = "INSERT INTO academicoevaluador (numeroDeTrabajador, nombreAcademico) VALUES (?,?)";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(insertarSQL)) {

            conexion.setAutoCommit(false);

            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(academicoEvaluador.getNumeroDeTrabajador());
            usuario.setTipoUsuario(academicoEvaluador.getTipoUsuario());
            usuario.setContrasena(ContrasenaUtil.crearContrasenaPorDefecto(academicoEvaluador));
            interfazUsuarioDAO.insertarUsuario(usuario);

            declaracionPreparada.setString(1, academicoEvaluador.getNumeroDeTrabajador());
            declaracionPreparada.setString(2, academicoEvaluador.getNombreAcademico());

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
    public boolean eliminarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException {
        
        String eliminarSQL = "DELETE FROM academicoevaluador WHERE numeroDeTrabajador = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(eliminarSQL)) {

            declaracionPreparada.setString(1, numeroDeTrabajador);

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean editarAcademicoEvaluador(AcademicoEvaluadorDTO academicoEvaluador) throws SQLException {

        String actualizarSQL = "UPDATE academicoevaluador SET nombreAcademico = ? WHERE numeroDeTrabajador = ?";

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, academicoEvaluador.getNombreAcademico());
            declaracionPreparada.setString(2, academicoEvaluador.getNumeroDeTrabajador());

            int filasAfectadas = declaracionPreparada.executeUpdate();

            return filasAfectadas > 0;
        }
    }
    
    @Override
    public AcademicoEvaluadorDTO buscarAcademicoEvaluador(String numeroDeTrabajador) throws SQLException {

       String consultaSQL = "SELECT numeroDeTrabajador, nombreAcademico FROM academicoevaluador WHERE numeroDeTrabajador = ?";
       AcademicoEvaluadorDTO academico = null;

       try (Connection conexion = ConexionBD.getInstancia().conectar();
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

           declaracionPreparada.setString(1, numeroDeTrabajador);

           ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

           if (resultadoDeOperacion.next()) {
               
               academico = new AcademicoEvaluadorDTO();
               academico.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
               academico.setNombreAcademico(resultadoDeOperacion.getString("nombreAcademico"));
           }
       }

       return academico;
    }

    @Override
    public List<AcademicoEvaluadorDTO> listarAcademicoEvaluador() throws SQLException {
        
        String consultaSQL = "SELECT numeroDeTrabajador, nombreAcademico FROM academicoevaluador";
        List<AcademicoEvaluadorDTO> academicos;
        academicos = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()){

            while(resultadoDeOperacion.next()) {
                AcademicoEvaluadorDTO academico = new AcademicoEvaluadorDTO();
                academico.setNumeroDeTrabajador(resultadoDeOperacion.getString("numeroDeTrabajador"));
                academico.setNombreAcademico(resultadoDeOperacion.getString("nombreAcademico"));
                academicos.add(academico);
            }
        } 
        return academicos;    
    }
    
    @Override
    public int contarAcademicosEvaluador() throws SQLException{
        
        final String contarSQL = "SELECT COUNT(*) FROM academicoevaluador";
        int totalAcademicosEvaluadores = 0;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
            PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
            ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                totalAcademicosEvaluadores = resultado.getInt(1);
            }
        }
    
        return totalAcademicosEvaluadores;
    }
}
