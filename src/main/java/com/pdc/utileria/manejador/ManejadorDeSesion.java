package com.pdc.utileria.manejador;

import com.pdc.modelo.dto.UsuarioDTO;
import com.pdc.utileria.ConstantesUtil;

public class ManejadorDeSesion {
    
    private static UsuarioDTO usuario;

    private ManejadorDeSesion(){
        
        throw new IllegalStateException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }

    public static UsuarioDTO getUsuario() {
        
        return usuario;
    }

    public static void iniciarSesion(UsuarioDTO usuario) {
        
        ManejadorDeSesion.usuario = usuario;
    }

   public static void cerrarSesion(){
       
       usuario = null;
   }
}
