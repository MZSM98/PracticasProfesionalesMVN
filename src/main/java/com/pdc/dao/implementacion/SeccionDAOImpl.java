package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.SeccionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.ISeccionDAO;

public class SeccionDAOImpl implements ISeccionDAO {

    @Override
    public SeccionDTO buscarSeccion(Integer idSeccion) throws SQLException {
        
        String consultaSQL = "SELECT idseccion, nombreseccion FROM seccion WHERE idseccion = ?";
        SeccionDTO seccion = null;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idSeccion);
            try (ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

                if (resultadoDeOperacion.next()) {
                    seccion = new SeccionDTO();
                    seccion.setIdSeccion(resultadoDeOperacion.getInt("idseccion"));
                    seccion.setNombreSeccion(resultadoDeOperacion.getString("nombreseccion"));
                }
            }
        }
        return seccion;
    }

    @Override
    public List<SeccionDTO> listarSecciones() throws SQLException {
        
        String consultaSQL = "SELECT idseccion, nombreseccion FROM seccion";
        List<SeccionDTO> listaSeccion = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                SeccionDTO seccion = new SeccionDTO();
                seccion.setIdSeccion(resultadoDeOperacion.getInt("idseccion"));
                seccion.setNombreSeccion(resultadoDeOperacion.getString("nombreseccion"));
                listaSeccion.add(seccion);
            }
        }
        return listaSeccion;  
    }
}
