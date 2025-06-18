package com.pdc.modelo.dto;

public class ProyectoAsignadoDTO {
    
    private Integer idProyectoAsignado;
    private ProyectoDTO proyecto;
    private EstudianteDTO estudiante;

    public Integer getIdProyectoAsignado() {
        return idProyectoAsignado;
    }

    public void setIdProyectoAsignado(Integer idProyectoAsignado) {
        this.idProyectoAsignado = idProyectoAsignado;
    }

    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }

    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }
       
}
