package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Friend;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface FriendsService {

    void list(DisposableObserver<List<Friend>> callback);

    void get(String id, DisposableObserver<Friend> callback);
}
