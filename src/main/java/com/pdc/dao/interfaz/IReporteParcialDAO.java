package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ReporteParcialDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;

public interface IReporteParcialDAO {
    
    public boolean insertarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException;
    public boolean eliminarReporteParcial(Date fechaDeReporte) throws SQLException, IOException;
    public boolean editarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException;
    public ReporteParcialDTO buscarReporteParcial(Date fechaDeReporte) throws SQLException, IOException;
}
