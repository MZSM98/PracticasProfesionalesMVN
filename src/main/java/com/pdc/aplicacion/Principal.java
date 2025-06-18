package com.pdc.aplicacion;

import com.pdc.utileria.GmailUtil;

public class Principal {
    
    public static void main(String args[]){
        
        GmailUtil.configurarCorreo();
        GmailUtil.enviarCorreoHTML("vhsm9204@gmail.com", "Test", "Víctor Hugo", "Mensajito");
    }
    
}
