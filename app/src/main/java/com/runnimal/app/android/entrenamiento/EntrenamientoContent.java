package com.runnimal.app.android.entrenamiento;

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


    //Todo función para probar añadir desde fuera de la clase...
    public static void añadirItem(String id, String content, String details){
        addItem(createEntrenamientoItem(id, content, details));
    }

    private static void addItem(EntrenamientoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static EntrenamientoItem createEntrenamientoItem(String id, String content, String details) {
        return new EntrenamientoItem(id, content, details);
    }

    //obtiene los pasos a realizar para el ejercicio indicado con el parametro id.
    public static ArrayList<String> getSteps(String id){
        if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getSteps();
        return null;
    }

    //guarda los pasos a realizar para el ejercicio indicado con el parametro id.(evitar llamar a la api si ya los tenemos)
    public static boolean setSteps(String id, ArrayList<String> steps){
        for (int i=0;i<ITEMS.size();i++){
            if (ITEMS.get(i).getId().equals(id)){
                ITEMS.get(i).setSteps(steps);
                return true;
            }
        }
        return false;
    }


    /**
     * Entrenamiento item representing a piece of content.
     */
    public static class EntrenamientoItem {
        public final String id;
        public final String content;
        final String details;
        final String imagen_url;
        ArrayList<String> steps = new ArrayList<>();


        public EntrenamientoItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.imagen_url = "https://t2.uc.ltmcdn.com/images/0/5/2/img_como_ensenar_a_un_perro_a_dar_la_pata_22250_600.jpg";

            steps.add("paso1 de test");
            steps.add("paso2 de test");
        }

        public String getId() {
            return id;
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

        public void setSteps(ArrayList<String> steps) {
            this.steps = steps;
        }

        public ArrayList<String> getSteps() {
            return steps;
        }

    }
}

