package logica.dao;

import logica.interfaces.InterfazReporteParcialDAO;
import accesoadatos.dto.ReporteParcialDTO;
import accesoadatos.ConexionBD;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class ReporteParcialDAO implements InterfazReporteParcialDAO {

    Connection conexionBD;
    PreparedStatement declaracionPreparada;
    ResultSet resultadoDeOperacion;

    @Override
    public boolean insertarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException {
        String insertarSQL = "INSERT INTO reporteparcial (fechaDeReporte, horasReportadas) VALUES (?, ?)";
        boolean insercionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setDate(1, reporte.getFechaDeReporte());
            declaracionPreparada.setInt(2, reporte.getHorasReportadas());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarReporteParcial(Date fechaDeReporte) throws SQLException, IOException {
        String eliminarSQL = "DELETE FROM reporteparcial WHERE fechaDeReporte = ?";
        boolean eliminacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setDate(1, fechaDeReporte);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException {
        String actualizarSQL = "UPDATE reporteparcial SET horasReportadas = ? WHERE fechaDeReporte = ?";
        boolean actualizacionExitosa = false;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setInt(1, reporte.getHorasReportadas());
            declaracionPreparada.setDate(2, reporte.getFechaDeReporte());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public ReporteParcialDTO buscarReporteParcial(Date fechaDeReporte) throws SQLException, IOException {
        String consultaSQL = "SELECT fechaDeReporte, horasReportadas FROM reporteparcial WHERE fechaDeReporte = ?";
        ReporteParcialDTO reporte = null;

        try {
            conexionBD = new ConexionBD().getConexionBD();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setDate(1, fechaDeReporte);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                reporte = new ReporteParcialDTO();
                reporte.setFechaDeReporte(resultadoDeOperacion.getDate("fechaDeReporte"));
                reporte.setHorasReportadas(resultadoDeOperacion.getInt("horasReportadas"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return reporte;
    }
}
    