
package com.pdc.utileria;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertaUtil {
    
    public static final String EXITO = "EXITO";
    public static final String ADVERTENCIA = "ADVERTENCIA";
    public static final String ERROR = "ERROR";
    public static final String INFORMACION = "INFORMACION";
    public static final String CONFIRMACION = "CONFIRMACION";
    public static final String ERROR_VENTANA = "No se pudo abrir la ventana, intente más tarde";

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
        exito.setContentText("Registro Exitoso");
        exito.showAndWait();
    }
    
    public static void mostrarAlertaRegistroFallido(){
        Alert fallido = new Alert(Alert.AlertType.ERROR);
        fallido.setTitle(ERROR);
        fallido.setHeaderText(null);
        fallido.setContentText("No se pudo realizar el registro, intente mas tarde");
        fallido.showAndWait();
    }
    
    public static void mostrarAlertaEliminacionFallida(){
        Alert eliminacionFallida = new Alert (Alert.AlertType.ERROR);
        eliminacionFallida.setTitle(ERROR);
        eliminacionFallida.setHeaderText(ERROR);
        eliminacionFallida.setContentText("No se pudo eliminar el registro, intente más tarde");
    }
    
    public static boolean mostrarAlertaCancelarGuardado(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(CONFIRMACION);
        confirmacion.setHeaderText("¿Está seguro que desea cancelar?");
        confirmacion.setContentText("Se perderán todos los datos ingresados.");
        confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
    
    public static boolean mostrarAlertaEliminar(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(CONFIRMACION);
        confirmacion.setHeaderText("¿Está seguro que desea eliminar este registro?");
        confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
    
    public static void mostrarAlertaVentana(){
        Alert errorVentana = new Alert (Alert.AlertType.ERROR);
        errorVentana.setTitle(ERROR);
        errorVentana.setHeaderText(null);
        errorVentana.setContentText(ERROR_VENTANA);
        errorVentana.showAndWait();
    }
}
