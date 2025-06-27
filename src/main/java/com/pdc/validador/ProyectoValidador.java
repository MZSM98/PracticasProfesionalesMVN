package com.pdc.validador;

import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;
import com.pdc.utileria.ConstantesUtil;
import java.sql.Date;

public class ProyectoValidador {
    
    private ProyectoValidador(){
        throw new UnsupportedOperationException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }
    
    public static void validarTituloProyecto(String tituloProyecto) {
        if (tituloProyecto == null || tituloProyecto.isEmpty()) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_TITULO_OBLIGATORIO);
        }
        if (!tituloProyecto.matches(ConstantesUtil.REGEX_SOLO_LETRAS)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_FORMATO_TITULO);
        }
        if (!tituloProyecto.matches(ConstantesUtil.REGEX_LONGITUD_NOMBRES)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_TITULO);
        }
    }
    
    public static void validarDescripcionProyecto(String descripcionProyecto) {
        if (descripcionProyecto == null || descripcionProyecto.isEmpty()) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_DESCRIPCION_OBLIGATORIA);
        }
        if (!descripcionProyecto.matches(ConstantesUtil.REGEX_LETRAS_Y_NUMEROS)) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_FORMATO_DESCRIPCION);
        }
        if (descripcionProyecto.length() > ConstantesUtil.LONGITUD_DESCRIPCIONES) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_LONGITUD_DESCRIPCION);
        }
    }
    
    public static void validarCampoCronograma(String cronograma, String nombreCampo) {
        if (cronograma == null || cronograma.isEmpty()) {
            throw new IllegalArgumentException("El campo de cronograma (" + nombreCampo + ") es obligatorio");
        }
        if (!cronograma.matches(ConstantesUtil.REGEX_LETRAS_Y_NUMEROS)) {
            throw new IllegalArgumentException("El cronograma (" + nombreCampo + ") solo puede contener letras y números");
        }
        if (cronograma.length() > ConstantesUtil.LONGITUD_DESCRIPCIONES) {
            throw new IllegalArgumentException("El cronograma (" + nombreCampo + ") supera la longitud máxima permitida");
        }
    }
    
    public static void validarVacantes(int vacantes) {
        if (vacantes < 1 || vacantes > 6) {
            throw new IllegalArgumentException("Las vacantes deben estar entre 1 y 6");
        }
    }
    
    public static void validarFechasProyecto(Date fechaInicio, Date fechaFinal) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        if (fechaFinal == null) {
            throw new IllegalArgumentException("La fecha final es obligatoria");
        }
        if (fechaInicio.after(fechaFinal)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final");
        }
    }
    
    public static void validarOrganizacionVinculada(OrganizacionVinculadaDTO organizacion) {
        if (organizacion == null) {
            throw new IllegalArgumentException(ConstantesUtil.ALERTA_SELECCION_ORGANIZACION_VINCULADA);
        }
    }
    
    public static void validarResponsable(ResponsableOrganizacionVinculadaDTO responsable) {
        if (responsable == null) {
            throw new IllegalArgumentException("Debe seleccionar un responsable para el proyecto");
        }
    }
    
    public static void validarPeriodoEscolar(PeriodoEscolarDTO periodoEscolar) {
        if (periodoEscolar == null) {
            throw new IllegalArgumentException("Debe seleccionar un periodo escolar");
        }
    }
    
    public static void validarProyecto(ProyectoDTO proyectoDTO) {
        validarTituloProyecto(proyectoDTO.getTituloProyecto());
        validarDescripcionProyecto(proyectoDTO.getDescripcionProyecto());
        validarCampoCronograma(proyectoDTO.getCronogramaMesUno(), "Mes 1");
        validarCampoCronograma(proyectoDTO.getCronogramaMesDos(), "Mes 2");
        validarCampoCronograma(proyectoDTO.getCronogramaMesTres(), "Mes 3");
        validarCampoCronograma(proyectoDTO.getCronogramaMesCuatro(), "Mes 4");
        validarVacantes(proyectoDTO.getVacantes());
        validarFechasProyecto(proyectoDTO.getFechaInicio(), proyectoDTO.getFechaFinal());
        validarOrganizacionVinculada(proyectoDTO.getOrganizacion());
        validarResponsable(proyectoDTO.getResponsable());
        validarPeriodoEscolar(proyectoDTO.getPeriodoEscolar());
    }
}
