
package grafica.validadores;

import accesoadatos.dto.EstudianteDTO;
import grafica.utils.ConstantesUtil;


public class EstudianteValidador {
    
    private EstudianteValidador(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    public static boolean validarEstudiante(EstudianteDTO estudiante){
        return true;
    }
    
}
