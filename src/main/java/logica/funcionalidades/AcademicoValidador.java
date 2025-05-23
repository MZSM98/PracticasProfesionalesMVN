package logica.funcionalidades;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.dto.ProfesorEEDTO;
import accesoadatos.dto.UsuarioDTO;

public class AcademicoValidador {
    public static void validarNumeroDeTrabajador(String numeroDeTrabajador, int longitudExacta) {
        if (numeroDeTrabajador.isEmpty()) {
            throw new IllegalArgumentException("El número de trabajador es obligatorio");
        }
        if (numeroDeTrabajador.length() != longitudExacta) {
            throw new IllegalArgumentException("El número de trabajador debe tener exactamente "+longitudExacta+" caracteres");
        }
    }
    
    public static void validarNombre(String nombreAcademico, int longitudMaxima) {
        if (nombreAcademico.isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (nombreAcademico.length() > longitudMaxima) {
            throw new IllegalArgumentException("El nombre no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
    public static void validarSeccion(String seccion, int longitudMaxima) {
        if (seccion.isEmpty()) {
            throw new IllegalArgumentException("La sección es obligatoria");
        }
        if (seccion.length() > longitudMaxima) {
            throw new IllegalArgumentException("La sección no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
   
    public static void validarAcademico(UsuarioDTO academico) {
            if(academico instanceof AcademicoEvaluadorDTO){
                AcademicoEvaluadorDTO academicoEvaluador = (AcademicoEvaluadorDTO) academico;
                validarNumeroDeTrabajador(academicoEvaluador.getNumeroDeTrabajador(),9);
                validarNombre(academicoEvaluador.getNombreAcademico(), 100);
            }else if(academico instanceof ProfesorEEDTO){
                ProfesorEEDTO profesorExperienciaEducativa = (ProfesorEEDTO) academico;
                validarNumeroDeTrabajador(profesorExperienciaEducativa.getNumeroTrabajador(),9);
                validarNombre(profesorExperienciaEducativa.getNombreProfesor(), 100);
                validarSeccion(profesorExperienciaEducativa.getSeccion(), 2);
            }

    }
}
