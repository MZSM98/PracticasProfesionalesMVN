
package accesoadatos.dto;

public class AcademicoEvaluadorDTO extends UsuarioDTO{
    private String numeroDeTrabajador;
    private String nombreAcademico;
    
    public String getNumeroDeTrabajador() {
        
        return numeroDeTrabajador;
    }

    public void setNumeroDeTrabajador(String numeroDeTrabajador) {
        this.numeroDeTrabajador = numeroDeTrabajador;
    }

    public String getNombreAcademico() {
        return nombreAcademico;
    }

    public void setNombreAcademico(String nombreAcademico) {
        this.nombreAcademico = nombreAcademico;
    }
    
    
}
