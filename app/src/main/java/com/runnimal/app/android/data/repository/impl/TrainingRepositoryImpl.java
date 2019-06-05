package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
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
            api.listTrainings(new RunnimalApi.RunnimalApiCallback<List<Training>>() {
                @Override
                public void responseOK(List<Training> trainings) {
                    emitter.onNext(trainings);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            });
        });
    }

    public Observable<Training> get(String id) {
        return Observable.create(emitter -> {
            api.getTraining(id, //
                    new RunnimalApi.RunnimalApiCallback<Training>() {
                        @Override
                        public void responseOK(Training training) {
                            emitter.onNext(training);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Observable<String> addPoints(String trainingId) {
        return Observable.create(emitter -> {
            api.addPoint(trainingId, //
                    new RunnimalApi.RunnimalApiCallback<String>() {
                        @Override
                        public void responseOK(String response) {
                            emitter.onNext(response);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
            });
    }


}
