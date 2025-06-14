package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ReporteParcialDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;

public interface IReporteParcialDAO {
    
    boolean insertarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException;
    
    boolean eliminarReporteParcial(Date fechaDeReporte) throws SQLException, IOException;
    
    boolean editarReporteParcial(ReporteParcialDTO reporte) throws SQLException, IOException;
    
    ReporteParcialDTO buscarReporteParcial(Date fechaDeReporte) throws SQLException, IOException;
    
}
