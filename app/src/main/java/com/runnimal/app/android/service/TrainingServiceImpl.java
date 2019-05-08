package com.runnimal.app.android.service;

import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class TrainingServiceImpl extends AbstractService implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Inject
    public TrainingServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                               @Named("ui_thread") Scheduler uiThread, //
                               TrainingRepository trainingRepository) {
        super(executorThread, uiThread);
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void list(DisposableObserver<List<Training>> callback) {
        execute(trainingRepository.list(), callback);
    }

    @Override
    public void get(String id, DisposableObserver<Training> callback) {
        execute(trainingRepository.get(id), callback);
    }
}
