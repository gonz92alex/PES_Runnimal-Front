package com.runnimal.app.android;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class SingletonSession {

    private static SingletonSession instance;
    private String username;
    private Bitmap photo;
    private String mail;
    private ArrayList<String> mascotas = new ArrayList<>();

    //no outer class can initialize this class's object
    private SingletonSession() {}

    public static SingletonSession Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new SingletonSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMascotas(ArrayList<String> mascotas) {
        this.mascotas = mascotas;
    }

    public ArrayList<String> getMascotas() {
        return mascotas;
    }
}

