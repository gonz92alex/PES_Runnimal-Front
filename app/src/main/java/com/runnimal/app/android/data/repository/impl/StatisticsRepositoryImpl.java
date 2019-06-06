package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.StatisticsRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.domain.StatsWalks;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final RunnimalApi api;

    @Inject
    public StatisticsRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    public Observable<StatsTraining> getStatsTraining() {
        return Observable.create(emitter -> {
            api.getStatsTraining(
                    new RunnimalApi.RunnimalApiCallback<StatsTraining>() {
                        @Override
                        public void responseOK(StatsTraining training) {
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
    public Observable<StatsWalks> getStatsWalks() {
        return Observable.create(emitter -> {
            api.getStatsWalks(
                    new RunnimalApi.RunnimalApiCallback<StatsWalks>() {
                        @Override
                        public void responseOK(StatsWalks walks) {
                            emitter.onNext(walks);
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

