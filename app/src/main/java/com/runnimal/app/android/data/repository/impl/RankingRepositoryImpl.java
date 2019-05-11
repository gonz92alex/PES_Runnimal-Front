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

    public Observable<Ranking> get(String id) {
        return Observable.create(emitter -> {
            api.getRanking(id, //
                    new RunnimalApi.RunnimalApiCallback<Ranking>() {
                        @Override
                        public void responseOK(Ranking ranking) {
                            emitter.onNext(ranking);
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
