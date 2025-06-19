package com.pdc.dao.interfaz;

import com.pdc.modelo.dto.ProfesorEEDTO;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public interface IProfesorExperienciaEducativaDAO {
    
    public boolean insertarProfesorEE(ProfesorEEDTO profesor) throws SQLException, IOException;
    public boolean eliminarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    public boolean editarProfesorEE(ProfesorEEDTO profesor) throws SQLException, IOException;
    public ProfesorEEDTO buscarProfesorEE(String numeroTrabajador) throws SQLException, IOException;
    public List<ProfesorEEDTO> listaProfesorEE() throws SQLException, IOException;
}