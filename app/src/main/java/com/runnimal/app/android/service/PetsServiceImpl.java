package com.runnimal.app.android.service;

import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.data.repository.PetsRepository;
import com.runnimal.app.android.domain.Pet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class PetsServiceImpl extends AbstractService implements PetsService {

    private final PetsRepository petsRepository;

    @Inject
    public PetsServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                           @Named("ui_thread") Scheduler uiThread, //
                           PetsRepository petsRepository) {
        super(executorThread, uiThread);
        this.petsRepository = petsRepository;
    }

    @Override
    public void list(DisposableObserver<List<Pet>> callback) {
        execute(petsRepository.list(SingletonSession.Instance().getMail()), callback);
    }
}
