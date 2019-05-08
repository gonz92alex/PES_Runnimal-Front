package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.api.impl.RunnimalApiImpl;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class TrainingRepositoryImpl implements TrainingRepository {

    private final RunnimalApi api;

    @Inject
    public TrainingRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    public Observable<List<Training>> list() {
        return Observable.create(emitter -> {
            List<Training> trainings = api.getTrainings();
            if (trainings != null) {
                emitter.onNext(trainings);
                emitter.onComplete();
            } else {
                emitter.onError(
                        new Throwable("Error getting team data list from the local json (euro_data.json)"));
            }
        });
    }
}
