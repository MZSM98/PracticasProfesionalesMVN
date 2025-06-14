package com.pdc.modelo.dto;

public class CoordinadorDTO extends UsuarioDTO {
    
    private String numeroDeTrabajador;
    private String nombreCoordinador;
    private String telefono;

    public String getNumeroDeTrabajador() {
        return numeroDeTrabajador;
    }

    public void setNumeroDeTrabajador(String numeroDeTrabajador) {
        this.numeroDeTrabajador = numeroDeTrabajador;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
