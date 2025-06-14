package logica.dao;

import accesoadatos.ConexionBD;
import accesoadatos.dto.TipoUsuarioDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.interfaces.ITipoUsuarioDAO;

public class TipoUsuarioDAOImpl implements ITipoUsuarioDAO {
    
    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    
    @Override
    public List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException, IOException{
        String consultaSQL = "SELECT idtipo, tipo FROM tipousuario";

        List<TipoUsuarioDTO> listaTipoUsuario = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while(resultadoDeOperacion.next()) {
                TipoUsuarioDTO tipoUsuario=  new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("idtipo"));
                tipoUsuario.setTipo(resultadoDeOperacion.getString("tipo"));
                listaTipoUsuario.add(tipoUsuario);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }
        return listaTipoUsuario;
    }

    @Override
    public TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException, IOException {
        String consultaSQL = "SELECT idtipo, tipo FROM tipousuario WHERE idtipo = ?";

        TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, idtipo);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if(resultadoDeOperacion.next()) {
                tipoUsuario =  new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("idtipo"));
                tipoUsuario.setTipo(resultadoDeOperacion.getString("tipo"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
            
        }
        return tipoUsuario;
    }
    
}
