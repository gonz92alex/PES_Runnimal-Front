package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface PetsService {

    void list(DisposableObserver<List<Pet>> callback);

    void get(String id, DisposableObserver<Pet> callback);

    void modify(Pet pet, DisposableObserver<String> callback);
}
