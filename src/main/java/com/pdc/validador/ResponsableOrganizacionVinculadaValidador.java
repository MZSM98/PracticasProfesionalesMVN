package com.pdc.validador;

import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.ConstantesUtil;

public class ResponsableOrganizacionVinculadaValidador {
    
    private ResponsableOrganizacionVinculadaValidador() {
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarRfc(String rfc) {
        if (rfc == null || rfc.isEmpty()) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_RFC_OBLIGATORIO);
        }
        if (!rfc.matches(ConstantesUtil.REGEX_RFC_FISICO)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_RFC_INVALIDO);
        }
    }
    
    public static void validarNombreResponsable(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_NOMBRE_OBLIGATORIO);
        }
        if (!nombre.matches(ConstantesUtil.REGEX_SOLO_LETRAS)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_NOMBRES_NO_PERSONALES);
        }
        if (!nombre.matches(ConstantesUtil.REGEX_LONGITUD_NOMBRES)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_NOMBRE);
        }
    }
    
    public static void validarCargo(String cargo) {
        if (cargo == null || cargo.isEmpty()) {
            throw new IllegalArgumentException("El cargo es obligatorio");
        }
    }
    
    public static void validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
    }
    
    public static void validarResponsable(ResponsableOrganizacionVinculadaDTO responsable) {
        validarRfc(responsable.getRfc());
        validarNombreResponsable(responsable.getNombreResponsable());
        validarCargo(responsable.getCargo());
        validarCorreo(responsable.getCorreoResponsable());
    }
}
