package com.pdc.modelo.dto;

public class OrganizacionVinculadaDTO {

    private String rfcMoral;
    private String nombreOV;
    private String telefonoOV;
    private String direccionOV;
    private String estadoOV;
    private String estado;
    private String ciudad;
    private String correo;
    private String sector;

    public String getRfcMoral() {
        return rfcMoral;
    }

    public void setRfcMoral(String rfcMoral) {
        this.rfcMoral = rfcMoral;
    }

    public String getNombreOV() {
        return nombreOV;
    }

    public void setNombreOV(String nombreOV) {
        this.nombreOV = nombreOV;
    }

    public String getDireccionOV() {
        return direccionOV;
    }

    public void setDireccionOV(String direccionOV) {
        this.direccionOV = direccionOV;
    }

    public String getTelefonoOV() {
        return telefonoOV;
    }

    public void setTelefonoOV(String telefonoOV) {
        this.telefonoOV = telefonoOV;
    }

    public String getEstadoOV() {
        return estadoOV;
    }

    public void setEstadoOV(String estadoOV) {
        this.estadoOV = estadoOV;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public enum EstadoOrganizacionVinculada {
        ACTIVO, INACTIVO
    };

    @Override
    public String toString() {
        return this.nombreOV != null ? this.nombreOV : "";
    }
}
