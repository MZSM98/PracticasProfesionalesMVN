package com.pdc.utileria;

import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class POIUtil {
    
    private POIUtil() {
        throw new IllegalStateException("Clase utileria");
    }
    
    public static void reemplazarTextoEnDocumento(XWPFDocument document, String marcador, String valor) {
        reemplazarEnParrafos(document.getParagraphs(), marcador, valor);
        
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    reemplazarEnParrafos(cell.getParagraphs(), marcador, valor);
                }
            }
        }
        
        for (XWPFHeader header : document.getHeaderList()) {
            reemplazarEnParrafos(header.getParagraphs(), marcador, valor);
        }
        
        for (XWPFFooter footer : document.getFooterList()) {
            reemplazarEnParrafos(footer.getParagraphs(), marcador, valor);
        }
    }
    
    private static void reemplazarEnParrafos(List<XWPFParagraph> paragraphs, String marcador, String valor) {
        for (XWPFParagraph paragraph : paragraphs) {
            reemplazarEnParrafo(paragraph, marcador, valor);
        }
    }
    
    private static void reemplazarEnParrafo(XWPFParagraph paragraph, String marcador, String valor) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs.isEmpty()) {
            return;
        }
        
        StringBuilder textoCompleto = new StringBuilder();
        for (XWPFRun run : runs) {
            String texto = run.getText(0);
            if (texto != null) {
                textoCompleto.append(texto);
            }
        }
        
        String textoOriginal = textoCompleto.toString();
        if (!textoOriginal.contains(marcador)) {
            return;
        }
        
        String textoNuevo = textoOriginal.replace(marcador, valor);
        
        XWPFRun primerRun = runs.get(0);
        String fontFamily = primerRun.getFontFamily();
        int fontSize = primerRun.getFontSize();
        boolean isBold = primerRun.isBold();
        
        for (int i = runs.size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }
        
        XWPFRun nuevoRun = paragraph.createRun();
        nuevoRun.setText(textoNuevo);
        
        if (fontFamily != null) {
            nuevoRun.setFontFamily(fontFamily);
        }
        if (fontSize != -1) {
            nuevoRun.setFontSize(fontSize);
        }
        nuevoRun.setBold(isBold);
    }
    
    public static void reemplazarMarcadores(XWPFDocument document, Map<String, String> reemplazos) {
        for (Map.Entry<String, String> entry : reemplazos.entrySet()) {
            reemplazarTextoEnDocumento(document, entry.getKey(), entry.getValue());
        }
    }
}