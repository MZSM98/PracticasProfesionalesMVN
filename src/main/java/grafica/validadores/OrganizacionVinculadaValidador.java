package grafica.validadores;

import accesoadatos.dto.OrganizacionVinculadaDTO;
import grafica.utils.ConstantesUtil;


public class OrganizacionVinculadaValidador {
    
    private OrganizacionVinculadaValidador(){
        
        throw new IllegalAccessError (ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarRfcMoral(String rfcMoral) {
        
        if (rfcMoral.isEmpty()) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_RFC_OBLIGATORIO);
        }
            
        if (!rfcMoral.matches(ConstantesUtil.REGEX_RFC_MORAL)) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_RFC_INVALIDO);
        }
    }
    
    public static void validarNombre(String nombreOV) {
        
        if (nombreOV.isEmpty()) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_NOMBRE_OBLIGATORIO);
        }
        if (!nombreOV.matches(ConstantesUtil.REGEX_LETRAS_Y_NUMEROS)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_NOMBRES_NO_PERSONALES);
        }
        if (!nombreOV.matches(ConstantesUtil.REGEX_LONGITUD_NOMBRES)) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_NOMBRE);
        }
    }
    
    public static void validarTelefono(String telefonoOV) {
        
        if (telefonoOV.isEmpty()) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_TELEFONO_OBLIGATORIO);
        }        
        if (telefonoOV.length() !=(ConstantesUtil.LONGITUD_TELEFONO)) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_TELEFONO);
        }               
    }
    
    public static void validarDireccion(String direccionOV) {
        
        if (direccionOV.isEmpty()) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_DIRECCION_OBLIGATORIA);
        }      
        if (!direccionOV.matches(ConstantesUtil.REGEX_LETRAS_Y_NUMEROS)){
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_FORMATO_DIRECCION);
        }
        if (!direccionOV.matches(ConstantesUtil.REGEX_LONGITUD_DIRECCION)) {
            
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_DIRECCION);
        }
    }
    
    public static void validarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        validarRfcMoral(organizacionVinculadaDTO.getRfcMoral());
        validarNombre(organizacionVinculadaDTO.getNombreOV());
        validarTelefono(organizacionVinculadaDTO.getTelefonoOV());
        validarDireccion(organizacionVinculadaDTO.getDireccionOV());
    }
    
}