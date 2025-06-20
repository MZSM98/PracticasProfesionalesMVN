
package com.pdc.modelo.dto;


public class EstudianteExperienciaEducativaDTO {
    
    private Integer idExperienciaAsignada;
    private EstudianteDTO estudiante;
    private ExperienciaEducativaDTO experienciaEducativa;

    public Integer getIdExperienciaAsignada() {
        return idExperienciaAsignada;
    }

    public void setIdExperienciaAsignada(Integer idExperienciaAsignada) {
        this.idExperienciaAsignada = idExperienciaAsignada;
    }

    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }

    public ExperienciaEducativaDTO getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativaDTO experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }
}
