package com.pdc.modelo.dto;

import java.sql.Date;

public class ProyectoDTO {
    
    private int proyectoID;
    private String tituloProyecto;
    private String descripcionProyecto;
    private PeriodoEscolarDTO periodoEscolar;
    private String rfcMoral;
    private String estadoProyecto;
    private Date fechaInicio;
    private Date fechaFinal;
    private String responsable;
    
    public int getProyectoID() {
        return proyectoID;
    }
    
    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }
    
    public String getTituloProyecto() {
        return tituloProyecto;
    }
    
    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }
    
    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }
    
    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }
    
    public PeriodoEscolarDTO getPeriodoEscolar() {
        return periodoEscolar;
    }
    
    public void setPeriodoEscolar(PeriodoEscolarDTO periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }
    
    public String getRfcMoral() {
        return rfcMoral;
    }
    
    public void setRfcMoral(String rfcMoral) {
        this.rfcMoral = rfcMoral;
    }
    
    public String getEstadoProyecto() {
        return estadoProyecto;
    }
    
    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date getFechaFinal() {
        return fechaFinal;
    }
    
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    public String getResponsable() {
        return responsable;
    }
    
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public enum EstadoProyecto {
        ACTIVO, INACTIVO
    }
}