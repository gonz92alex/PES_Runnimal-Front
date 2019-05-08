package com.runnimal.app.android.service;

import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class TrainingService extends Service<List<Training>> {

    private final TrainingRepository repository;

    @Inject
    public TrainingService(@Named("executor_thread") Scheduler executorThread,
                           @Named("ui_thread") Scheduler uiThread, TrainingRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<Training>> createObservableUseCase() {
        return this.repository.list();
    }
}
