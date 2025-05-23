package grafica.utils;

import accesoadatos.dto.AcademicoEvaluadorDTO;
import accesoadatos.dto.CoordinadorDTO;
import accesoadatos.dto.EstudianteDTO;
import accesoadatos.dto.ProfesorEEDTO;

public class ContrasenaUtil {
    
    public static final String creaContrasenaPorDefecto(AcademicoEvaluadorDTO academicoEvaluador){
        String contrasenaDefecto = academicoEvaluador.getNumeroDeTrabajador().substring(0,7);
        return contrasenaDefecto.concat(academicoEvaluador.getNombreAcademico().substring(0, 2));
    }
    public static final String creaContrasenaPorDefecto(ProfesorEEDTO profesorEE){
        String contrasenaDefecto = profesorEE.getNumeroTrabajador().substring(0,7);
        return contrasenaDefecto.concat(profesorEE.getNombreProfesor().substring(0, 2));
    }
    public static final String creaContrasenaPorDefecto(CoordinadorDTO coordinador){
        String contrasenaDefecto = coordinador.getNumeroDeTrabajador().substring(0,7);
        return contrasenaDefecto.concat(coordinador.getNombreCoordinador().substring(0, 2));
    }    
    public static final String creaContrasenaPorDefecto(EstudianteDTO estudiante){
        String contrasenaDefecto = estudiante.getMatricula().substring(0,7);
        return contrasenaDefecto.concat(estudiante.getNombreEstudiante().substring(0, 2));
    }    
    
}
