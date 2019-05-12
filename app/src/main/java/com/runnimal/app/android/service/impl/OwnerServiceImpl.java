package com.runnimal.app.android.service.impl;

import com.runnimal.app.android.data.repository.OwnerRepository;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.OwnerService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class OwnerServiceImpl extends AbstractService implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Inject
    public OwnerServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                            @Named("ui_thread") Scheduler uiThread, //
                            OwnerRepository ownerRepository) {
        super(executorThread, uiThread);
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void get(String id, DisposableObserver<Owner> callback) {
        execute(ownerRepository.get(id), callback);
    }

    @Override
    public void modify(Owner owner, DisposableObserver<String> callback) {
        execute(ownerRepository.modify(owner), callback);
    }

    @Override
    public void create(Owner owner, DisposableObserver<String> callback) {
        execute(ownerRepository.create(owner), callback);
    }

    @Override
    public void getFriendRequests(String ownerEmail, DisposableObserver<List<FriendRequest>> callback) {
        execute(ownerRepository.getFriendRequests(ownerEmail), callback);
    }

    @Override
    public void isFriend(String friendEmail, DisposableObserver<Boolean> callback) {
        execute(ownerRepository.isFriend(friendEmail), callback);
    }

    @Override
    public void createFriendRequest(String requestedEmail, DisposableObserver<String> callback) {
        execute(ownerRepository.createFriendRequest(requestedEmail), callback);
    }

    @Override
    public void deleteFriend(String ownerId, DisposableObserver<String> callback) {
        execute(ownerRepository.deteleFriend(ownerId), callback);
    }
}
