package com.pdc.dao.implementacion;

import com.pdc.utileria.bd.ConexionBD;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;

public class PeriodoEscolarDAOImpl implements IPeriodoEscolarDAO {

    private static final String COLUMNA_ID_PERIODO_ESCOLAR = "idperiodoescolar";
    private static final String COLUMNA_NOMBRE_PERIODO_ESCOLAR = "nombreperiodoescolar";
    private static final String COLUMNA_FECHA_INICIO_PERIODO_ESCOLAR = "fechainicioperiodoescolar";
    private static final String COLUMNA_FECHA_FIN_PERIODO_ESCOLAR = "fechafinperiodoescolar";
    private static final String COLUMNA_STATUS = "status";
    
    @Override
    public PeriodoEscolarDTO buscarPeriodoEscolar(Integer idPeriodoEscolar) throws SQLException {
        
        String consultaSQL = "SELECT idperiodoescolar, nombreperiodoescolar, fechainicioperiodoescolar, fechafinperiodoescolar, status FROM periodoescolar WHERE idperiodoescolar = ?";
        PeriodoEscolarDTO periodoEscolar = null;

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL)) {

            declaracionPreparada.setInt(1, idPeriodoEscolar);

            ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                periodoEscolar = new PeriodoEscolarDTO();
                periodoEscolar.setIdPeriodoEscolar(resultadoDeOperacion.getInt(COLUMNA_ID_PERIODO_ESCOLAR));
                periodoEscolar.setNombrePeriodoEscolar(resultadoDeOperacion.getString(COLUMNA_NOMBRE_PERIODO_ESCOLAR));
                periodoEscolar.setFechaInicioPeriodoEscolar(resultadoDeOperacion.getDate(COLUMNA_FECHA_INICIO_PERIODO_ESCOLAR));
                periodoEscolar.setFechaFinPeriodoEscolar(resultadoDeOperacion.getDate(COLUMNA_FECHA_FIN_PERIODO_ESCOLAR));
                periodoEscolar.setEstado(resultadoDeOperacion.getString(COLUMNA_STATUS));
            }
        }

        return periodoEscolar;    
    }

    @Override
    public List<PeriodoEscolarDTO> listarPeriodos() throws SQLException {
        
        String consultaSQL = "SELECT idperiodoescolar, nombreperiodoescolar, fechainicioperiodoescolar, fechafinperiodoescolar, status FROM periodoescolar";
        List<PeriodoEscolarDTO> listaPeriodoEscolar = new ArrayList<>();

        try (Connection conexion = ConexionBD.getInstancia().conectar();
             PreparedStatement declaracionPreparada = conexion.prepareStatement(consultaSQL);
             ResultSet resultadoDeOperacion = declaracionPreparada.executeQuery()) {

            while (resultadoDeOperacion.next()) {
                
                PeriodoEscolarDTO periodoEscolar = new PeriodoEscolarDTO();
                periodoEscolar.setIdPeriodoEscolar(resultadoDeOperacion.getInt(COLUMNA_ID_PERIODO_ESCOLAR));
                periodoEscolar.setNombrePeriodoEscolar(resultadoDeOperacion.getString(COLUMNA_NOMBRE_PERIODO_ESCOLAR));
                periodoEscolar.setFechaInicioPeriodoEscolar(resultadoDeOperacion.getDate(COLUMNA_FECHA_INICIO_PERIODO_ESCOLAR));
                periodoEscolar.setFechaFinPeriodoEscolar(resultadoDeOperacion.getDate(COLUMNA_FECHA_FIN_PERIODO_ESCOLAR));
                periodoEscolar.setEstado(resultadoDeOperacion.getString(COLUMNA_STATUS));
                listaPeriodoEscolar.add(periodoEscolar);
            }
        }

        return listaPeriodoEscolar;    
    }
}
