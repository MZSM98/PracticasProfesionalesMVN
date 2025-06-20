package com.pdc.modelo.dto;

public class DocumentoDTO {
    private Integer idDocumento;
    private String nombreDocumento;
    private String formatoNombre;
    private Integer limiteArchivos;
    private String plantilla;

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getFormatoNombre() {
        return formatoNombre;
    }

    public void setFormatoNombre(String formatoNombre) {
        this.formatoNombre = formatoNombre;
    }

    public Integer getLimiteArchivos() {
        return limiteArchivos;
    }

    public void setLimiteArchivos(Integer limiteArchivos) {
        this.limiteArchivos = limiteArchivos;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }
    
    
}
