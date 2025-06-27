package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import java.sql.SQLException;
import java.util.List;

public interface IProfesorExperienciaEducativaDAO {
    
    public boolean insertarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException;
    public boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException;
    public boolean editarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException;
    public ProfesorExperienciaEducativaDTO buscarProfesorEE(String numeroTrabajador) throws SQLException;
    public List<ProfesorExperienciaEducativaDTO> listaProfesorEE() throws SQLException;
    public int contarProfesores() throws SQLException;
}