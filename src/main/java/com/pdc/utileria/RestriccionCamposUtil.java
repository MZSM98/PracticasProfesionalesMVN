package com.pdc.utileria;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.PasswordField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class RestriccionCamposUtil {
    
    private RestriccionCamposUtil() {
        
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void aplicarRestriccionLongitud(TextField textField, int maxLength) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && newValue.length() > maxLength) {
                    
                    textField.setText(oldValue);
                }
            }
        });
    }
    
    public static void aplicarRestriccionLongitud(PasswordField passwordField, int maxLength) {
        
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && newValue.length() > maxLength) {
                    
                    passwordField.setText(oldValue);
                }
            }
        });
    }
    
    public static void aplicarRestriccionLongitud(TextArea textArea, int maxLength) {
        
        textArea.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && newValue.length() > maxLength) {
                    
                    textArea.setText(oldValue);
                }
            }
        });
    }
    
    public static void aplicarRestriccionSoloNumeros(TextField textField) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && !newValue.matches("\\d*")) {
                    
                    textField.setText(oldValue);
                }
            }
        });
    }
 
    public static void aplicarRestriccionAlfanumerica(TextField textField) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && !newValue.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]*")) {
                    
                    textField.setText(oldValue);
                }
            }
        });
    }
    
    public static void aplicarRestriccionNumerosConLongitud(TextField textField, int maxLength) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && (!newValue.matches("\\d*") || newValue.length() > maxLength)) {
                        
                    textField.setText(oldValue);
                }
            }
        });
    }
    
    public static void aplicarRestriccionLetrasConLongitud(TextField textField, int maxLength) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null &&(!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") || newValue.length() > maxLength)) {
                    
                    textField.setText(oldValue);
                }
            }
        });
    }

    public static void aplicarRestriccionAlfanumericaConLongitud(TextField textField, int maxLength) {
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null && (!newValue.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]*") || newValue.length() > maxLength)) {
                    
                    textField.setText(oldValue);
                }
            }
        });
    }
}