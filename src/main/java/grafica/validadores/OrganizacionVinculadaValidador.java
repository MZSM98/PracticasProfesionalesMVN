package grafica.validadores;

import accesoadatos.dto.OrganizacionVinculadaDTO;


public class OrganizacionVinculadaValidador {
    
    public static void validarRfcMoral(String rfcMoral, int longitudExacta) {
        
        if (rfcMoral.isEmpty()) {
            
            throw new IllegalArgumentException("El RFC es obligatorio");
        }
        
        String formatoRfcMoral = "^[A-Z]{3}[0-9]{6}[A-Z][0-9]{2}$";
    
        if (!rfcMoral.matches(formatoRfcMoral)) {
            
            throw new IllegalArgumentException("RFC inválido");
        }
    }
    
    public static void validarNombre(String nombreOV, int longitudMaxima) {
        
        if (nombreOV.isEmpty()) {
            
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        String formatoNombre = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        
        if (!nombreOV.matches(formatoNombre)){
            
            throw new IllegalArgumentException("El nombre sólo puede contener letras");
        }
        
        if (nombreOV.length() > longitudMaxima) {
            
            throw new IllegalArgumentException("El nombre no puede tener más de " + longitudMaxima + " caracteres");
        }
    }
    
    public static void validarTelefono(String telefonoOV, int longitudExacta) {
        
        if (telefonoOV.isEmpty()) {
            
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        
        String formatoTelefono = "^[0-9]{10}$";
        
        if (!telefonoOV.matches(formatoTelefono)) {
            
            throw new IllegalArgumentException("Numero de telefono inválido");
        }               
    }
    
    public static void validarDireccion(String direccionOV, int longitudMaxima) {
        
        if (direccionOV.isEmpty()) {
            
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        
        String formatoDireccion = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]$";
        
        if (!direccionOV.matches(formatoDireccion)){
            
            throw new IllegalArgumentException("La dirección no es válida");
        }
        
        if (direccionOV.length() > longitudMaxima) {
            
            throw new IllegalArgumentException("La dirección no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
    public static void validarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        validarRfcMoral(organizacionVinculadaDTO.getRfcMoral(),12);
        validarNombre(organizacionVinculadaDTO.getNombreOV(), 45);
        validarTelefono(organizacionVinculadaDTO.getTelefonoOV(),10);
        validarDireccion(organizacionVinculadaDTO.getDireccionOV(), 200);
    }
    
}