
package com.pdc.validador;

import com.pdc.modelo.dto.EstudianteDTO;
import com.pdc.utileria.ConstantesUtil;


public class EstudianteValidador {
    
    private EstudianteValidador(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    public static boolean validarEstudiante(EstudianteDTO estudiante){
        return true;
    }
    
}
