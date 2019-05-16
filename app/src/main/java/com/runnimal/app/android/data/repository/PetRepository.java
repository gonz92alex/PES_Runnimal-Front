package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.Observable;

public interface PetRepository {

    Observable<List<Pet>> list(String ownerEmail);

    Observable<Pet> get(String id, String email);

    Observable<String> modify(Pet pet);

    Observable<String> create(Pet pet);

    Observable<String> delete(String email, String petName);
}
