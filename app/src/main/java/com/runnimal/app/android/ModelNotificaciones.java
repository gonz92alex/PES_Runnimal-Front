package com.runnimal.app.android;

import android.widget.Button;

public class ModelNotificaciones {
    String title;
    String mail;
    int icon;

    public ModelNotificaciones(String title, int icon, String mail) {
        this.title = title;
        this.icon = icon;
        this.mail = mail;
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
}
