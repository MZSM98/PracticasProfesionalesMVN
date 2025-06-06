
package grafica.utils;

import javafx.scene.control.Alert;

public class AlertaUtil {
    
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
}
