package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.RankingRepository;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.RankingService;

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
    public void localRank(String mail, DisposableObserver<List<Ranking>> callback) {
        execute(rankingRepository.localRank(mail), callback);
    }
}
