package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.PetRepository;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.PetService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class PetServiceImpl extends AbstractService implements PetService {

    private final PetRepository petsRepository;

    @Inject
    public PetServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                          @Named("ui_thread") Scheduler uiThread, //
                          PetRepository petsRepository) {
        super(executorThread, uiThread);
        this.petsRepository = petsRepository;
    }

    @Override
    public void list(String ownerEmail, DisposableObserver<List<Pet>> callback) {
        execute(petsRepository.list(ownerEmail), callback);
    }

    @Override
    public void get(String id, DisposableObserver<Pet> callback) {
        execute(petsRepository.get(id), callback);
    }

    @Override
    public void modify(Pet pet, DisposableObserver<String> callback) {
        execute(petsRepository.modify(pet), callback);
    }

    @Override
    public void create(Pet pet, DisposableObserver<Pet> callback) {
        execute(petsRepository.create(pet), callback);
    }

}
