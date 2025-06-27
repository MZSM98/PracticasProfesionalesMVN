package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.TipoUsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.ITipoUsuarioDAO;

public class TipoUsuarioDAOImpl implements ITipoUsuarioDAO {
    
    @Override
    public List<TipoUsuarioDTO> listaTipoUsuario() throws SQLException {
        String consultaSQL = "SELECT idtipo, tipo FROM tipousuario";
        List<TipoUsuarioDTO> listaTipoUsuario = new ArrayList<>();

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("idtipo"));
                tipoUsuario.setTipo(resultadoDeOperacion.getString("tipo"));
                listaTipoUsuario.add(tipoUsuario);
            }
        }
        return listaTipoUsuario;
    }

    @Override
    public TipoUsuarioDTO buscarTipoUsuario(Integer idtipo) throws SQLException {
        String consultaSQL = "SELECT idtipo, tipo FROM tipousuario WHERE idtipo = ?";
        TipoUsuarioDTO tipoUsuario = null;

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idtipo);
            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                tipoUsuario = new TipoUsuarioDTO();
                tipoUsuario.setIdTipo(resultadoDeOperacion.getInt("idtipo"));
                tipoUsuario.setTipo(resultadoDeOperacion.getString("tipo"));
            }
        }
        return tipoUsuario;
    }
}
