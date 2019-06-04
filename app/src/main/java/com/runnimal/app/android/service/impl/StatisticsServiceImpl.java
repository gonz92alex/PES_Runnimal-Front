package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.StatisticsRepository;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.StatisticsService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class StatisticsServiceImpl extends AbstractService implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Inject
    public StatisticsServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                               @Named("ui_thread") Scheduler uiThread, //
                                 StatisticsRepository statisticsRepository) {
        super(executorThread, uiThread);
        this.statisticsRepository = statisticsRepository;
    }


    @Override
    public void getTrainingStats(DisposableObserver<StatsTraining> callback) {
        execute(statisticsRepository.getStatsTraining(), callback);
    }

}
