package logica.dao;

import accesoadatos.ConexionBD;
import accesoadatos.dto.PeriodoEscolarDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.interfaces.InterfazPeriodoEscolarDAO;

public class PeriodoEscolarDAO implements InterfazPeriodoEscolarDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    
    @Override
    public PeriodoEscolarDTO buscarPeriodoEscolar(Integer idPeriodoEscolar)  throws SQLException, IOException {
        String consultaSQL = "SELECT idperiodoescolar, nombreperiodoescolar, fechainicioperiodoescolar, fechafinperiodoescolar FROM periodoescolar WHERE idperiodoescolar=?";
        PeriodoEscolarDTO periodoEscolar = null;

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, idPeriodoEscolar);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                periodoEscolar = new PeriodoEscolarDTO();
                periodoEscolar.setIdPeriodoEscolar(resultadoDeOperacion.getInt("idperiodoescolar"));
                periodoEscolar.setNombrePeriodoEscolar(resultadoDeOperacion.getString("nombreperiodoescolar"));
                periodoEscolar.setFechaInicioPeriodoEscolar(resultadoDeOperacion.getDate("fechainicioperiodoescolar"));
                periodoEscolar.setFechaFinPeriodoEscolar(resultadoDeOperacion.getDate("fechafinperiodoescolar"));
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return periodoEscolar;    
    }

    @Override
    public List<PeriodoEscolarDTO> listarPeriodos()  throws SQLException, IOException {
        String consultaSQL = "SELECT idperiodoescolar, nombreperiodoescolar, fechainicioperiodoescolar, fechafinperiodoescolar FROM periodoescolar";
        List<PeriodoEscolarDTO> listaPeriodoEscolar = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while(resultadoDeOperacion.next()) {
                PeriodoEscolarDTO periodoEscolar = new PeriodoEscolarDTO();
                periodoEscolar.setIdPeriodoEscolar(resultadoDeOperacion.getInt("idperiodoescolar"));
                periodoEscolar.setNombrePeriodoEscolar(resultadoDeOperacion.getString("nombreperiodoescolar"));
                periodoEscolar.setFechaInicioPeriodoEscolar(resultadoDeOperacion.getDate("fechainicioperiodoescolar"));
                periodoEscolar.setFechaFinPeriodoEscolar(resultadoDeOperacion.getDate("fechafinperiodoescolar"));
                listaPeriodoEscolar.add(periodoEscolar);
            }
        } finally {
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return listaPeriodoEscolar;    
    }
    
}
