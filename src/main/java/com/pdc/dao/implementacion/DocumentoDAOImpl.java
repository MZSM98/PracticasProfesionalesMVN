package com.pdc.dao.implementacion;

import com.pdc.dao.interfaz.IDocumentoDAO;
import com.pdc.modelo.dto.DocumentoDTO;
import com.pdc.utileria.bd.ConexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DocumentoDAOImpl implements IDocumentoDAO {

    private Connection conexionBD;
    private PreparedStatement declaracionPreparada;
    private ResultSet resultadoDeOperacion;
    
    public static final String ID_DOCUMENTO = "iddocumento";
    public static final String NOMBRE_DOCUMENTO = "nombredocumento";
    public static final String FORMATO_NOMBRE = "formatonombre";
    public static final String LIMITE_ARCHIVOS = "limiteArchivos";
    public static final String PLANTILLA = "plantilla";
    
    public DocumentoDAOImpl(){
    }
    
    @Override
    public DocumentoDTO buscarDocumento(int idDocumento) throws SQLException, IOException {
        
        String consultaSQL = "SELECT iddocumento, nombredocumento, formatonombre, limiteArchivos, plantilla FROM documento WHERE iddocumento = ?";
        DocumentoDTO documento = null;

        try {
            
            conexionBD = new ConexionBD().getConexionBaseDatos();
            declaracionPreparada = conexionBD.prepareStatement(consultaSQL);
            declaracionPreparada.setInt(1, idDocumento);
            resultadoDeOperacion = declaracionPreparada.executeQuery();

            if (resultadoDeOperacion.next()) {
                
                documento = new DocumentoDTO();
                documento.setIdDocumento(resultadoDeOperacion.getInt(ID_DOCUMENTO));
                documento.setNombreDocumento(resultadoDeOperacion.getString(NOMBRE_DOCUMENTO));
                documento.setFormatoNombre(resultadoDeOperacion.getString(FORMATO_NOMBRE));
                documento.setLimiteArchivos(resultadoDeOperacion.getInt(LIMITE_ARCHIVOS));
                documento.setPlantilla(resultadoDeOperacion.getString(PLANTILLA));
            }
        } finally {
            
            if (resultadoDeOperacion != null) resultadoDeOperacion.close();
            if (declaracionPreparada != null) declaracionPreparada.close();
            if (conexionBD != null) conexionBD.close();
        }
        return documento;
    }
}