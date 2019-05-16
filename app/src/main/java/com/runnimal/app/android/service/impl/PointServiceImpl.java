package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.PointRepository;
import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.PointService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class PointServiceImpl extends AbstractService implements PointService {

    private final PointRepository pointRepository;

    @Inject
    public PointServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                            @Named("ui_thread") Scheduler uiThread, //
                            PointRepository pointRepository) {
        super(executorThread, uiThread);
        this.pointRepository = pointRepository;
    }

    @Override
    public void list(DisposableObserver<List<Point>> callback) {
        execute(pointRepository.list(), callback);
    }
}
