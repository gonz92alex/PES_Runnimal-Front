package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.PointRepository;
import com.runnimal.app.android.domain.Point;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PointRepositoryImpl implements PointRepository {

    private final RunnimalApi api;

    @Inject
    public PointRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Point>> list() {
        return Observable.create(emitter -> {
            api.listPoints(new RunnimalApi.RunnimalApiCallback<List<Point>>() {
                @Override
                public void responseOK(List<Point> points) {
                    emitter.onNext(points);
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
