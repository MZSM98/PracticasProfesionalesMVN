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
    
    public static void validarNombre(String nombreOV, String longitudMinimaYMaxima) {
        
        if (nombreOV.isEmpty()) {
            
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        String formatoNombre = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s]+$";
        
        if (!nombreOV.matches(formatoNombre)){
            
            throw new IllegalArgumentException("El nombre sólo puede contener letras y números");
        }
        
        if (!nombreOV.matches(longitudMinimaYMaxima)) {
            
            throw new IllegalArgumentException("El nombre debe tener al menos 3 letras y menos de 100");
        }
    }
    
    public static void validarTelefono(String telefonoOV, int longitudExacta) {
        
        if (telefonoOV.isEmpty()) {
            
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        
        String formatoTelefono = "^[0-9]{10}$";
        
        if (!telefonoOV.matches(formatoTelefono)) {
            
            throw new IllegalArgumentException("Numero de telefono inválido: el número debe tener 10 dígitos");
        }               
    }
    
    public static void validarDireccion(String direccionOV, int longitudMaxima) {
        
        if (direccionOV.isEmpty()) {
            
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        
        String formatoDireccion = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]$";
        
        if (!direccionOV.matches(formatoDireccion)){
            
            throw new IllegalArgumentException("La dirección sólo debe contener letras y números");
        }
        
        if (direccionOV.length() > longitudMaxima) {
            
            throw new IllegalArgumentException("La dirección no puede exceder los " + longitudMaxima + " caracteres");
        }
    }
    
    public static void validarOrganizacionVinculada(OrganizacionVinculadaDTO organizacionVinculadaDTO) {
        
        validarRfcMoral(organizacionVinculadaDTO.getRfcMoral(),12);
        validarNombre(organizacionVinculadaDTO.getNombreOV(), ".{3,100}");
        validarTelefono(organizacionVinculadaDTO.getTelefonoOV(),10);
        validarDireccion(organizacionVinculadaDTO.getDireccionOV(), 200);
    }
    
}