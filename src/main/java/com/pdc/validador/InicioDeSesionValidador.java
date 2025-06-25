package com.pdc.validador;

import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.ConstantesUtil;

public class InicioDeSesionValidador {
    
    private InicioDeSesionValidador (){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarUsuario (String usuarioIngresado){
        
        if (usuarioIngresado.isEmpty()){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_INGRESE_USUARIO);
        }
        if (!usuarioIngresado.matches(ConstantesUtil.REGEX_USUARIO_VALIDO)){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_USUARIO_INVALIDO);
        }
    }
    
    private static void validarCampoContrasena(String contrasenaIngresada){
        
        if (contrasenaIngresada.isEmpty()){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_INGRESE_CONTRASENA);
        }
    }
    
    public static void validarInicioDeSesion(UsuarioDTO usuarioDTO){
        
        validarUsuario(usuarioDTO.getUsuario());
        validarCampoContrasena(usuarioDTO.getContrasena());
    }
}
