package com.example.myapplication.entrenamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntrenamientoContent {
    /**
     * An array of sample (Entrenamiento) items.
     */
    public static final List<EntrenamientoItem> ITEMS = new ArrayList<EntrenamientoItem>();

    /**
     * A map of sample (Entrenamiento) items, by ID.
     */
    public static final Map<String, EntrenamientoItem> ITEM_MAP = new HashMap<String, EntrenamientoItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createEntrenamientoItem(i));
        }
    }

    //Todo función para probar añadir desde fuera de la clase...
    public static void añadirItem(String id, String content, String details){
        addItem(createEntrenamientoItem(Integer.parseInt(id)));
    }

    private static void addItem(EntrenamientoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static EntrenamientoItem createEntrenamientoItem(int position) {
        return new EntrenamientoItem(String.valueOf(position), "Ejercicio " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * Entrenamiento item representing a piece of content.
     */
    public static class EntrenamientoItem {
        public final String id;
        public final String content;
        final String details;
        final String imagen_url;

        public EntrenamientoItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.imagen_url = "https://t2.uc.ltmcdn.com/images/0/5/2/img_como_ensenar_a_un_perro_a_dar_la_pata_22250_600.jpg";

        }

        public String getContent() {
            return content;
        }

        public String getDetails() {
            return details;
        }

        public String getImagen_url() {
            return imagen_url;
        }
    }
}

