package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProfesorExperienciaEducativaDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IProfesorExperienciaEducativaDAO {
    
    public boolean insertarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException, IOException;
    public boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    public boolean editarProfesorEE(ProfesorExperienciaEducativaDTO profesor) throws SQLException, IOException;
    public ProfesorExperienciaEducativaDTO buscarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    public List<ProfesorExperienciaEducativaDTO> listaProfesorEE() throws SQLException, IOException;
}