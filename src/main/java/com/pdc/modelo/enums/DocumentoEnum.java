package com.pdc.modelo.enums;

public enum DocumentoEnum {
    SOLICITUD_PRACTICAS(1),
    REPORTE_PARCIAL(2),
    AUTOEVALUACION_ALUMNO(3),
    EVALUACION_PRESENTACION(4);
    
    private final int id;
    
    DocumentoEnum(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
