package com.pdc.utileria;

import java.util.Base64;

public class Base64Util {
    
    private Base64Util(){
        throw new IllegalStateException("Clase de utileria");
    }
    
    public static String encrypt(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }
    
    public static String decrypt(String encodedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        return new String(decodedBytes);
    }
    
}
