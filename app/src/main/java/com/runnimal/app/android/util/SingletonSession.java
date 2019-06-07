package com.runnimal.app.android.util;

import java.net.URI;

public class SingletonSession {

    private static SingletonSession instance;
    private String username;
    private URI photo;
    private String mail;
    private String id;
    private String token;

    //no outer class can initialize this class's object
    private SingletonSession() {
    }

    public static SingletonSession Instance() {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null) {
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

    public URI getPhoto() {
        return photo;
    }

    public void setPhoto(URI photo) {
        this.photo = photo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setToken(String token){
        this.token=token;
    }

    public String getToken(){
        return token;
    }



}

