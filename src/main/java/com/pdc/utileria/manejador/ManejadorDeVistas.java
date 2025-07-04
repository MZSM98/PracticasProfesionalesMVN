package com.pdc.utileria.manejador;

import com.pdc.utileria.AlertaUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

public class ManejadorDeVistas {

    public enum Vista {
        INICIO_SESION("/com/pdc/vista/iniciodesesion/InicioDeSesion.fxml", false,"Inicio de sesión"),
        
        PROFESOREE_MENU_PRINCIPAL("/com/pdc/vista/profesoree/ProfesorEEMenuPrincipal.fxml", false," Menú principal"),
        PROFESOREE_CONSULTA_EVALUACION_PRESENTACION("/com/pdc/vista/profesoree/ProfesorEEConsultaEvaluacionPresentacion.fxml", false,"Consulta evaluación presentación"),
        PROFESOREE_REGISTRO_CALIFACION_FINAL("/com/pdc/vista/profesoree/ProfesorEERegistroCalificacionFinal.fxml", false,"Registro calificación final"),

        ESTUDIANTE_MENU_PRINCIPAL("/com/pdc/vista/estudiante/EstudianteMenuPrincipal.fxml", false,"Menú principal "),
        ESTUDIANTE_ACTUALIZACION_PERFIL("/com/pdc/vista/estudiante/EstudianteActualizacionPerfil.fxml", false,"Actualización de perfil"),
        ESTUDIANTE_CONSULTA_CRONOGRAMA("/com/pdc/vista/estudiante/EstudianteConsultaCronograma.fxml", false,"Consulta cronograma"),
        ESTUDIANTE_CONSULTA_PROYECTO_ASIGNADO("/com/pdc/vista/estudiante/EstudianteConsultaProyectoAsignado.fxml", false,"Consulta proyecto asignado"),
        ESTUDIANTE_GESTION("/com/pdc/vista/estudiante/EstudianteGestion.fxml", false,"Gestión de estudiantes"),
        ESTUDIANTE_REGISTRO("/com/pdc/vista/estudiante/EstudianteRegistro.fxml", false,"Registrar estudiante"),
        ESTUDIANTE_ACTUALIZAR("/com/pdc/vista/estudiante/EstudianteRegistro.fxml", false,"Actualizar estudiante"),
        ESTUDIANTE_REGISTRO_AUTOEVALUACION("/com/pdc/vista/estudiante/EstudianteRegistroAutoevaluacion.fxml", false,"Registro autoevaluación"),
        ESTUDIANTE_REGISTRO_REPORTE_MENSUAL("/com/pdc/vista/estudiante/EstudianteRegistroReporteMensual.fxml", false,"Registro reporte mensual"),
        ESTUDIANTE_REGISTRO_SOLICITUD_PROYECTO("/com/pdc/vista/estudiante/EstudianteRegistroSolicitudProyecto.fxml", false,"Registro solicitud proyecto"),
        ESTUDIANTE_GENERA_PLANTILLA_SOLICITUD("/com/pdc/vista/estudiante/EstudianteGeneraPlantillaSolicitudProyecto.fxml", false,"Generar plantilla solicitud"),


        COORDINADOR_MENU_PRINCIPAL("/com/pdc/vista/coordinador/CoordinadorMenuPrincipal.fxml", false,"Menú principal"),
        COORDINADOR_GESTION_ACADEMICO("/com/pdc/vista/coordinador/CoordinadorGestionAcademico.fxml", false,"Gestión de académicos"),
        COORDINADOR_GESTION_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorGestionOrganizacionVinculada.fxml", false,"Gestión organización vinculada"),
        COORDINADOR_CONSULTA_ESTADISTICA("/com/pdc/vista/coordinador/CoordiandorConsultaEstadistica.fxml", false,"Consulta estadísticas"),
        COORDINADOR_GESTION_PROYECTO("/com/pdc/vista/coordinador/CoordinadorGestionProyecto.fxml", false,"Gestión de proyectos"),
        COORDINADOR_GESTION_RESPONSABLE_ORGANIZACION ("/com/pdc/vista/coordinador/CoordinadorGestionResponsableOrganizacion.fxml",false,"Gestión responsable organización"),
        COORDINADOR_REGISTRO_ACADEMICO("/com/pdc/vista/coordinador/CoordinadorRegistroAcademico.fxml", false,"Registro académicos"),
        COORDINADOR_ACTUALIZA_ACADEMICO("/com/pdc/vista/coordinador/CoordinadorRegistroAcademico.fxml", false,"Actualización de académicos"),
        COORDINADOR_REGISTRO_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorRegistroOrganizacionVinculada.fxml", false,"Registro organización vinculada"),
        COORDINADOR_REGISTRO_PROYECTO("/com/pdc/vista/coordinador/CoordinadorRegistroProyecto.fxml", false,"Registro de proyecto"),
        COORDINADOR_PROYECTO_ASIGNADO("/com/pdc/vista/coordinador/CoordinadorProyectoAsignado.fxml", false,"Proyectos asignados"),
        COORDINADOR_ASIGNAR_PROYECTO("/com/pdc/vista/coordinador/CoordinadorAsignarProyecto.fxml", false,"Asignar proyecto"),
        COORDINADOR_REASIGNAR_PROYECTO("/com/pdc/vista/coordinador/CoordinadorReasignarProyecto.fxml", false,"Reasignar proyecto"),
        COORDINADOR_REGISTRO_RESPONSABLE_ORGANIZACION_VINCULADA("/com/pdc/vista/coordinador/CoordinadorRegistroResponsableOrganizacionVinculada.fxml", false,"Registro responsable organización"),

        ACADEMICO_EVALUADOR_MENU_PRINCIPAL("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorMenuPrincipal.fxml", false,"Menú principal"),
        ACADEMICO_EVALUADOR_CONSULTA_EVALUACION_PARCIAL("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorConsultaEstudiante.fxml", true,"Consulta evaluación parcial"),
        ACADEMICO_EVALUADOR_REGISTRO_EVALUACION_PARCIAL("/com/pdc/vista/academicoevaluador/AcademicoEvaluadorRegistroEvaluacionParcial.fxml", false,"Registro evaluación parcial");

        private final String rutaFXML;
        private final boolean resizable;
        private final String titulo;

        Vista(String rutaFXML, boolean resizable, String titulo) {
            this.rutaFXML = rutaFXML;
            this.resizable = resizable;
            this.titulo = titulo;
        }

        public String getRutaFXML() {
            return rutaFXML;
        }

        public boolean isResizable() {
            return resizable;
        }
        
        public String getTitulo(){
            return titulo;
        }
    }

    private static ManejadorDeVistas instancia;
    private Stage escenarioPrincipal;
    private final Map<Vista, Parent> vistasCache;
    private final Map<Vista, Object> controladoresCache;

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
            AlertaUtil.mostrarAlertaErrorVentana();
            
            return null;
        }
    }

    public void cambiarVista(Vista vista) {
        cambiarVista(vista, "Gestor de prácticas profesionales - " + vista.getTitulo());
    }

    public void cambiarVista(Vista vista, String titulo) {
        
        try {
            
            Parent root;
            root = cargarVista(vista);
            Scene escena;
            escena = new Scene(root);
            
            if (escenarioPrincipal != null) {
                
                escenarioPrincipal.hide();
                if (escenarioPrincipal.isMaximized()) {
                    
                    escenarioPrincipal.setMaximized(false);
                }
                escenarioPrincipal.sizeToScene();
                escenarioPrincipal.setScene(escena);
                escenarioPrincipal.setTitle(titulo);
                escenarioPrincipal.setResizable(vista.isResizable());
                escenarioPrincipal.centerOnScreen();
                escenarioPrincipal.show();
            }
        } catch (IOException e) {
            
            AlertaUtil.mostrarAlertaErrorVentana();
        }
    }

    public <T> T obtenerControlador(Vista vista) throws IOException {
        
        if (controladoresCache.containsKey(vista)) {
            
            return (T) controladoresCache.get(vista);
        }

        cargarVista(vista);
        return (T) controladoresCache.get(vista);
    }
    
    public void configurarCierreVentana(EventHandler<WindowEvent> manejadorCierre) {
        if (escenarioPrincipal != null) {
            escenarioPrincipal.setOnCloseRequest(manejadorCierre);
        }
    }
    
    public void configurarCierreConConfirmacion() {
        configurarCierreVentana(event -> {
            event.consume(); // Prevenir cierre automático

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar salida");
            alert.setHeaderText("¿Estás seguro de que deseas salir de la aplicación.");

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                escenarioPrincipal.close();
            }
        });
    }

    public void limpiarCache() {
        vistasCache.clear();
        controladoresCache.clear();
    }

    public void limpiarCacheVista(Vista vista) {
        vistasCache.remove(vista);
        controladoresCache.remove(vista);
    }

    public boolean estaEnCache(Vista vista) {
        return vistasCache.containsKey(vista);
    }

    public void cerrarAplicacion() {
        if (escenarioPrincipal != null) {
            escenarioPrincipal.close();
        }
    }

    public Stage obtenerEscenarioPrincipal() {
        return escenarioPrincipal;
    }
}