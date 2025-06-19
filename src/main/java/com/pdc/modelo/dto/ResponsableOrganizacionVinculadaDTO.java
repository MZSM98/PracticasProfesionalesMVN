
package com.pdc.modelo.dto;

public class ResponsableOrganizacionVinculadaDTO {
    private String rfc;
    private String cargo;
    private String nombreResponsable;
    private String correoResponsable;
    private String rfcMoral;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getCorreoResponsable() {
        return correoResponsable;
    }

    public void setCorreoResponsable(String correoResponsable) {
        this.correoResponsable = correoResponsable;
    }
    
    public String getRfcMoral() {
        return rfcMoral;
    }

    public void setRfcMoral(String rfcMoral) {
        this.rfcMoral = rfcMoral;
    }
    
    @Override
    public String toString() {
        return this.nombreResponsable != null ? this.nombreResponsable : "";
    }
}
