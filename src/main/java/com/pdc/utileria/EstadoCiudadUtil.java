package com.pdc.utileria;

import java.util.*;

public class EstadoCiudadUtil {
    
    private static final Map<String, List<String>> ESTADOS_CIUDADES = new LinkedHashMap<>();
   
    private EstadoCiudadUtil(){
        throw new IllegalStateException("Clase de utileria");
    }
    
    static {
        ESTADOS_CIUDADES.put("Aguascalientes", Arrays.asList(
            "Aguascalientes", "Calvillo", "Jesús María", "Pabellón de Arteaga", "Rincón de Romos", "San José de Gracia"
        ));
        
        ESTADOS_CIUDADES.put("Baja California", Arrays.asList(
            "Tijuana", "Mexicali", "Ensenada", "Tecate", "Playas de Rosarito"
        ));
        
        ESTADOS_CIUDADES.put("Baja California Sur", Arrays.asList(
            "La Paz", "Los Cabos", "Comondú", "Mulegé", "Loreto"
        ));
        
        ESTADOS_CIUDADES.put("Campeche", Arrays.asList(
            "Campeche", "Ciudad del Carmen", "Champotón", "Escárcega", "Calkiní", "Hopelchén"
        ));
        
        ESTADOS_CIUDADES.put("Chiapas", Arrays.asList(
            "Tuxtla Gutiérrez", "San Cristóbal de las Casas", "Tapachula", "Comitán", "Palenque", "Ocosingo"
        ));
        
        ESTADOS_CIUDADES.put("Chihuahua", Arrays.asList(
            "Chihuahua", "Ciudad Juárez", "Delicias", "Parral", "Nuevo Casas Grandes", "Cuauhtémoc"
        ));
        
        ESTADOS_CIUDADES.put("Ciudad de México", Arrays.asList(
            "Álvaro Obregón", "Azcapotzalco", "Benito Juárez", "Coyoacán", "Cuajimalpa", "Cuauhtémoc", 
            "Gustavo A. Madero", "Iztacalco", "Iztapalapa", "Magdalena Contreras", "Miguel Hidalgo", 
            "Milpa Alta", "Tláhuac", "Tlalpan", "Venustiano Carranza", "Xochimilco"
        ));
        
        ESTADOS_CIUDADES.put("Coahuila", Arrays.asList(
            "Saltillo", "Torreón", "Monclova", "Piedras Negras", "Acuña", "Sabinas"
        ));
        
        ESTADOS_CIUDADES.put("Colima", Arrays.asList(
            "Colima", "Manzanillo", "Tecomán", "Villa de Álvarez", "Armería", "Comala"
        ));
        
        ESTADOS_CIUDADES.put("Durango", Arrays.asList(
            "Durango", "Gómez Palacio", "Lerdo", "Santiago Papasquiaro", "Guadalupe Victoria", "Canatlán"
        ));
        
        ESTADOS_CIUDADES.put("Guanajuato", Arrays.asList(
            "León", "Irapuato", "Celaya", "Salamanca", "Guanajuato", "Dolores Hidalgo", "San Miguel de Allende"
        ));
        
        ESTADOS_CIUDADES.put("Guerrero", Arrays.asList(
            "Acapulco", "Chilpancingo", "Iguala", "Taxco", "Zihuatanejo", "Tlapa de Comonfort"
        ));
        
        ESTADOS_CIUDADES.put("Hidalgo", Arrays.asList(
            "Pachuca", "Tulancingo", "Tizayuca", "Ixmiquilpan", "Huejutla", "Actopan"
        ));
        
        ESTADOS_CIUDADES.put("Jalisco", Arrays.asList(
            "Guadalajara", "Zapopan", "Tlaquepaque", "Tonalá", "Puerto Vallarta", "Tlajomulco", "Tepatitlán"
        ));
        
        ESTADOS_CIUDADES.put("México", Arrays.asList(
            "Toluca", "Ecatepec", "Naucalpan", "Nezahualcóyotl", "Tlalnepantla", "Chimalhuacán", "Cuautitlán Izcalli"
        ));
        
        ESTADOS_CIUDADES.put("Michoacán", Arrays.asList(
            "Morelia", "Uruapan", "Lázaro Cárdenas", "Zamora", "Apatzingán", "Pátzcuaro"
        ));
        
        ESTADOS_CIUDADES.put("Morelos", Arrays.asList(
            "Cuernavaca", "Jiutepec", "Temixco", "Cuautla", "Yautepec", "Emiliano Zapata"
        ));
        
        ESTADOS_CIUDADES.put("Nayarit", Arrays.asList(
            "Tepic", "Bahía de Banderas", "Santiago Ixcuintla", "Compostela", "Ixtlán del Río", "Tuxpan"
        ));
        
        ESTADOS_CIUDADES.put("Nuevo León", Arrays.asList(
            "Monterrey", "Guadalupe", "San Nicolás de los Garza", "Apodaca", "Santa Catarina", "San Pedro Garza García"
        ));
        
        ESTADOS_CIUDADES.put("Oaxaca", Arrays.asList(
            "Oaxaca de Juárez", "Salina Cruz", "Tuxtepec", "Juchitán", "Huajuapan de León", "Puerto Escondido"
        ));
        
        ESTADOS_CIUDADES.put("Puebla", Arrays.asList(
            "Puebla", "Tehuacán", "San Martín Texmelucan", "Atlixco", "Cholula", "Huauchinango"
        ));
        
        ESTADOS_CIUDADES.put("Querétaro", Arrays.asList(
            "Querétaro", "San Juan del Río", "Corregidora", "El Marqués", "Tequisquiapan", "Cadereyta"
        ));
        
        ESTADOS_CIUDADES.put("Quintana Roo", Arrays.asList(
            "Cancún", "Chetumal", "Playa del Carmen", "Cozumel", "Tulum", "Felipe Carrillo Puerto"
        ));
        
        ESTADOS_CIUDADES.put("San Luis Potosí", Arrays.asList(
            "San Luis Potosí", "Soledad de Graciano Sánchez", "Ciudad Valles", "Matehuala", "Rioverde", "Tamazunchale"
        ));
        
        ESTADOS_CIUDADES.put("Sinaloa", Arrays.asList(
            "Culiacán", "Mazatlán", "Los Mochis", "Guasave", "Navolato", "El Fuerte"
        ));
        
        ESTADOS_CIUDADES.put("Sonora", Arrays.asList(
            "Hermosillo", "Ciudad Obregón", "Nogales", "San Luis Río Colorado", "Navojoa", "Guaymas"
        ));
        
        ESTADOS_CIUDADES.put("Tabasco", Arrays.asList(
            "Villahermosa", "Cárdenas", "Comalcalco", "Huimanguillo", "Macuspana", "Teapa"
        ));
        
        ESTADOS_CIUDADES.put("Tamaulipas", Arrays.asList(
            "Reynosa", "Matamoros", "Nuevo Laredo", "Tampico", "Ciudad Victoria", "Altamira"
        ));
        
        ESTADOS_CIUDADES.put("Tlaxcala", Arrays.asList(
            "Tlaxcala", "Apizaco", "Huamantla", "San Pablo del Monte", "Chiautempan", "Zacatelco"
        ));
        
        ESTADOS_CIUDADES.put("Veracruz", Arrays.asList(
            "Veracruz", "Xalapa", "Coatzacoalcos", "Córdoba", "Poza Rica", "Orizaba", "Minatitlán"
        ));
        
        ESTADOS_CIUDADES.put("Yucatán", Arrays.asList(
            "Mérida", "Kanasín", "Valladolid", "Progreso", "Tizimín", "Motul"
        ));
        
        ESTADOS_CIUDADES.put("Zacatecas", Arrays.asList(
            "Zacatecas", "Fresnillo", "Guadalupe", "Jerez", "Río Grande", "Sombrerete"
        ));
    }
    
    public static final List<String> obtenerTodosLosEstados() {
        return new ArrayList<>(ESTADOS_CIUDADES.keySet());
    }
    
    public static final List<String> obtenerCiudadesPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> ciudades = ESTADOS_CIUDADES.get(estado.trim());
        return ciudades != null ? new ArrayList<>(ciudades) : new ArrayList<>();
    }
    
}