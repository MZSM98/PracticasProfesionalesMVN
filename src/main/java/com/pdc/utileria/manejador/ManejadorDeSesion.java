package com.pdc.utileria.manejador;

import com.pdc.modelo.dto.UsuarioDTO;

public class ManejadorDeSesion {
    
    private static UsuarioDTO usuario;

    private ManejadorDeSesion(){
        throw new IllegalAccessError("Clase de utileria..");
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
