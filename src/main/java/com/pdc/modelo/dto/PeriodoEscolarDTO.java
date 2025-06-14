package com.pdc.modelo.dto;

import java.sql.Date;

public class PeriodoEscolarDTO {
    
    private Integer idPeriodoEscolar;
    private String nombrePeriodoEscolar;
    private Date fechaInicioPeriodoEscolar;
    private Date fechaFinPeriodoEscolar;

    public Integer getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(Integer idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getNombrePeriodoEscolar() {
        return nombrePeriodoEscolar;
    }

    public void setNombrePeriodoEscolar(String nombrePeriodoEscolar) {
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
    }

    public Date getFechaInicioPeriodoEscolar() {
        return fechaInicioPeriodoEscolar;
    }

    public void setFechaInicioPeriodoEscolar(Date fechaInicioPeriodoEscolar) {
        this.fechaInicioPeriodoEscolar = fechaInicioPeriodoEscolar;
    }

    public Date getFechaFinPeriodoEscolar() {
        return fechaFinPeriodoEscolar;
    }

    public void setFechaFinPeriodoEscolar(Date fechaFinPeriodoEscolar) {
        this.fechaFinPeriodoEscolar = fechaFinPeriodoEscolar;
    }

    @Override
    public String toString() {
        return nombrePeriodoEscolar;
    }
    
    

}
