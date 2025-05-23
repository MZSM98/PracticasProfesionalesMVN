
package grafica.validadores;

import accesoadatos.dto.ProyectoDTO;


public class ProyectoValidador {

    public static void validarTituloProyecto(String tituloProyecto, int longitudMaxima){
        
        if (tituloProyecto.isEmpty()){
            
            throw new IllegalArgumentException ("El titulo es obligatorio");
        }
        
        String formatoTituloProyecto = "^[a-zA-Z]$";
        
        if (!tituloProyecto.matches(formatoTituloProyecto)){
            
            throw new IllegalArgumentException ("Ingrese sólamente letras para el título");
        }
        if (tituloProyecto.length() >= longitudMaxima){
            
            throw new IllegalArgumentException ("El título es muy largo, ingrese un título más corto");
        }
    }
    
    public static void validarDescripcionProyecto(String descripcionProyecto, int longitudMaxima){
        
        if (descripcionProyecto.isEmpty()){
            
            throw new IllegalArgumentException ("La descripción es obligatoria");
        }
                
        String formatoDescripcionProyecto = "^[a-zA-Z0-9]$";
        
        if (!descripcionProyecto.matches(formatoDescripcionProyecto)){
            
            throw new IllegalArgumentException ("La descripción debe contener sólo letras y números");
        }
        if (descripcionProyecto.length() >= longitudMaxima ){
        
            throw new IllegalArgumentException ("La descripción es muy larga, ingrese una descripción más corta");
        }
    }
    
    public static void validarProyecto (ProyectoDTO proyectoDTO){
        
        validarTituloProyecto(proyectoDTO.getTituloProyecto(), 100);
        validarDescripcionProyecto(proyectoDTO.getDescripcionProyecto(), 255);
    }
    
}
