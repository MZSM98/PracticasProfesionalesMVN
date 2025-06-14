package com.pdc.modelo.dto;

public class EstudianteDTO extends UsuarioDTO{
    
    private String matricula;
    private String nombreEstudiante;
    private PeriodoEscolarDTO periodoEscolar;
    private SeccionDTO seccionEstudiante;
    private Integer avanceCrediticio;
    private Double promedio;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public PeriodoEscolarDTO getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(PeriodoEscolarDTO periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public SeccionDTO getSeccionEstudiante() {
        return seccionEstudiante;
    }

    public void setSeccionEstudiante(SeccionDTO seccionEstudiante) {
        this.seccionEstudiante = seccionEstudiante;
    }

    public Integer getAvanceCrediticio() {
        return avanceCrediticio;
    }

    public void setAvanceCrediticio(Integer avanceCrediticio) {
        this.avanceCrediticio = avanceCrediticio;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }


    
}
