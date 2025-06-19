package com.pdc.modelo.dto;

import java.sql.Date;

public class ProyectoDTO {
    
    private int proyectoID;
    private String tituloProyecto;
    private String descripcionProyecto;
    private PeriodoEscolarDTO periodoEscolar;
    private OrganizacionVinculadaDTO organizacion;
    private String estadoProyecto;
    private Date fechaInicio;
    private Date fechaFinal;
    private ResponsableOrganizacionVinculadaDTO responsable;
    private int vacantes;
    
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
    
    public OrganizacionVinculadaDTO getOrganizacion() {
        return organizacion;
    }
    
    public void setOrganizacion(OrganizacionVinculadaDTO organizacion) {
        this.organizacion = organizacion;
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
    
    public ResponsableOrganizacionVinculadaDTO getResponsable() {
        return responsable;
    }
    
    public void setResponsable(ResponsableOrganizacionVinculadaDTO responsable) {
        this.responsable = responsable;
    }
    
    public enum EstadoProyecto {
        ACTIVO, INACTIVO
    }

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }
    
    

    @Override
    public String toString() {
        return tituloProyecto;
    }
    
    
}