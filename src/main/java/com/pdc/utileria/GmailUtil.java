package com.pdc.utileria;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GmailUtil {
    private static final Logger LOG = LogManager.getLogger(GmailUtil.class);
    
    private static final String RUTA_CONFIGURACION = "/configuracion/configuracionGmail.properties";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "gmail.usuario";
    private static final String SMTP_PASS = "gmail.contrasena";
    private static final Properties propiedades = new Properties();

    private static String username;
    private static String password;
    private static Session session;
    
    private GmailUtil(){
        throw new IllegalStateException("Clase de utileria");
    }
    
    public static void configurarCorreo() {
        try {
            propiedades.load(GmailUtil.class.getResourceAsStream(RUTA_CONFIGURACION));
        } catch (IOException e) {
            LOG.error(e);
        }
       
       Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        String usuario = propiedades.getProperty(SMTP_USER);
        String contrasena = propiedades.getProperty(SMTP_PASS);
        username = Base64Util.decrypt(usuario);
        password = Base64Util.decrypt(contrasena);
        
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
      
    private static MimeMessage crearEmailHtml(String to, String from, String subject, String htmlBody, Session session) throws MessagingException {
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setContent(htmlBody, "text/html; charset=utf-8");
        return email;
    }
    
    public static void enviarCorreoHTML(String destinatario, String asunto, String nombre, String mensaje){
        String cuerpo = generarCuerpoHTML(nombre, mensaje);
        try {
            MimeMessage mimeMessage = crearEmailHtml(destinatario, username, asunto, cuerpo , session);
            Transport.send(mimeMessage);
        } catch (MessagingException ex) {
            LOG.error(ex);
        }
        LOG.info("Mensaje HTML enviado a: " + destinatario);
    }
    
    private static String generarCuerpoHTML(String nombreUsuario, String contenidoMensaje) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>Prácticas Profesionales</title>");
        html.append("</head>");
        html.append("<body style='font-family: Arial, sans-serif; font-size: 14px; line-height: 1.6; color: #333; margin: 0; padding: 20px;'>");
        
        html.append("<h1 style='font-family: Arial, sans-serif; font-size: 14px; font-weight: bold; margin: 0 0 20px 0; color: #333;'>");
        html.append("Estimado ").append(nombreUsuario);
        html.append("</h1>");
        
        html.append("<br><br>");
        
        html.append("<div style='font-family: Arial, sans-serif; font-size: 14px; margin-bottom: 40px; color: #333;'>");

        String contenidoFormateado = contenidoMensaje.replace("\n", "<br>");
        html.append(contenidoFormateado);
        html.append("</div>");
        
        html.append("<div style='font-family: Arial, sans-serif; font-size: 14px; margin-top: 40px; color: #333;'>");
        html.append("Sistema de gestión de prácticas profesionales.");
        html.append("</div>");
        
        html.append("</body>");
        html.append("</html>");
        
        return html.toString();
    }
    
}