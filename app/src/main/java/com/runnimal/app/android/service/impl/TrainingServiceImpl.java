package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.TrainingsRepository;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.TrainingService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class TrainingServiceImpl extends AbstractService implements TrainingService {

    private final TrainingsRepository trainingRepository;

    @Inject
    public TrainingServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                               @Named("ui_thread") Scheduler uiThread, //
                               TrainingsRepository trainingRepository) {
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
