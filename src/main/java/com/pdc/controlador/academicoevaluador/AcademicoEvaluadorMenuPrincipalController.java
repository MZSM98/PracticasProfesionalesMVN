package com.pdc.controlador.academicoevaluador;

import com.pdc.utileria.manejador.ManejadorDeVistas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AcademicoEvaluadorMenuPrincipalController implements Initializable {
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    void abrirRegistroEvaluacionParcial(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL);
    }

    @FXML
    void abrirListaEvaluaciones(ActionEvent event) {
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.ACADEMICO_EVALUADOR_CONSULTA_EVALUACION);
    }
    @FXML
    private void cerrarSesion() {
        ManejadorDeVistas.getInstancia().limpiarCache();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }
    
}
