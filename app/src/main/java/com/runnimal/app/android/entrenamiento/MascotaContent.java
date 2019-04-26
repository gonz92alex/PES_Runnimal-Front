package com.runnimal.app.android.entrenamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MascotaContent {
    /**
     * An array of sample (Mascota) items.
     */
    public static final List<MascotaItem> ITEMS = new ArrayList<MascotaItem>();

    /**
     * A map of sample (Mascota) items, by ID.
     */
    public static final Map<String, MascotaItem> ITEM_MAP = new HashMap<String, MascotaItem>();


    public static void añadirItem(String id, String name, String details, String size, String birthdate, String weight, String breed, String owner, String ownerAlias){
        if (!ITEM_MAP.containsKey(id))
            addItem(createMascotaItem(id, name, details,size,birthdate,weight,breed,owner, ownerAlias));
    }

    private static void addItem(MascotaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MascotaItem createMascotaItem(String id, String nombre, String details, String size, String birthdate, String weight, String breed, String owner, String ownerAlias) {
        return new MascotaItem(id, nombre, details, size, birthdate, weight, breed,owner, ownerAlias);
    }

    public static List<MascotaItem> belongsTo(String email) {
        List<MascotaItem> r = new ArrayList<MascotaItem>();
        for (int i = 0; i<ITEMS.size(); i++)
            if (ITEMS.get(i).getOwner().equals(email)) r.add(ITEMS.get(i));
        return r;
    }


    /**
     * Entrenamiento item representing a piece of name.
     */
    public static class MascotaItem {
        public final String id;
        public final String name;
        String details;
        String weight;
        //ToDO actualmente guardamos la edad
        String birthdate;
        String size;
        String breed;
        final String owner;
        final String ownerAlias;
        final String imagen_url;


        public MascotaItem(String id, String content, String details, String size, String birthdate, String weight, String breed, String owner, String ownerAlias) {
            this.id = id;
            this.name = content;
            this.details = details;
            this.size = size;
            this.birthdate = birthdate;
            this.weight = weight;
            this.breed = breed;
            this.owner = owner;
            this.ownerAlias = ownerAlias;
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

        public String getWeight() {
            return weight;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public String getSize() {
            return size;
        }

        public String getBreed() {
            return breed;
        }

        public String getOwner() {
            return owner;
        }

        public String getImagen_url() {
            return imagen_url;
        }

        public String getOwnerAlias() {
            return ownerAlias;
        }

    }
}

