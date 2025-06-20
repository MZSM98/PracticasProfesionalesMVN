package com.pdc.aplicacion;

import com.pdc.utileria.manejador.ManejadorDeVistas;
import javafx.application.Application;
import javafx.stage.Stage;

public class PracticasProfesionales extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ManejadorDeVistas.getInstancia().setEscenarioPrincipal(stage);
        ManejadorDeVistas.getInstancia().configurarCierreConConfirmacion();
        ManejadorDeVistas.getInstancia().cambiarVista(ManejadorDeVistas.Vista.INICIO_SESION);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}