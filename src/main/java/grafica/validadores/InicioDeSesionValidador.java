package grafica.validadores;

import accesoadatos.dto.TipoUsuarioDTO;
import accesoadatos.dto.UsuarioDTO;
import grafica.utils.ConstantesUtil;

public class InicioDeSesionValidador {
    
    private InicioDeSesionValidador (){
        
        throw new IllegalAccessError(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    private static void validarTipoUsuario(TipoUsuarioDTO tipoUsuario) {
        
        if (tipoUsuario == null){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_SELECCION_TIPO_USUARIO);
        }
    }
    
    public static void validarUsuario (String usuarioIngresado){
        
        if (usuarioIngresado.isEmpty()){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_INGRESE_USUARIO);
        }
        if (!usuarioIngresado.matches(ConstantesUtil.REGEX_USUARIO_VALIDO)){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_USUARIO_INVALIDO);
        }
    }
    
    private static void validarCampoContrasena(String contrasena){
        
        if (contrasena.isEmpty()){
            
            throw new IllegalArgumentException (ConstantesUtil.ALERTA_INGRESE_CONTRASENA);
        }
    }
    
    public static void validarInicioDeSesion(UsuarioDTO usuarioDTO){
        
        validarTipoUsuario(usuarioDTO.getTipoUsuario());
        validarUsuario(usuarioDTO.getUsuario());
        validarCampoContrasena(usuarioDTO.getContrasena());
    }
}
