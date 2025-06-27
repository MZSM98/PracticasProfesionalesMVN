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
    public static final String SEPARADOR = "/";
    private static String host;
    private static int puerto;
    private static String usuario;
    private static String contrasena;
    private static boolean configuracionCargada = false;

    private static final String ARCHIVO_CONFIGURACION = "/serverfei.properties";
    private static final String RUTA_BASE_SERVIDOR = "/fei/pdc/";

    private FTPUtil() {
        
        throw new IllegalStateException(ConstantesUtil.ALERTA_CLASE_UTILERIA);
    }

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

    private static void verificarConfiguracion() {
        
        if (!configuracionCargada) {
            
            throw new IllegalStateException("La configuración FTP no ha sido cargada");
        }
    }

    private static FTPClient conectar() throws IOException {
        
        FTPClient ftpClient = new FTPClient();

        try {

            ftpClient.connect(host, puerto);
            int codigoRespuesta = ftpClient.getReplyCode();
            
            if (!FTPReply.isPositiveCompletion(codigoRespuesta)) {
                
                ftpClient.disconnect();
                throw new ConnectException("Error al conectar al servidor FTP. Código: " + codigoRespuesta);
            }

            boolean loginExitoso;
            loginExitoso= ftpClient.login(usuario, contrasena);
            if (!loginExitoso) {
                
                ftpClient.disconnect();
                throw new IOException("Error de autenticación FTP. Credenciales inválidas.");
            }

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(RUTA_BASE_SERVIDOR);

            return ftpClient;

        } catch (IOException ioe) {
            
            if (ftpClient.isConnected()) {
                
                ftpClient.disconnect();
            }
            throw ioe;
        }
    }

    private static void desconectar(FTPClient ftpClient) {
        
        if (ftpClient != null && ftpClient.isConnected()) {
            
            try {
                
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ioe) {
                
                LOG.error("Error al cerrar conexión FTP: {}", ioe);
            }
        }
    }

    public static boolean cargarArchivo(String origen, String destino) {
        verificarConfiguracion();

        FTPClient ftpClient = null;
        FileInputStream archivoLocal = null;

        try {
            File archivo = new File(origen);
            if (!archivo.exists()) {
                LOG.error("El archivo local no existe: {}"+ origen);
                return false;
            }

            if (!archivo.isFile()) {
                LOG.error("La ruta especificada no es un archivo: {}"+ origen);
                return false;
            }

            ftpClient = conectar();

            crearDirectoriosRemotos(ftpClient, destino);

            archivoLocal = new FileInputStream(archivo);

            boolean resultado;
            resultado = ftpClient.storeFile(destino, archivoLocal);

            if (resultado) {
                LOG.info("Archivo cargado: {} -> {}{}"+ origen+ RUTA_BASE_SERVIDOR+ destino);
            } else {
                LOG.error("Error al cargar el archivo: {}"+ ftpClient.getReplyString());
            }

            return resultado;

        } catch (IOException ioe) {
            
            LOG.error("Error al cargar archivo: {}", ioe);
            return false;   
        } finally {
            
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

    public static boolean descargarArchivo(String archivo, String destinoLocal) {
        
        verificarConfiguracion();

        FTPClient ftpClient = null;
        FileOutputStream archivoDestino = null;

        try {
            
            ftpClient = conectar();

            File archivoLocal = new File(destinoLocal);
            File directorioLocal = archivoLocal.getParentFile();
            if (directorioLocal != null && !directorioLocal.exists()) {
                directorioLocal.mkdirs();
            }

            archivoDestino = new FileOutputStream(destinoLocal);

            boolean resultado = ftpClient.retrieveFile(archivo, archivoDestino);

            if (resultado) {
                LOG.info("Archivo descargado: {}{} -> {}"+ RUTA_BASE_SERVIDOR+ archivo+ destinoLocal);
            } else {
                LOG.error("Error al descargar el archivo: {}"+ ftpClient.getReplyString());
                File archivoParcial = new File(destinoLocal);
                if (archivoParcial.exists()) {
                    archivoParcial.delete();
                }
            }

            return resultado;

        } catch (IOException ioe) {
            LOG.error("Error al descargar archivo: {}", ioe);
            return false;
        } finally {
            if (archivoDestino != null) {
                try {
                    archivoDestino.close();
                } catch (IOException ioe) {
                    LOG.error("Error al cerrar archivo local: {}", ioe);
                }
            }
            desconectar(ftpClient);
        }
    }

    private static void crearDirectoriosRemotos(FTPClient ftpClient, String rutaArchivo) {
        
        try {
            
            int ultimaBarra = rutaArchivo.lastIndexOf('/');
            if (ultimaBarra == -1) {
                return;
            }

            String directorio = rutaArchivo.substring(0, ultimaBarra);
            if (directorio.isEmpty()) {
                return;
            }

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
    
    public static File descargarPlantillaTemp(String archivoRemoto) {
        verificarConfiguracion();

        try {
            
            String archivoTemporal = System.getProperty("java.io.tmpdir") + "/"
                    + archivoRemoto.substring(archivoRemoto.lastIndexOf('/') + 1);

            boolean descargado = descargarArchivo(archivoRemoto, archivoTemporal);

            return descargado ? new File(archivoTemporal) : null;

        } catch (Exception e) {
            
            LOG.error("Error al descargar plantilla: ", e);
            return null;
        }
    }

    public static String obtenerInformacionConfiguracion() {
        
        if (!configuracionCargada) {
            
            return "Configuración FTP no cargada";
        }

        return String.format("FTP configurado - Servidor: %s:%d, Usuario: %s, Ruta base: %s",
                host, puerto, usuario, RUTA_BASE_SERVIDOR);
    }

}
