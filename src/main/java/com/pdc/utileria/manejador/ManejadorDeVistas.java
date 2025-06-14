package com.pdc.utileria.manejador;

import com.pdc.utileria.AlertaUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManejadorDeVistas {

    public enum Vista {
        INICIO_SESION("/com/pdc/vista/iniciodesesion/InicioDeSesion.fxml", false),
        
        PROFESOREE_MENU_PRINCIPAL("/com/pdc/vista/profesoree/ProfesorEEMenuPrincipal.fxml", false),
        PROFESOREE_CONSULTA_EVALUACION_PRESENTACION("/com/pdc/vista/profesoree/ProfesorEEConsultaEvaluacionPresentacion.fxml", false),
        PROFESOREE_REGISTRO_CALIFACION_FINAL("/com/pdc/vista/profesoree/ProfesorEERegistroCalificacionFinal.fxml", false),

        ESTUDIANTE_MENU_PRINCIPAL("/com/pdc/vista/estudiante/EstudianteMenuPrincipal.fxml", false),
        ESTUDIANTE_ACTUALIZACION_PERFIL("/com/pdc/vista/estudiante/EstudianteMenuPrincipal.fxml", false),
        ESTUDIANTE_CONSULTA_CRONOGRAMA("/com/pdc/vista/estudiante/EstudianteConsultaCronograma.fxml", false),
        ESTUDIANTE_CONSULTA_PROYECTO_ASIGNADO("/com/pdc/vista/estudiante/EstudianteConsultaProyectoAsignado.fxml", false),
        ESTUDIANTE_EVALUACION_PRESENTACION("/com/pdc/vista/estudiante/EstudianteEvaluacionPresentacion.fxml", false),
        ESTUDIANTE_GESTION("/com/pdc/vista/estudiante/EstudianteGestion.fxml", false),
        ESTUDIANTE_REGISTRO("/com/pdc/vista/estudiante/EstudianteRegistro.fxml", false),
        ESTUDIANTE_REGISTRO_AUTOEVALUACION("/com/pdc/vista/estudiante/EstudianteRegistroAutoevaluacion.fxml", false),
        ESTUDIANTE_REGISTRO_REPORTE_MENSUAL("/com/pdc/vista/estudiante/EstudianteRegistroReporteMensual.fxml", false),
        ESTUDIANTE_REGISTRO_SOLICITUD_PROYECTO("/com/pdc/vista/estudiante/EstudianteRegistroSolicitudProyecto.fxml", false),
        
        COORDINADOR_MENU_PRINCIPAL("/com/pdc/vista/coordinador/CoordinadorMenuPrincipal.fxml", false),
        COORDINADOR_GESTION_ACADEMICO("/com/pdc/vista/coordinador/CoordinadorGestionAcademico.fxml", false),
        COORDINADOR_GESTION_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorGestionOrganizacionVinculada.fxml", false),
        COORDINADOR_GESTION_PROYECTO("/com/pdc/vista/coordinador/CoordinadorGestionProyecto.fxml", false),
        COORDINADOR_REGISTRO_ACADEMICO("/com/pdc/vista/coordinador/CoordinadorRegistroAcademico.fxml", false),
        COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorRegistroOrganizacionVinculada.fxml", false),
        COORDINADOR_REGISTRO_PROYECTO("/com/pdc/vista/coordinador/CoordinadorRegistroProyecto.fxml", false),
        COORDINADOR_REGISTRO_REPRESENTANTE_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorRegistroRepresentanteOrganizacionVinculada.fxml", false),

        ACADEMICO_EVALUADOR_MENU_PRINCIPAL("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorMenuPrincipal.fxml", false),
        ACADEMICO_EVALUADOR_GESTION("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorConsultaListaEvaluacion.fxml", false),
        ACADEMICO_EVALUADOR_REGISTRO("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorRegistroEvaluacionParcial.fxml", false)
        ;

        private final String rutaFXML;
        private final boolean resizable;

        Vista(String rutaFXML, boolean resizable) {
            this.rutaFXML = rutaFXML;
            this.resizable = resizable;
        }

        public String getRutaFXML() {
            return rutaFXML;
        }

        public boolean isResizable() {
            return resizable;
        }
    }

    private static ManejadorDeVistas instancia;
    private Stage escenarioPrincipal;
    private Map<Vista, Parent> vistasCache;
    private Map<Vista, Object> controladoresCache;

    private ManejadorDeVistas() {
        this.vistasCache = new HashMap<>();
        this.controladoresCache = new HashMap<>();
    }

    public static ManejadorDeVistas getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorDeVistas();
        }
        return instancia;
    }

    public void setEscenarioPrincipal(Stage escenario) {
        this.escenarioPrincipal = escenario;
    }

    public Parent cargarVista(Vista vista) throws IOException {
        if (vistasCache.containsKey(vista)) {
            return vistasCache.get(vista);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista.getRutaFXML()));
            Parent root = loader.load();

            vistasCache.put(vista, root);
            controladoresCache.put(vista, loader.getController());

            return root;
        } catch (IOException e) {
            AlertaUtil.mostrarAlertaVentana();
            
            return null;
        }
    }

    public void cambiarVista(Vista vista) {
        cambiarVista(vista, "Gestor de prácticas profesionales - " + vista.name());
    }

    public void cambiarVista(Vista vista, String titulo) {
        try {
            Parent root = cargarVista(vista);
            Scene escena = new Scene(root);
            
            if (escenarioPrincipal != null) {
                
                escenarioPrincipal.hide();
                if (escenarioPrincipal.isMaximized()) {
                    escenarioPrincipal.setMaximized(false);
                }
                escenarioPrincipal.sizeToScene();
                // Se asignan valores para la siguiente ventana
                escenarioPrincipal.setScene(escena);
                escenarioPrincipal.setTitle(titulo);
                escenarioPrincipal.setResizable(vista.isResizable());
                
                // Centrar la ventana en la pantalla
                escenarioPrincipal.centerOnScreen();
                
                escenarioPrincipal.show();
            }
        } catch (IOException e) {
            AlertaUtil.mostrarAlertaVentana();
        }
    }

    // Obtener el controlador de una vista específica
    public <T> T obtenerControlador(Vista vista) throws IOException {
        // Si ya está en caché, devolver el controlador cacheado
        if (controladoresCache.containsKey(vista)) {
            return (T) controladoresCache.get(vista);
        }

        // Si no está en caché, cargar la vista (esto también guardará el controlador)
        cargarVista(vista);
        return (T) controladoresCache.get(vista);
    }

    // Limpiar caché de vistas
    public void limpiarCache() {
        vistasCache.clear();
        controladoresCache.clear();
    }

    // Limpiar caché de una vista específica
    public void limpiarCacheVista(Vista vista) {
        vistasCache.remove(vista);
        controladoresCache.remove(vista);
    }

    // Verificar si una vista está en caché
    public boolean estaEnCache(Vista vista) {
        return vistasCache.containsKey(vista);
    }

    // Cerrar la aplicación
    public void cerrarAplicacion() {
        if (escenarioPrincipal != null) {
            escenarioPrincipal.close();
        }
    }

    // Obtener el escenario principal
    public Stage obtenerEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
        // Abrir una vista en una nueva ventana
    public Stage abrirVistaEnNuevaVentana(Vista vista) {
        try {
            Parent root = cargarVista(vista);
            Scene escena = new Scene(root);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(escena);
            nuevaVentana.setTitle("EuroBank - " + vista.name());
            nuevaVentana.setResizable(vista.isResizable());
            nuevaVentana.showAndWait();

            return nuevaVentana;
        } catch (IOException ioe) {
            AlertaUtil.mostrarAlertaVentana();

            return null;
        }
    }

}