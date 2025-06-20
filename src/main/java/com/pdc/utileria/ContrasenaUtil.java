package com.pdc.utileria;

import com.pdc.modelo.dto.AcademicoEvaluadorDTO;
import com.pdc.modelo.dto.CoordinadorDTO;
import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;

public class ContrasenaUtil {
    
    private ContrasenaUtil(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static final String crearContrasenaPorDefecto(AcademicoEvaluadorDTO academicoEvaluador){
        
        String contrasenaDefecto = academicoEvaluador.getNumeroDeTrabajador().substring(0,7);
        return contrasenaDefecto.concat(academicoEvaluador.getNombreAcademico().substring(0, 2));
    }
    
    public static final String crearContrasenaPorDefecto(ProfesorExperienciaEducativaDTO profesorEE){
        
        String contrasenaDefecto = profesorEE.getNumeroTrabajador().substring(0,7);
        return contrasenaDefecto.concat(profesorEE.getNombreProfesor().substring(0, 2));
    }
    
    public static final String crearContrasenaPorDefecto(CoordinadorDTO coordinador){
        
        String contrasenaDefecto = coordinador.getNumeroDeTrabajador().substring(0,7);
        return contrasenaDefecto.concat(coordinador.getNombreCoordinador().substring(0, 2));
    }   
    
    public static final String crearContrasenaPorDefecto(EstudianteDTO estudiante){
        
        String contrasenaDefecto = estudiante.getMatricula().substring(0,7);
        return contrasenaDefecto.concat(estudiante.getNombreEstudiante().substring(0, 2));
    }    
    
}
