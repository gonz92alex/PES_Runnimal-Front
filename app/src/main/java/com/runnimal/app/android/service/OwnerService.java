package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface OwnerService {

    void login(String email, String password, DisposableObserver<String> callback);

    void get(String id, DisposableObserver<Owner> callback);

    void modify(Owner owner, DisposableObserver<String> callback);

    void create(Owner owner, DisposableObserver<String> callback);

    void getFriendRequests(String ownerEmail, DisposableObserver<List<FriendRequest>> callback);

    void isFriend(String friendEmail, DisposableObserver<Boolean> callback);

    void createFriendRequest(String requestedEmail, DisposableObserver<String> callback);

    void deleteFriend(String ownerId, DisposableObserver<String> callback);
}
