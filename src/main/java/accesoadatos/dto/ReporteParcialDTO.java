
package accesoadatos.dto;

import java.sql.Date;

public class ReporteParcialDTO {
    private Date fechaDeReporte;
    private int horasReportadas;

    public Date getFechaDeReporte() {
        return fechaDeReporte;
    }

    public void setFechaDeReporte(Date fechaDeReporte) {
        this.fechaDeReporte = fechaDeReporte;
    }

    public int getHorasReportadas() {
        return horasReportadas;
    }

    public void setHorasReportadas(int horasReportadas) {
        this.horasReportadas = horasReportadas;
    }
    
}
