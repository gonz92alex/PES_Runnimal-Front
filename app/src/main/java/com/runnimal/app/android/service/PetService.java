package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface PetService {

    void list(String ownerEmail, DisposableObserver<List<Pet>> callback);

    void get(String id, DisposableObserver<Pet> callback);

    void modify(Pet pet, DisposableObserver<String> callback);

    void create(Pet pet, DisposableObserver<String> callback);
}
