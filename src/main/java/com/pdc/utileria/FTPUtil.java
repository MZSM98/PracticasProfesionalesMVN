package com.pdc.utileria;

import java.io.*;
import java.net.ConnectException;
import java.util.Properties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class FTPUtil {

    private static final Logger LOG = LogManager.getLogger(FTPUtil.class);

    private static String host;
    private static int puerto;
    private static String usuario;
    private static String contrasena;
    private static boolean configuracionCargada = false;

    private static final String ARCHIVO_CONFIGURACION = "/serverfei.properties";
    private static final String RUTA_BASE_SERVIDOR = "/fei/pdc/";

    // Constructor privado para evitar instanciación
    private FTPUtil() {
    }

    /**
     * Método para cargar la configuración al inicio de la aplicación Debe ser
     * llamado una sola vez al iniciar la aplicación
     */
    public static void configurar() {
        if (configuracionCargada) {
            return;
        }

        Properties propiedades = new Properties();
        try (InputStream entrada = FTPUtil.class.getResourceAsStream(ARCHIVO_CONFIGURACION)) {
            if (entrada == null) {
                throw new FileNotFoundException("Archivo " + ARCHIVO_CONFIGURACION + " no encontrado en el classpath");
            }

            propiedades.load(entrada);

            host = propiedades.getProperty("ftp.host");
            puerto = Integer.parseInt(propiedades.getProperty("ftp.puerto", "21"));
            usuario = propiedades.getProperty("ftp.usuario");
            contrasena = propiedades.getProperty("ftp.contrasena");

            validarConfiguracion();
            configuracionCargada = true;

            LOG.info("Configuración FTP cargada: {}:{}"+ host+ puerto);

        } catch (IOException e) {
            LOG.error("Error al cargar la configuración FTP: " , e);
        } catch (NumberFormatException e) {
            LOG.error("Puerto FTP inválido en la configuración: " , e);
        }
    }

    /**
     * Valida que la configuración esté completa
     */
    private static void validarConfiguracion() {
        if (host == null || host.isEmpty()) {
            throw new IllegalArgumentException("Host FTP no configurado en serverfei.properties");
        }
        if (usuario == null || usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario FTP no configurado en serverfei.properties");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Contraseña FTP no configurada en serverfei.properties");
        }
    }

    /**
     * Verifica que la configuración esté cargada
     */
    private static void verificarConfiguracion() {
        if (!configuracionCargada) {
            throw new IllegalStateException("La configuración FTP no ha sido cargada. Llame a FTPUtil.configurar() primero.");
        }
    }

    /**
     * Establece conexión con el servidor FTP
     *
     * @return FTPClient conectado
     * @throws IOException si hay error en la conexión
     */
    private static FTPClient conectar() throws IOException {
        FTPClient ftpClient = new FTPClient();

        try {
            // Conectar al servidor
            ftpClient.connect(host, puerto);

            // Verificar respuesta de conexión
            int codigoRespuesta = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(codigoRespuesta)) {
                ftpClient.disconnect();
                throw new ConnectException("Error al conectar al servidor FTP. Código: " + codigoRespuesta);
            }

            // Hacer login
            boolean loginExitoso = ftpClient.login(usuario, contrasena);
            if (!loginExitoso) {
                ftpClient.disconnect();
                throw new IOException("Error de autenticación FTP. Credenciales inválidas.");
            }

            // Configurar modo de transferencia
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            // Cambiar a la ruta base del servidor
            ftpClient.changeWorkingDirectory(RUTA_BASE_SERVIDOR);

            return ftpClient;

        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            throw e;
        }
    }

    /**
     * Cierra la conexión FTP de forma segura
     *
     * @param ftpClient Cliente FTP a cerrar
     */
    private static void desconectar(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                LOG.error("Error al cerrar conexión FTP: {}", e);
            }
        }
    }

    /**
     * Carga un archivo local al servidor FTP La ruta de destino será relativa a
     * /fei/pdc/
     *
     * @param origen Ruta completa del archivo local a subir
     * @param destino Ruta relativa donde se guardará el archivo (a partir de
     * /fei/pdc/)
     * @return true si la carga fue exitosa, false en caso contrario
     */
    public static boolean cargarArchivo(String origen, String destino) {
        verificarConfiguracion();

        FTPClient ftpClient = null;
        FileInputStream archivoLocal = null;

        try {
            // Verificar que el archivo local existe
            File archivo = new File(origen);
            if (!archivo.exists()) {
                LOG.error("El archivo local no existe: {}"+ origen);
                return false;
            }

            if (!archivo.isFile()) {
                LOG.error("La ruta especificada no es un archivo: {}"+ origen);
                return false;
            }

            // Conectar al servidor FTP
            ftpClient = conectar();

            // Crear directorios remotos si no existen (relativo a la ruta base)
            crearDirectoriosRemotos(ftpClient, destino);

            // Abrir archivo local
            archivoLocal = new FileInputStream(archivo);

            // Subir archivo (destino es relativo a /fei/pdc/)
            boolean resultado = ftpClient.storeFile(destino, archivoLocal);

            if (resultado) {
                LOG.info("Archivo cargado: {} -> {}{}"+ origen+ RUTA_BASE_SERVIDOR+ destino);
            } else {
                LOG.error("Error al cargar el archivo: {}"+ ftpClient.getReplyString());
            }

            return resultado;

        } catch (IOException e) {
            LOG.error("Error al cargar archivo: {}", e);
            return false;
        } finally {
            // Cerrar recursos
            if (archivoLocal != null) {
                try {
                    archivoLocal.close();
                } catch (IOException e) {
                    LOG.error("Error al cerrar archivo local: {}", e);
                }
            }
            desconectar(ftpClient);
        }
    }

    /**
     * Descarga un archivo del servidor FTP al sistema local La ruta del archivo
     * será relativa a /fei/pdc/
     *
     * @param archivo Ruta relativa del archivo en el servidor FTP (a partir de
     * /fei/pdc/)
     * @param destinoLocal Ruta donde se guardará el archivo descargado
     * @return true si la descarga fue exitosa, false en caso contrario
     */
    public static boolean descargarArchivo(String archivo, String destinoLocal) {
        verificarConfiguracion();

        FTPClient ftpClient = null;
        FileOutputStream archivoDestino = null;

        try {
            // Conectar al servidor FTP
            ftpClient = conectar();

            // Crear directorios locales si no existen
            File archivoLocal = new File(destinoLocal);
            File directorioLocal = archivoLocal.getParentFile();
            if (directorioLocal != null && !directorioLocal.exists()) {
                directorioLocal.mkdirs();
            }

            // Abrir stream de salida local
            archivoDestino = new FileOutputStream(destinoLocal);

            // Descargar archivo (archivo es relativo a /fei/pdc/)
            boolean resultado = ftpClient.retrieveFile(archivo, archivoDestino);

            if (resultado) {
                LOG.info("Archivo descargado: {}{} -> {}"+ RUTA_BASE_SERVIDOR+ archivo+ destinoLocal);
            } else {
                LOG.error("Error al descargar el archivo: {}"+ ftpClient.getReplyString());
                // Eliminar archivo parcial si existe
                File archivoParcial = new File(destinoLocal);
                if (archivoParcial.exists()) {
                    archivoParcial.delete();
                }
            }

            return resultado;

        } catch (IOException e) {
            LOG.error("Error al descargar archivo: {}", e);
            return false;
        } finally {
            // Cerrar recursos
            if (archivoDestino != null) {
                try {
                    archivoDestino.close();
                } catch (IOException e) {
                    LOG.error("Error al cerrar archivo local: {}", e);
                }
            }
            desconectar(ftpClient);
        }
    }

    /**
     * Crea los directorios remotos necesarios para la ruta especificada
     *
     * @param ftpClient Cliente FTP conectado
     * @param rutaArchivo Ruta relativa del archivo (incluyendo nombre)
     */
    private static void crearDirectoriosRemotos(FTPClient ftpClient, String rutaArchivo) {
        try {
            // Obtener solo el directorio (sin el nombre del archivo)
            int ultimaBarra = rutaArchivo.lastIndexOf('/');
            if (ultimaBarra == -1) {
                return; // No hay directorio que crear
            }

            String directorio = rutaArchivo.substring(0, ultimaBarra);
            if (directorio.isEmpty()) {
                return;
            }

            // Dividir la ruta en directorios
            String[] directorios = directorio.split("/");
            String rutaActual = "";

            for (String dir : directorios) {
                if (!dir.isEmpty()) {
                    if (!rutaActual.isEmpty()) {
                        rutaActual += "/";
                    }
                    rutaActual += dir;

                    try {
                        ftpClient.makeDirectory(rutaActual);
                    } catch (IOException e) {
                        // El directorio probablemente ya existe, continuar
                    }
                }
            }
        } catch (Exception e) {
            LOG.warn("No se pudieron crear algunos directorios remotos: {}", e);
        }
    }

    /**
     * Obtiene información sobre la configuración actual
     *
     * @return String con información de la configuración
     */
    public static String obtenerInformacionConfiguracion() {
        if (!configuracionCargada) {
            return "Configuración FTP no cargada";
        }

        return String.format("FTP configurado - Servidor: %s:%d, Usuario: %s, Ruta base: %s",
                host, puerto, usuario, RUTA_BASE_SERVIDOR);
    }

}
