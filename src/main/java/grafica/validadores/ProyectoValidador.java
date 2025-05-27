
package grafica.validadores;

import accesoadatos.dto.ProyectoDTO;
import grafica.utils.ConstantesUtil;


public class ProyectoValidador {
    
    private ProyectoValidador(){
        
        throw new IllegalAccessError (ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarTituloProyecto(String tituloProyecto){
        
        if (tituloProyecto.isEmpty()){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_TITULO_OBLIGATORIO);
        }
        if (!tituloProyecto.matches(ConstantesUtil.REGEX_SOLO_LETRAS)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_FORMATO_TITULO);
        }
        if (!tituloProyecto.matches(ConstantesUtil.REGEX_LONGITUD_NOMBRES)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_TITULO);
        }
    }
    
    public static void validarDescripcionProyecto(String descripcionProyecto){
        
        if (descripcionProyecto.isEmpty()){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_DESCRIPCION_OBLIGATORIA);
        }
        if (!descripcionProyecto.matches(ConstantesUtil.REGEX_LETRAS_Y_NUMEROS)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_FORMATO_DESCRIPCION);
        }
        if (!descripcionProyecto.matches(ConstantesUtil.REGEX_LONGITUD_DESCRIPCION) ){
        
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_DESCRIPCION);
        }
    }
    
    public static void validarProyecto (ProyectoDTO proyectoDTO){
        
        validarTituloProyecto(proyectoDTO.getTituloProyecto());
        validarDescripcionProyecto(proyectoDTO.getDescripcionProyecto());
    }
    
}
