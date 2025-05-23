
package accesoadatos.dto;

public class ResponsableOrganizacionVinculadaDTO {
    private String rfc;
    private String cargo;
    private String nombreResponsable;
    private String rfcMoralOrganizacionVinculada;

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
    
    public String getRfcMoralOrganizacionVinculada() {
        return rfcMoralOrganizacionVinculada;
    }

    public void setRfcMoralOrganizacionVinculada(String rfcMoralOrganizacionVinculada) {
        this.rfcMoralOrganizacionVinculada = rfcMoralOrganizacionVinculada;
    }
    
    
}
