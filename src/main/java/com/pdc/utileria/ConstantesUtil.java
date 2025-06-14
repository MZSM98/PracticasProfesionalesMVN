package com.pdc.utileria;


public class ConstantesUtil {
    
    private ConstantesUtil(){
        
        throw new UnsupportedOperationException(ALERTA_CLASE_UTILERIA);
    }
    
    public static final int RESTRICCION_LONGITUD_TEXTAREA = 1000;
    public static final int RESTRICCION_LONGITUD_TEXTFIELD = 1000;
    public static final int LONGITUD_NOMBRES = 100 ;
    public static final int LONGITUD_DESCRIPCIONES = 255;
    public static final int LONGITUD_DIRECCION = 200;
    public static final int LONGITUD_TELEFONO = 10;
    
    public static final String ERROR = "Error";
    public static final String ADVERTENCIA = "Advertencia";
    public static final String EXITO = "Exito";
    public static final String ACTUALIZAR = "Actualizar";
    public static final String MENU_PRINCIPAL = "Menu Principal";
    public static final String ESPACIO = " ";
    
    public static final String REGEX_LETRAS_Y_NUMEROS = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$";
    public static final String REGEX_SOLO_LETRAS = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    public static final String REGEX_RFC_MORAL = "^[A-Z]{3}[0-9]{6}[A-Z][0-9]{2}$";
    public static final String REGEX_RFC_FISICO = "^[A-Z]{4}[0-9]{6}[A-Z][0-9]{2}$";
    public static final String REGEX_NUMERO_TRABAJADOR = "^[A-Z]{3}[0-9]{6}$";
    public static final String REGEX_TELEFONO = "\\d+";
    public static final String REGEX_ESPACIOS_MULTIPLES = "\\s+";
    public static final String REGEX_LONGITUD_NOMBRES = ".{3,100}";
    public static final String REGEX_LONGITUD_DIRECCION = ".{3,200}";
    public static final String REGEX_LONGITUD_DESCRIPCION = ".{3,255}";
    public static final String REGEX_USUARIO_VALIDO = "^[a-zA-Z0-9]{9}$";
    
    public static final String ALERTA_CLASE_UTILERIA = "Clase de utilería...";
    public static final String ALERTA_ERROR_BD = "Error de conexión con la base de datos";
    public static final String ALERTA_ERROR_CARGAR_VENTANA = "No se pudo cargar la ventana, contacte con un administrador";
    public static final String ALERTA_ERROR_CARGAR_INFORMACION = "No se pudo cargar la información, contacte con un administrador";
    public static final String ALERTA_DATOS_INVALIDOS = "Datos inválidos o Incompletos";
    public static final String ALERTA_REGISTRO_EXITOSO = "Registro Exitoso";
    public static final String ALERTA_REGISTRO_FALLIDO = "Error al guardar el registro";
    public static final String ALERTA_ACTUALIZACION_EXITOSA = "Registro actualizado exitosamente";
    public static final String ALERTA_ACTUALIZACION_FALLIDA = "Falló la actualización del registro, intente más tarde";
    public static final String ALERTA_REGISTRO_RFC_MORAL_DUPLICADO = "Esa Organización vinculada ya se encuentra registrada";
    public static final String ALERTA_REGISTRO_NUMERO_EMPLEADO_DUPLICADO = "Ese Academico ya se encuentra registrado";
    public static final String ALERTA_RFC_INVALIDO = "RFC no válido";
    public static final String ALERTA_RFC_OBLIGATORIO = "El RFC es obligatorio";
    public static final String ALERTA_NOMBRE_OBLIGATORIO = "El nombre es obligatorio";
    public static final String ALERTA_NOMBRES_NO_PERSONALES = "El nombre sólo puede contener letras y números";
    public static final String ALERTA_NOMBRES_PERSONALES = "El nombre sólo puede contener letras";
    public static final String ALERTA_LONGITUD_NOMBRE = "El nombre debe tener al menos 3 letras y máximo 100";
    public static final String ALERTA_LONGITUD_DIRECCION = "La dirección debe tener al menos 3 letras y máximo 200";
    public static final String ALERTA_FORMATO_DIRECCION = "La direccion sólo puede contener letras y números";
    public static final String ALERTA_USUARIO_INVALIDO = "Nombre de usuario no valido";
    public static final String ALERTA_INGRESE_USUARIO = "Ingrese el nombre de usuario";
    public static final String ALERTA_INGRESE_CONTRASENA = "Ingrese su contraseña";
    public static final String ALERTA_CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos";
    public static final String ALERTA_SELECCION_TIPO_USUARIO = "Elija un tipo de usuario";
    public static final String ALERTA_TITULO_OBLIGATORIO = "El titulo es obligatorio";
    public static final String ALERTA_FORMATO_TITULO = "Ingrese sólamente letras para el título";
    public static final String ALERTA_LONGITUD_TITULO = "El título debe tener al menos 3 letras y máximo 100";
    public static final String ALERTA_DESCRIPCION_OBLIGATORIA = "La descripción es obligatoria";
    public static final String ALERTA_FORMATO_DESCRIPCION = "La descripción sólo puede contener letras y números";
    public static final String ALERTA_LONGITUD_DESCRIPCION = "La descripción debe tener al menos 3 letras y máximo 255";
    public static final String ALERTA_DIRECCION_OBLIGATORIA = "La direccion es obligatoria";
    public static final String ALERTA_LONGITUD_TELEFONO = "El número de telefono debe tener 10 dígitos";
    public static final String ALERTA_TELEFONO_OBLIGATORIO = "El número de telefono es obligatorio";
    public static final String ALERTA_FORMATO_TELEFONO = "El telefono sólo puede contener números";
    public static final String ALERTA_CAMBIO_ESTADO = "Estado de la organización cambiado a:";
    public static final String ALERTA_TIPO_USUARIO_NO_VALIDO = "El tipo de usuario no existe";
    public static final String ALERTA_SELECCION_EDITAR = "Por favor selecciona un registro para editar";
    
    public static final String LOG_ACTUALIZACION_FALLIDA = "Error al actualizar el registro";
    public static final String LOG_ERROR_VENTANA = "No se pudo cargar la ventana";
    public static final String LOG_ERROR_REGISTRO_DUPLICADO = "Se intentó registrar una clave primaria ya existente";
    public static final String LOG_DATOS_NO_VALIDOS = "Se ingresaron datos no válidos";
    public static final String LOG_ERROR_CARGAR_INFORMACION = "No se pudo cargar la información";
}
