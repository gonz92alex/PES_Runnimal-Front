package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Pet;

import java.util.List;

import io.reactivex.Observable;

public interface PetsRepository {

    Observable<List<Pet>> list(String email);

    Observable<Pet> get(String id);
}
