package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.Observable;

public interface PetsRepository {

    Observable<List<Pet>> list();

    Observable<Pet> get(String id);

    Observable<String> modify(Pet pet);
}
