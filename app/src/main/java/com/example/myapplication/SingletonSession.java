package com.example.myapplication;

import android.graphics.Bitmap;

public class SingletonSession {

    private static SingletonSession instance;
    private String username;
    private Bitmap photo;
    private String mail;
    private String mascotas[];

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

    public void setMascotas(String[] mascotas) {
        this.mascotas = mascotas;
    }

    public String[] getMascotas() {
        return mascotas;
    }
}

