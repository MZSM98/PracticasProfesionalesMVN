package grafica.validadores;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import grafica.utils.ConstantesUtil;

public class AcademicoValidador {
    
    private AcademicoValidador(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarNumeroDeTrabajador(String numeroDeTrabajador) {
        
        if (numeroDeTrabajador.isEmpty()) {
            
            throw new IllegalArgumentException("El número de trabajador es obligatorio");
        }
        if (!numeroDeTrabajador.matches(ConstantesUtil.REGEX_NUMERO_TRABAJADOR)) {
            
            throw new IllegalArgumentException("RFC inválido");
        }
    }
    
    public static void validarNombre(String nombreAcademico, int longitudMaxima) {
        
        if (nombreAcademico.isEmpty()) {
            
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (!nombreAcademico.matches(ConstantesUtil.REGEX_SOLO_LETRAS)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_NOMBRES_PERSONALES);
        }
        if (nombreAcademico.length() > longitudMaxima) {
            
            throw new IllegalArgumentException("El nombre no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
   
    public static void validarAcademico(AcademicoEvaluadorDTO academicoEvaluadorDTO) {
        
        validarNumeroDeTrabajador(academicoEvaluadorDTO.getNumeroDeTrabajador());
        validarNombre(academicoEvaluadorDTO.getNombreAcademico(), 100);
    }
}
