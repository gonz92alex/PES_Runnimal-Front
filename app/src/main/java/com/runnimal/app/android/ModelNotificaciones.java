package com.runnimal.app.android;

import android.widget.Button;

public class ModelNotificaciones {
    String title;
    String mail;
    int icon;
    String id;

    public ModelNotificaciones(String title, int icon, String mail, String id) {
        this.title = title;
        this.icon = icon;
        this.mail = mail;
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getMail() {
        return this.mail;
    }


    public String getId() {
        return this.id;
    }

}
