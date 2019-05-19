package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.FriendsRepository;
import com.runnimal.app.android.data.repository.SearchRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.FriendsService;
import com.runnimal.app.android.service.SearchService;
import com.runnimal.app.android.service.TrainingService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class SearchServiceImpl extends AbstractService implements SearchService {

    private final SearchRepository searchRepository;

    @Inject
    public SearchServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                              @Named("ui_thread") Scheduler uiThread, //
                             SearchRepository searchRepository) {
        super(executorThread, uiThread);
        this.searchRepository = searchRepository;
    }

    @Override
    public void list(DisposableObserver<List<User>> callback) {
        execute(searchRepository.list(), callback);
    }

    @Override
    public void get(String id, DisposableObserver<User> callback) {
        execute(searchRepository.get(id), callback);
    }
}
