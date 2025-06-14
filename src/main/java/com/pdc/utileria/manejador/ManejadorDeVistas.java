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
        LOGIN("/vistas/login.fxml", false),
        MENU("/vistas/menu.fxml", false),
        CLIENTE("/vistas/cliente.fxml", true),
        CUENTA("/vistas/cuenta.fxml", true),
        EMPLEADO("/vistas/empleado.fxml", true),
        SUCURSAL("/vistas/sucursal.fxml", true),
        TRANSACCION("/vistas/transaccion.fxml", true),
        TRANSACCION_REGISTRO("/vistas/transaccionregistro.fxml",false),
        CLIENTE_REGISTRO("/vistas/clienteregistro.fxml", false),
        EMPLEADO_REGISTRO("/vistas/empleadoregistro.fxml", false),
        SUCURSAL_REGISTRO("/vistas/sucursalregistro.fxml", false),
        SUCURSAL_EMPLEADOS("/vistas/sucursalempleado.fxml", false),
        SUCURSAL_CUENTAS("/vistas/sucursalcuenta.fxml", false),
        CUENTA_REGISTRO("/vistas/cuentaregistro.fxml", true);

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
        cambiarVista(vista, "EuroBank - " + vista.name());
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