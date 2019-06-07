package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface PetService {

    void list(String ownerEmail, DisposableObserver<List<Pet>> callback);

    void get(String id, String ownerEmail, DisposableObserver<Pet> callback);

    void modify(Pet pet, DisposableObserver<String> callback);

    void create(Pet pet, DisposableObserver<String> callback);

    void delete(String email, String petName, DisposableObserver<String> callback);

    void deleteOwner(String id, String mail, DisposableObserver<String> callback);

    void addOwner(String petId, String email, DisposableObserver<String> callback);
}
