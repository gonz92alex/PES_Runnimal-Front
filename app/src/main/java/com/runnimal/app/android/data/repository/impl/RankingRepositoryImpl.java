package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.RankingRepository;
import com.runnimal.app.android.domain.Ranking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RankingRepositoryImpl implements RankingRepository {

    private final RunnimalApi api;

    @Inject
    public RankingRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    public Observable<List<Ranking>> list() {
        return Observable.create(emitter -> {
            api.listRankings(new RunnimalApi.RunnimalApiCallback<List<Ranking>>() {
                @Override
                public void responseOK(List<Ranking> rankings) {
                    emitter.onNext(rankings);
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
    public Observable<List<Ranking>> localRank(String mail) {
        return Observable.create(emitter -> {
            api.listLocalRanking(new RunnimalApi.RunnimalApiCallback<List<Ranking>>() {
                @Override
                public void responseOK(List<Ranking> rankings) {
                    emitter.onNext(rankings);
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
