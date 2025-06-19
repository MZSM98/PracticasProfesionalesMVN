package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pdc.dao.interfaz.IProyectoDAO;
import com.pdc.dao.interfaz.IResponsableOrganizacionVinculadaDAO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import com.pdc.modelo.dto.PeriodoEscolarDTO;
import com.pdc.modelo.dto.ResponsableOrganizacionVinculadaDTO;

public class ProyectoDAOImpl implements IProyectoDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    private final IPeriodoEscolarDAO interfazPeriodoEscolarDAO;
    private final IOrganizacionVinculadaDAO interfazOrganizacionVinculadaDAO;
    private final IResponsableOrganizacionVinculadaDAO interfazResponsableOrganizacionVinculadaDAO;
    public static final String RESPONSABLE = "responsable";
    public static final String FECHA_INICIO = "fechaInicio";
    public static final String FECHA_FINAL = "fechaFinal";
    public static final String ESTADO_PROYECTO = "estadoProyecto";
    public static final String DESCRIPCION = "descripcion";
    public static final String RFC_MORAL = "rfcMoral";
    public static final String ID_PERIODO_ESCOLAR = "idperiodoescolar";
    public static final String PROYECTO_ID = "proyectoID";
    public static final String TITULO = "titulo";
    public static final String RFC = "rfc";
    public static final String VACANTES = "vacantes";
    
    public ProyectoDAOImpl(){
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazResponsableOrganizacionVinculadaDAO = new ResponsableOrganizacionVinculadaDAOImpl();
    }
    
    @Override
    public boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException, IOException {
        
        String insertarSQL = "INSERT INTO proyecto (titulo, idperiodoescolar, descripcion, rfcMoral, fechaInicio, fechaFinal, responsable, vacantes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean insercionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(insertarSQL);
            declaracionPreparada.setString(1, proyecto.getTituloProyecto());
            declaracionPreparada.setInt(2, proyecto.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setString(3, proyecto.getDescripcionProyecto());
            declaracionPreparada.setString(4, proyecto.getOrganizacion().getRfcMoral());
            declaracionPreparada.setDate(5, proyecto.getFechaInicio());
            declaracionPreparada.setDate(6, proyecto.getFechaFinal());
            declaracionPreparada.setString(7, proyecto.getResponsable().getRfc());
            declaracionPreparada.setInt(8, proyecto.getVacantes());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarProyecto(int proyectoID) throws SQLException, IOException {
        
        String eliminarSQL = "DELETE FROM proyecto WHERE proyectoID = ?";
        boolean eliminacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setInt(1, proyectoID);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarProyecto(ProyectoDTO proyecto) throws SQLException, IOException {
        
        String actualizarSQL = "UPDATE proyecto SET titulo = ?, idperiodoescolar = ?,"
                + " descripcion = ?, rfcMoral = ?, estadoProyecto = ?, fechaInicio = ?, fechaFinal = ?, responsable = ?, vacantes = ? WHERE proyectoID = ?";
        boolean actualizacionExitosa = false;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(actualizarSQL);
            declaracionPreparada.setString(1, proyecto.getTituloProyecto());
            declaracionPreparada.setInt(2, proyecto.getPeriodoEscolar().getIdPeriodoEscolar());
            declaracionPreparada.setString(3, proyecto.getDescripcionProyecto());
            declaracionPreparada.setString(4, proyecto.getOrganizacion().getRfcMoral());
            declaracionPreparada.setString(5, proyecto.getEstadoProyecto());
            declaracionPreparada.setDate(6, proyecto.getFechaInicio());
            declaracionPreparada.setDate(7, proyecto.getFechaFinal());
            declaracionPreparada.setString(8, proyecto.getResponsable().getRfc());
            declaracionPreparada.setInt(9, proyecto.getVacantes());
            declaracionPreparada.setInt(10, proyecto.getProyectoID());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {
            
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return actualizacionExitosa;
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException, IOException {
        
        String consultaSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, estadoProyecto, fechaInicio, fechaFinal,"
                + " responsable, vacantes FROM proyecto WHERE titulo LIKE ?";
        List<ProyectoDTO> proyectosEncontrados = new ArrayList<>();

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, "%" + titulo + "%");
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt(PROYECTO_ID));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString(TITULO));
                
                Integer idPeriodoEscolar = resultadoDeOperacion.getInt(ID_PERIODO_ESCOLAR);
                PeriodoEscolarDTO periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
                proyecto.setPeriodoEscolar(periodoEscolar);
                
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString(DESCRIPCION));
                
                String rfcMoral = resultadoDeOperacion.getString(RFC_MORAL);
                OrganizacionVinculadaDTO organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
                proyecto.setOrganizacion(organizacionVinculada);
                
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString(ESTADO_PROYECTO));
                proyecto.setFechaInicio(resultadoDeOperacion.getDate(FECHA_INICIO));
                proyecto.setFechaFinal(resultadoDeOperacion.getDate(FECHA_FINAL));
                
                ResponsableOrganizacionVinculadaDTO responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoral);
                proyecto.setResponsable(responsable);
                
                proyecto.setVacantes(resultadoDeOperacion.getInt(VACANTES));
                proyectosEncontrados.add(proyecto);
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return proyectosEncontrados;
    }
    
    @Override
    public List<ProyectoDTO> listarProyectos() throws SQLException, IOException {
        
        String consultaTodoSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, estadoProyecto, fechaInicio, fechaFinal, responsable, vacantes FROM proyecto";
        List<ProyectoDTO> listaProyectos = new ArrayList<>();

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaTodoSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                
                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt(PROYECTO_ID));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString(TITULO));
                
                Integer idPeriodoEscolar = resultadoDeOperacion.getInt(ID_PERIODO_ESCOLAR);
                PeriodoEscolarDTO periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
                proyecto.setPeriodoEscolar(periodoEscolar);
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString(DESCRIPCION));
                
                String rfcMoral = resultadoDeOperacion.getString(RFC_MORAL);
                OrganizacionVinculadaDTO organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
                proyecto.setOrganizacion(organizacionVinculada);
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString(ESTADO_PROYECTO));
                proyecto.setFechaInicio(resultadoDeOperacion.getDate(FECHA_INICIO));
                proyecto.setFechaFinal(resultadoDeOperacion.getDate(FECHA_FINAL));
                ResponsableOrganizacionVinculadaDTO responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoral);
                proyecto.setResponsable(responsable);
                proyecto.setVacantes(resultadoDeOperacion.getInt(VACANTES));
                
                listaProyectos.add(proyecto);
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return listaProyectos;
    }
    
    @Override
    public ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException, IOException {
        
        String consultaSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, estadoProyecto, fechaInicio, fechaFinal, responsable, vacantes FROM proyecto WHERE proyectoID = ?";
        ProyectoDTO proyecto = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, proyectoID);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt(PROYECTO_ID));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString(TITULO));
                Integer idPeriodoEscolar = resultadoDeOperacion.getInt(ID_PERIODO_ESCOLAR);
                PeriodoEscolarDTO periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
                proyecto.setPeriodoEscolar(periodoEscolar);
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString(DESCRIPCION));
                String rfcMoral = resultadoDeOperacion.getString(RFC_MORAL);
                OrganizacionVinculadaDTO organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
                proyecto.setOrganizacion(organizacionVinculada);
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString(ESTADO_PROYECTO));
                proyecto.setFechaInicio(resultadoDeOperacion.getDate(FECHA_INICIO));
                proyecto.setFechaFinal(resultadoDeOperacion.getDate(FECHA_FINAL));
                ResponsableOrganizacionVinculadaDTO responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoral);
                proyecto.setResponsable(responsable);
                proyecto.setVacantes(resultadoDeOperacion.getInt(VACANTES));
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return proyecto;
    }
}