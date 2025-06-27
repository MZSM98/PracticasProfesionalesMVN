package com.pdc.dao.implementacion;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.bd.ConexionBD;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;
import java.util.ArrayList;
import java.util.List;

public class ResponsableOrganizacionVinculadaDAOImpl implements IResponsableOrganizacionVinculadaDAO {

    @Override
    public boolean insertarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException {
        
        String insertarSQL = "INSERT INTO responsableorganizacionvinculada (rfc, cargo, nombreResponsable, correoResponsable, rfcMoral) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(insertarSQL)) {

            declaracionPreparada.setString(1, responsable.getRfc());
            declaracionPreparada.setString(2, responsable.getCargo());
            declaracionPreparada.setString(3, responsable.getNombreResponsable());
            declaracionPreparada.setString(4, responsable.getCorreoResponsable());
            declaracionPreparada.setString(5, responsable.getRfcMoral());
            declaracionPreparada.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean eliminarResponsableOV(String rfc) throws SQLException {
        
        String eliminarSQL = "DELETE FROM responsableorganizacionvinculada WHERE rfc = ?";

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(eliminarSQL)) {

            declaracionPreparada.setString(1, rfc);
            declaracionPreparada.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean editarResponsableOV(ResponsableOrganizacionVinculadaDTO responsable) throws SQLException {
        
        String actualizarSQL = "UPDATE responsableorganizacionvinculada SET cargo = ?, correoResponsable = ? WHERE rfc = ?";

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(actualizarSQL)) {

            declaracionPreparada.setString(1, responsable.getCargo());
            declaracionPreparada.setString(2, responsable.getCorreoResponsable());
            declaracionPreparada.setString(3, responsable.getRfc());
            declaracionPreparada.executeUpdate();
            return true;
        }
    }

    @Override
    public ResponsableOrganizacionVinculadaDTO buscarResponsableOV(String rfcMoral) throws SQLException {
        
        String consultaSQL = "SELECT rfc, cargo, nombreResponsable, correoResponsable, rfcMoral FROM responsableorganizacionvinculada WHERE rfcMoral = ?";
        ResponsableOrganizacionVinculadaDTO responsable = null;

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, rfcMoral);
            try (ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

                if (resultadoDeOperacion.next()) {
                    responsable = new ResponsableOrganizacionVinculadaDTO();
                    responsable.setRfc(resultadoDeOperacion.getString("rfc"));
                    responsable.setCargo(resultadoDeOperacion.getString("cargo"));
                    responsable.setNombreResponsable(resultadoDeOperacion.getString("nombreResponsable"));
                    responsable.setCorreoResponsable(resultadoDeOperacion.getString("correoResponsable"));
                    responsable.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                }
            }
        }
        return responsable;
    }

    @Override
    public List<ResponsableOrganizacionVinculadaDTO> listarResponsablesPorOrganizacion(String rfcMoral) throws SQLException {
        
        String consultaSQL = "SELECT rfc, nombreResponsable, cargo, correoResponsable, rfcMoral FROM responsableorganizacionvinculada WHERE rfcMoral = ?";
        List<ResponsableOrganizacionVinculadaDTO> responsables = new ArrayList<>();

        try (Connection conexionBD = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracionPreparada = conexionBD.prepareStatement(consultaSQL)) {

            declaracionPreparada.setString(1, rfcMoral);
            try (ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

                while (resultadoDeOperacion.next()) {
                    ResponsableOrganizacionVinculadaDTO responsable = new ResponsableOrganizacionVinculadaDTO();
                    responsable.setRfc(resultadoDeOperacion.getString("rfc"));
                    responsable.setNombreResponsable(resultadoDeOperacion.getString("nombreResponsable"));
                    responsable.setCargo(resultadoDeOperacion.getString("cargo"));
                    responsable.setCorreoResponsable(resultadoDeOperacion.getString("correoResponsable"));
                    responsable.setRfcMoral(resultadoDeOperacion.getString("rfcMoral"));
                    responsables.add(responsable);
                }
            }
        }
        return responsables;
    }
}
