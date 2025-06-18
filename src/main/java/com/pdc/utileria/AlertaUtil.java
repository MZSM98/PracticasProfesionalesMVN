
package com.pdc.utileria;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertaUtil {
    
    public static final String EXITO = "EXITO";
    public static final String ADVERTENCIA = "ADVERTENCIA";
    public static final String ERROR = "ERROR";
    public static final String INFORMACION = "INFORMACION";
    public static final String CONFIRMACION = "CONFIRMACION";
    public static final String ALERTA_ERROR_VENTANA = "No se pudo abrir la ventana, intente más tarde";
    public static final String ALERTA_ERROR_BD = "Error de conexión con la base de datos";
    public static final String ALERTA_REGISTRO_EXITOSO = "Registro Exitoso";
    public static final String ALERTA_ACTUALIZACION_EXITOSA ="Datos actualizados exitosamente.";
    public static final String ALERTA_REGISTRO_FALLIDO = "Error al guardar el registro";
    public static final String ALERTA_ELIMINACION_FALLIDA = "No se pudo eliminar el registro, intente más tarde";
    public static final String ALERTA_ERROR_CARGAR_INFORMACION = "No se pudo cargar la información, contacte con un administrador";
    public static final String ALERTA_CONFIRMAR_ELIMINAR= "¿Está seguro que desea eliminar este registro?";
    public static final String ALERTA_INFO_CANCELACION= "Se perderán todos los datos ingresados";
    public static final String ALERTA_HEADER_CANCELACION = "¿Está seguro que desea cancelar?";
    public static final String ALERTA_ACTUALIZACION_FALLIDA = "Falló la actualización del registro, intente más tarde";

    private AlertaUtil(){
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public static void mostrarAlertaRegistroExitoso(){
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle(EXITO);
        exito.setHeaderText(null);
        exito.setContentText(ALERTA_REGISTRO_EXITOSO);
        exito.showAndWait();
    }
    
    public static void mostrarAlertaActualizacionExitosa(){
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle(EXITO);
        exito.setHeaderText(null);
        exito.setContentText(ALERTA_ACTUALIZACION_EXITOSA);
        exito.showAndWait();
    }
    
    public static void mostrarAlertaRegistroFallido(){
        Alert fallido = new Alert(Alert.AlertType.ERROR);
        fallido.setTitle(ERROR);
        fallido.setHeaderText(null);
        fallido.setContentText(ALERTA_REGISTRO_FALLIDO);
        fallido.showAndWait();
    }
    
    public static void mostrarAlertaActualizacionFallida(){
        Alert actualizacionFallida = new Alert(Alert.AlertType.ERROR);
        actualizacionFallida.setTitle(ERROR);
        actualizacionFallida.setHeaderText(null);
        actualizacionFallida.setContentText(ALERTA_ACTUALIZACION_FALLIDA);
    }
    
    public static void mostrarAlertaEliminacionFallida(){
        Alert eliminacionFallida = new Alert (Alert.AlertType.ERROR);
        eliminacionFallida.setTitle(ERROR);
        eliminacionFallida.setHeaderText(ERROR);
        eliminacionFallida.setContentText(ALERTA_ELIMINACION_FALLIDA);
    }
    
    public static boolean mostrarAlertaCancelarGuardado(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(CONFIRMACION);
        confirmacion.setHeaderText(ALERTA_HEADER_CANCELACION);
        confirmacion.setContentText(ALERTA_INFO_CANCELACION);
        confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
    
    public static boolean mostrarAlertaEliminar(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(CONFIRMACION);
        confirmacion.setHeaderText(ALERTA_CONFIRMAR_ELIMINAR);
        confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
    
    public static void mostrarAlertaErrorVentana(){
        Alert errorVentana = new Alert (Alert.AlertType.ERROR);
        errorVentana.setTitle(ERROR);
        errorVentana.setHeaderText(null);
        errorVentana.setContentText(ALERTA_ERROR_VENTANA);
        errorVentana.showAndWait();
    }
    
    public static void mostrarAlertaBaseDatos(){
        Alert errorBaseDatos = new Alert(Alert.AlertType.ERROR);
        errorBaseDatos.setTitle(ERROR);
        errorBaseDatos.setHeaderText(null);
        errorBaseDatos.setContentText(ALERTA_ERROR_BD);
        errorBaseDatos.showAndWait();
    }
    
    public static void mostrarAlertaErrorCargarInformacion(){
        Alert errorCargaInfo = new Alert (Alert.AlertType.ERROR);
        errorCargaInfo.setTitle(ERROR);
        errorCargaInfo.setHeaderText(null);
        errorCargaInfo.setContentText(ALERTA_ERROR_CARGAR_INFORMACION);
        errorCargaInfo.showAndWait();
    }
}
