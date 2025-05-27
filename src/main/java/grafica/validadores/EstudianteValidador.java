
package grafica.validadores;

import accesoadatos.dto.EstudianteDTO;


public class EstudianteValidador {
    
    private EstudianteValidador(){
        throw new IllegalAccessError("Es una clase de utileria");
    }
    public static boolean validarEstudiante(EstudianteDTO estudiante){
        return true;
    }
    
}
