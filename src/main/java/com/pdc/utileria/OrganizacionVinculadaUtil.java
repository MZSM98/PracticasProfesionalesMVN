package com.pdc.utileria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrganizacionVinculadaUtil {

    private OrganizacionVinculadaUtil() {
        throw new IllegalStateException("Clase de utileria");
    }

    public static final ObservableList<String> obtenerSectores() {
        return FXCollections.observableArrayList("Público", "Privado", "Social");
    }
}
