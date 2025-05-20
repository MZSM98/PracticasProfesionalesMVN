package grafica.validadores;

import accesoadatos.dto.AcademicoEvaluadorDTO;

public class AcademicoValidador {
    
    public static void validarNumeroDeTrabajador(String numeroDeTrabajador, int longitudExacta) {
        
        if (numeroDeTrabajador.isEmpty()) {
            
            throw new IllegalArgumentException("El número de trabajador es obligatorio");
        }
        
        String formatoRfcMoral = "^[A-Z]{2}[0-9]{7}$";
    
        if (!numeroDeTrabajador.matches(formatoRfcMoral)) {
            
            throw new IllegalArgumentException("RFC inválido");
        }
    }
    
    public static void validarNombre(String nombreAcademico, int longitudMaxima) {
        
        if (nombreAcademico.isEmpty()) {
            
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        String formatoNombre = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        
        if (!nombreAcademico.matches(formatoNombre)){
            
            throw new IllegalArgumentException("El nombre sólo puede contener letras");
        }
        
        if (nombreAcademico.length() > longitudMaxima) {
            
            throw new IllegalArgumentException("El nombre no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
   
    public static void validarAcademico(AcademicoEvaluadorDTO academicoEvaluadorDTO) {
        
        validarNumeroDeTrabajador(academicoEvaluadorDTO.getNumeroDeTrabajador(),9);
        validarNombre(academicoEvaluadorDTO.getNombreAcademico(), 100);
    }
}
