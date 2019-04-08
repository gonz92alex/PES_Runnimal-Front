package com.runnimal.app.android.entrenamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MascotaContent {
    /**
     * An array of sample (Entrenamiento) items.
     */
    public static final List<MascotaItem> ITEMS = new ArrayList<MascotaItem>();

    /**
     * A map of sample (Entrenamiento) items, by ID.
     */
    public static final Map<String, MascotaItem> ITEM_MAP = new HashMap<String, MascotaItem>();


    //Todo función para probar añadir desde fuera de la clase...
    public static void añadirItem(String id, String name, String details){
        addItem(createMascotaItem(id, name, details));
    }

    private static void addItem(MascotaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MascotaItem createMascotaItem(String id, String nombre, String details) {
        return new MascotaItem(id, nombre, details);
    }


    /**
     * Entrenamiento item representing a piece of name.
     */
    public static class MascotaItem {
        public final String id;
        public final String name;
        final String details;
        final String imagen_url;


        public MascotaItem(String id, String content, String details) {
            this.id = id;
            this.name = content;
            this.details = details;
            this.imagen_url = "https://estaticos.muymascotas.es/media/cache/1140x_thumb/uploads/images/gallery/5ac493345cafe80cf43c986d/comida-perros-parrafo.jpg";
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDetails() {
            return details;
        }

        public String getImagen_url() {
            return imagen_url;
        }

    }
}

