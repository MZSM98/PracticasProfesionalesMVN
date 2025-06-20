
package com.pdc.modelo.dto;


public class ExperienciaEducativaDTO {
    private String nrc;
    private String nombre;

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return (this.nombre != null ? this.nombre : "") + 
               " - NRC: " + (this.nrc != null ? this.nrc : "");
    }
}
