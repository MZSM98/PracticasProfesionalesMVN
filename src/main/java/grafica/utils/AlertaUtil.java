
package grafica.utils;

import javafx.scene.control.Alert;

public class AlertaUtil {
    
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private AlertaUtil(){
        throw new IllegalAccessError("Clase de utileria...");
    }
    
}
