package com.runnimal.app.android.service;

import com.runnimal.app.android.data.repository.RankingRepository;

import com.runnimal.app.android.domain.Ranking;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class RankingServiceImpl extends AbstractService implements RankingService {

    private final RankingRepository rankingRepository;

    @Inject
    public RankingServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                               @Named("ui_thread") Scheduler uiThread, //
                               RankingRepository rankingRepository) {
        super(executorThread, uiThread);
        this.rankingRepository = rankingRepository;
    }

    @Override
    public void list(DisposableObserver<List<Ranking>> callback) {
        execute(rankingRepository.list(), callback);
    }

    @Override
    public void get(String id, DisposableObserver<Ranking> callback) {
        execute(rankingRepository.get(id), callback);
    }
}
