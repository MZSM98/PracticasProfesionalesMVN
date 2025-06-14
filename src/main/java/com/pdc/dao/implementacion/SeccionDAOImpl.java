package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.SeccionDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.ISeccionDAO;

public class SeccionDAOImpl implements ISeccionDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
        
    @Override
    public SeccionDTO buscarSeccion(Integer idSeccion) throws SQLException, IOException{
        String consultaSQL = "SELECT idseccion, nombreseccion FROM seccion WHERE idseccion = ?";
        SeccionDTO seccion = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, idSeccion);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                seccion = new SeccionDTO();
                seccion.setIdSeccion(resultadoDeOperacion.getInt("idseccion"));
                seccion.setNombreSeccion(resultadoDeOperacion.getString("nombreseccion"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return seccion;
    }

    @Override
    public List<SeccionDTO> listarSecciones() throws SQLException, IOException{
        String consultaSQL = "SELECT idseccion, nombreseccion FROM seccion";
        List<SeccionDTO> listaSeccion = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while(resultadoDeOperacion.next()) {
                SeccionDTO seccion = new SeccionDTO();
                seccion.setIdSeccion(resultadoDeOperacion.getInt("idseccion"));
                seccion.setNombreSeccion(resultadoDeOperacion.getString("nombreseccion"));
                listaSeccion.add(seccion);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return listaSeccion;  
    }
    
}
