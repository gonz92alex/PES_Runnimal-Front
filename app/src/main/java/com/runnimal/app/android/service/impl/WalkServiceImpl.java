package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.WalkRepository;
import com.runnimal.app.android.domain.Walk;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.WalkService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class WalkServiceImpl extends AbstractService implements WalkService {

    private final WalkRepository walkRepository;

    @Inject
    public WalkServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                           @Named("ui_thread") Scheduler uiThread, //
                           WalkRepository walkRepository) {
        super(executorThread, uiThread);
        this.walkRepository = walkRepository;
    }

    @Override
    public void list(DisposableObserver<List<Walk>> callback) {
        execute(walkRepository.list(), callback);
    }
}
