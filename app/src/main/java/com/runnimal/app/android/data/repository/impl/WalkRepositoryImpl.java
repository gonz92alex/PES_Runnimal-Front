package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.WalkRepository;
import com.runnimal.app.android.domain.Walk;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WalkRepositoryImpl implements WalkRepository {

    private final RunnimalApi api;

    @Inject
    public WalkRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Walk>> list() {
        return Observable.create(emitter -> {
            api.listWalks(new RunnimalApi.RunnimalApiCallback<List<Walk>>() {
                @Override
                public void responseOK(List<Walk> walks) {
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

    @Override
    public Observable<Walk> save(Walk walk) {
        return Observable.create(emitter -> {
            api.createWalk(walk, new RunnimalApi.RunnimalApiCallback<String>() {
                @Override
                public void responseOK(String id) {
                    walk.setId(id);
                    emitter.onNext(walk);
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
