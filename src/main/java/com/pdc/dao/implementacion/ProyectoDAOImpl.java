package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.dao.interfaz.IPeriodoEscolarDAO;
import com.pdc.modelo.dto.ProyectoDTO;
import com.pdc.utileria.bd.ConexionBD;
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

    public static final String CRONOGRAMA_MES_UNO = "cronogramaMesUno";
    public static final String CRONOGRAMA_MES_DOS = "cronogramaMesDos";
    public static final String CRONOGRAMA_MES_TRES = "cronogramaMesTres";
    public static final String CRONOGRAMA_MES_CUATRO = "cronogramaMesCuatro";
    
    public ProyectoDAOImpl(){
        interfazPeriodoEscolarDAO = new PeriodoEscolarDAOImpl();
        interfazOrganizacionVinculadaDAO = new OrganizacionVinculadaDAOImpl();
        interfazResponsableOrganizacionVinculadaDAO = new ResponsableOrganizacionVinculadaDAOImpl();
    }

    @Override
    public boolean insertarProyecto(ProyectoDTO proyecto) throws SQLException {

        String insertarSQL = "INSERT INTO proyecto (titulo, idperiodoescolar, descripcion, rfcMoral, fechaInicio, fechaFinal, responsable, vacantes,"
                + "cronogramaMesUno, cronogramaMesDos, cronogramaMesTres, cronogramaMesCuatro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            declaracionPreparada.setString(9, proyecto.getCronogramaMesUno());
            declaracionPreparada.setString(10, proyecto.getCronogramaMesDos());
            declaracionPreparada.setString(11, proyecto.getCronogramaMesTres());
            declaracionPreparada.setString(12, proyecto.getCronogramaMesCuatro());
            declaracionPreparada.executeUpdate();
            insercionExitosa = true;
        } finally {

            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return insercionExitosa;
    }

    @Override
    public boolean eliminarProyecto(int proyectoID) throws SQLException {

        String eliminarSQL = "DELETE FROM proyecto WHERE proyectoID = ?";
        boolean eliminacionExitosa = false;

        try {

            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(eliminarSQL);
            declaracionPreparada.setInt(1, proyectoID);
            declaracionPreparada.executeUpdate();
            eliminacionExitosa = true;
        } finally {

            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return eliminacionExitosa;
    }

    @Override
    public boolean editarProyecto(ProyectoDTO proyecto) throws SQLException {

        String actualizarSQL = "UPDATE proyecto SET titulo = ?, idperiodoescolar = ?,"
                + " descripcion = ?, rfcMoral = ?, estadoProyecto = ?, fechaInicio = ?, fechaFinal = ?, responsable = ?, vacantes = ?,"
                + "cronogramaMesUno = ?, cronogramaMesDos = ?, cronogramaMesTres = ?, cronogramaMesCuatro = ? WHERE proyectoID = ?";
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
            declaracionPreparada.setString(10, proyecto.getCronogramaMesUno());
            declaracionPreparada.setString(11, proyecto.getCronogramaMesDos());
            declaracionPreparada.setString(12, proyecto.getCronogramaMesTres());
            declaracionPreparada.setString(13, proyecto.getCronogramaMesCuatro());
            declaracionPreparada.setInt(14, proyecto.getProyectoID());
            declaracionPreparada.executeUpdate();
            actualizacionExitosa = true;
        } finally {

            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return actualizacionExitosa;
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPorNombre(String titulo) throws SQLException {

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

            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyectosEncontrados;
    }

    @Override
    public List<ProyectoDTO> listarProyectos() throws SQLException {

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

            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return listaProyectos;
    }

    @Override
    public ProyectoDTO obtenerProyectoPorID(int proyectoID) throws SQLException {
        
        String consultaSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, estadoProyecto, fechaInicio, fechaFinal,"
                + " responsable, vacantes, cronogramaMesUno, cronogramaMesDos, cronogramaMesTres, cronogramaMesCuatro FROM proyecto WHERE proyectoID = ?";
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
                
                Integer idPeriodoEscolar;
                idPeriodoEscolar = resultadoDeOperacion.getInt(ID_PERIODO_ESCOLAR);
                PeriodoEscolarDTO periodoEscolar;
                periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
                proyecto.setPeriodoEscolar(periodoEscolar);
                
                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString(DESCRIPCION));
                
                String rfcMoral;
                rfcMoral = resultadoDeOperacion.getString(RFC_MORAL);
                OrganizacionVinculadaDTO organizacionVinculada;
                organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
                proyecto.setOrganizacion(organizacionVinculada);
                
                proyecto.setEstadoProyecto(resultadoDeOperacion.getString(ESTADO_PROYECTO));
                proyecto.setFechaInicio(resultadoDeOperacion.getDate(FECHA_INICIO));
                proyecto.setFechaFinal(resultadoDeOperacion.getDate(FECHA_FINAL));
                
                ResponsableOrganizacionVinculadaDTO responsable;
                responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoral);
                proyecto.setResponsable(responsable);
                
                proyecto.setVacantes(resultadoDeOperacion.getInt(VACANTES));
                proyecto.setCronogramaMesUno(resultadoDeOperacion.getString(CRONOGRAMA_MES_UNO));
                proyecto.setCronogramaMesDos(resultadoDeOperacion.getString(CRONOGRAMA_MES_DOS));
                proyecto.setCronogramaMesTres(resultadoDeOperacion.getString(CRONOGRAMA_MES_TRES));
                proyecto.setCronogramaMesCuatro(resultadoDeOperacion.getString(CRONOGRAMA_MES_CUATRO));
            }
        } finally {

            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyecto;
    }

    @Override
    public List<ProyectoDTO> listarProyectosPorOv(String rfcMoral) throws SQLException {

        String consultaSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, estadoProyecto, fechaInicio, fechaFinal,"
                + " responsable, vacantes, cronogramaMesUno, cronogramaMesDos, cronogramaMesTres, cronogramaMesCuatro FROM proyecto WHERE rfcMoral = ?";
        List<ProyectoDTO> listaProyectos = new ArrayList<>();

        try {

            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setString(1, rfcMoral);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {

                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setProyectoID(resultadoDeOperacion.getInt(PROYECTO_ID));
                proyecto.setTituloProyecto(resultadoDeOperacion.getString(TITULO));

                Integer idPeriodoEscolar = resultadoDeOperacion.getInt(ID_PERIODO_ESCOLAR);
                PeriodoEscolarDTO periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
                proyecto.setPeriodoEscolar(periodoEscolar);

                proyecto.setDescripcionProyecto(resultadoDeOperacion.getString(DESCRIPCION));

                String rfcMoralResult;
                rfcMoralResult= resultadoDeOperacion.getString(RFC_MORAL);
                OrganizacionVinculadaDTO organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoralResult);
                proyecto.setOrganizacion(organizacionVinculada);

                proyecto.setEstadoProyecto(resultadoDeOperacion.getString(ESTADO_PROYECTO));
                proyecto.setFechaInicio(resultadoDeOperacion.getDate(FECHA_INICIO));
                proyecto.setFechaFinal(resultadoDeOperacion.getDate(FECHA_FINAL));

                ResponsableOrganizacionVinculadaDTO responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoralResult);
                proyecto.setResponsable(responsable);
                
                proyecto.setVacantes(resultadoDeOperacion.getInt(VACANTES));
                proyecto.setCronogramaMesUno(resultadoDeOperacion.getString(CRONOGRAMA_MES_UNO));
                proyecto.setCronogramaMesDos(resultadoDeOperacion.getString(CRONOGRAMA_MES_DOS));
                proyecto.setCronogramaMesTres(resultadoDeOperacion.getString(CRONOGRAMA_MES_TRES));
                proyecto.setCronogramaMesCuatro(resultadoDeOperacion.getString(CRONOGRAMA_MES_CUATRO));

                listaProyectos.add(proyecto);
            }
        } finally {

            if (resultadoDeOperacion != null) {
                resultadoDeOperacion.close();
            }
            if (declaracionPreparada != null) {
                declaracionPreparada.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return listaProyectos;
    }

    @Override
    public List<ProyectoDTO> listarProyectosConVacantesDisponibles() throws SQLException {

        String consultaSQL = "SELECT proyectoID, titulo, idperiodoescolar, descripcion, rfcMoral, "
                + "estadoProyecto, fechaInicio, fechaFinal, responsable, vacantes, cronogramaMesUno, cronogramaMesDos, cronogramaMesTres, cronogramaMesCuatro  "
                + "FROM proyecto p "
                + "WHERE p.vacantes > (SELECT COUNT(*) FROM proyectoasignado pa WHERE pa.idproyecto = p.proyectoID)";

        List<ProyectoDTO> proyectosConVacantes = new ArrayList<>();

        try {
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            while (resultadoDeOperacion.next()) {
                ProyectoDTO proyecto = construirProyectoDesdeResultSet(resultadoDeOperacion);
                proyectosConVacantes.add(proyecto);
            }
        } finally {
            cerrarRecursos();
        }
        return proyectosConVacantes;
    }

    private ProyectoDTO construirProyectoDesdeResultSet(ResultSet rs) throws SQLException {
        ProyectoDTO proyecto = new ProyectoDTO();
        proyecto.setProyectoID(rs.getInt(PROYECTO_ID));
        proyecto.setTituloProyecto(rs.getString(TITULO));

        Integer idPeriodoEscolar = rs.getInt(ID_PERIODO_ESCOLAR);
        PeriodoEscolarDTO periodoEscolar = interfazPeriodoEscolarDAO.buscarPeriodoEscolar(idPeriodoEscolar);
        proyecto.setPeriodoEscolar(periodoEscolar);

        proyecto.setDescripcionProyecto(rs.getString(DESCRIPCION));

        String rfcMoral = rs.getString(RFC_MORAL);
        OrganizacionVinculadaDTO organizacionVinculada = interfazOrganizacionVinculadaDAO.buscarOrganizacionVinculada(rfcMoral);
        proyecto.setOrganizacion(organizacionVinculada);

        proyecto.setEstadoProyecto(rs.getString(ESTADO_PROYECTO));
        proyecto.setFechaInicio(rs.getDate(FECHA_INICIO));
        proyecto.setFechaFinal(rs.getDate(FECHA_FINAL));

        ResponsableOrganizacionVinculadaDTO responsable = interfazResponsableOrganizacionVinculadaDAO.buscarResponsableOV(rfcMoral);
        proyecto.setResponsable(responsable);

        proyecto.setVacantes(rs.getInt(VACANTES));
        proyecto.setCronogramaMesUno(rs.getString(CRONOGRAMA_MES_UNO));
        proyecto.setCronogramaMesDos(rs.getString(CRONOGRAMA_MES_DOS));
        proyecto.setCronogramaMesTres(rs.getString(CRONOGRAMA_MES_TRES));
        proyecto.setCronogramaMesCuatro(rs.getString(CRONOGRAMA_MES_CUATRO));

        return proyecto;
    }
    
    @Override
    public int contarProyectos() throws SQLException {
    
        final String contarSQL = "SELECT COUNT(*) FROM proyecto";
        int totalProyectos = 0;

        try (Connection conexion = new ConexionBD().getConexionBaseDatos();
             PreparedStatement declaracion = conexion.prepareStatement(contarSQL);
             ResultSet resultado = declaracion.executeQuery()) {

            if (resultado.next()) {
                totalProyectos = resultado.getInt(1);
            }
        }
    
        return totalProyectos;
    }

    private void cerrarRecursos() throws SQLException {
        if (resultadoDeOperacion != null) {
            resultadoDeOperacion.close();
        }
        if (declaracionPreparada != null) {
            declaracionPreparada.close();
        }
        if (conexionBD != null) {
            conexionBD.close();
        }
    }
}
