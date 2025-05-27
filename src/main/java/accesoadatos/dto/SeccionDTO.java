package accesoadatos.dto;

public class SeccionDTO {
    
    private int idSeccion;
    private String nombreSeccion;

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    @Override
    public String toString() {
        return nombreSeccion;
    }
    
    
}
