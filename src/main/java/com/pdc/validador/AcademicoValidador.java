package com.pdc.validador;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.ConstantesUtil;

public class AcademicoValidador {
    
    private AcademicoValidador(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarNumeroDeTrabajador(String numeroDeTrabajador) {
        
        if (numeroDeTrabajador.isEmpty()) {
            
            throw new IllegalArgumentException("El número de trabajador es obligatorio");
        }
        if (!numeroDeTrabajador.matches(ConstantesUtil.REGEX_NUMERO_TRABAJADOR)) {
            
            throw new IllegalArgumentException("Numero de trabajador no válido");
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
                validarNumeroDeTrabajador(academicoEvaluador.getNumeroDeTrabajador());
                validarNombre(academicoEvaluador.getNombreAcademico(), 100);
            }else if(academico instanceof ProfesorExperienciaEducativaDTO){
                ProfesorExperienciaEducativaDTO profesorExperienciaEducativa = (ProfesorExperienciaEducativaDTO) academico;
                validarNumeroDeTrabajador(profesorExperienciaEducativa.getNumeroTrabajador());
                validarNombre(profesorExperienciaEducativa.getNombreProfesor(), 100);
                validarSeccion(profesorExperienciaEducativa.getSeccion(), 2);
            }

    }
}
