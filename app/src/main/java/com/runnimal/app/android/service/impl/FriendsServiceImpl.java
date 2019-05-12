package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.FriendsRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.FriendsService;
import com.runnimal.app.android.service.TrainingService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class FriendsServiceImpl extends AbstractService implements FriendsService {

    private final FriendsRepository friendsRepository;

    @Inject
    public FriendsServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                               @Named("ui_thread") Scheduler uiThread, //
                              FriendsRepository friendsRepository) {
        super(executorThread, uiThread);
        this.friendsRepository = friendsRepository;
    }

    @Override
    public void list(DisposableObserver<List<Friend>> callback) {
        execute(friendsRepository.list(), callback);
    }

    @Override
    public void get(String id, DisposableObserver<Friend> callback) {
        execute(friendsRepository.get(id), callback);
    }
}
