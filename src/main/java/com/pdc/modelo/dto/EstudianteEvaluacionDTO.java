package com.pdc.modelo.dto;

public class EstudianteEvaluacionDTO {
    
    private int idvaluacion;
    private String matricula;
    private String numeroDeTrabajador;
    private int proyectoID;
    private Integer criterioUno;
    private Integer criterioDos;
    private Integer criterioTres;
    private Integer criterioCuatro;

    public int getIdvaluacion() {
        return idvaluacion;
    }

    public void setIdvaluacion(int idvaluacion) {
        this.idvaluacion = idvaluacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroDeTrabajador() {
        return numeroDeTrabajador;
    }

    public void setNumeroDeTrabajador(String numeroDeTrabajador) {
        this.numeroDeTrabajador = numeroDeTrabajador;
    }

    public int getProyectoID() {
        return proyectoID;
    }

    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }

    public Integer getCriterioUno() {
        return criterioUno;
    }

    public void setCriterioUno(Integer criterioUno) {
        this.criterioUno = criterioUno;
    }

    public Integer getCriterioDos() {
        return criterioDos;
    }

    public void setCriterioDos(Integer criterioDos) {
        this.criterioDos = criterioDos;
    }

    public Integer getCriterioTres() {
        return criterioTres;
    }

    public void setCriterioTres(Integer criterioTres) {
        this.criterioTres = criterioTres;
    }

    public Integer getCriterioCuatro() {
        return criterioCuatro;
    }

    public void setCriterioCuatro(Integer criterioCuatro) {
        this.criterioCuatro = criterioCuatro;
    }
       

}