package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.FriendshipRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.FriendshipService;
import com.runnimal.app.android.service.TrainingService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class FriendshipServiceImpl extends AbstractService implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    @Inject
    public FriendshipServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                                 @Named("ui_thread") Scheduler uiThread, //
                                 FriendshipRepository friendshipRepository) {
        super(executorThread, uiThread);
        this.friendshipRepository = friendshipRepository;
    }



    @Override
    public void listRequestsFriendship(DisposableObserver<List<Friendship>> callback) {
        execute(friendshipRepository.list(), callback);
    }

    @Override
    public void acceptFriend(String id, DisposableObserver<String> callback) {
        execute(friendshipRepository.acceptFriend(id),callback);
    }

    @Override
    public void rejectFriend(String id, DisposableObserver<String> callback) {
        execute(friendshipRepository.rejectFriend(id),callback);

    }

    @Override
    public void listFriendship(DisposableObserver<List<Friendship>> callback) {
        execute(friendshipRepository.listFriendship(),callback);
    }
}