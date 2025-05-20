package accesoadatos.dto;

public class ProyectoDTO {
    
    private int proyectoID;
    private String tituloProyecto;
    private String descripcionProyecto;
    private String periodoEscolar;
    private String rfcMoral;
    private String estadoProyecto;
    
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
    
    public String getPeriodoEscolar() {
        return periodoEscolar;
    }
    
    public void setPeriodoEscolar(String periodoEscolar) {
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
    
    public enum EstadoProyecto {ACTIVO,INACTIVO}
}