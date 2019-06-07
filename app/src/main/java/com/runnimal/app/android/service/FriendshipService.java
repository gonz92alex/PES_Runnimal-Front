package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface FriendshipService {
    void listRequestsFriendship(DisposableObserver<List<Friendship>> callback);

    void acceptFriend(String id, DisposableObserver<String> listDisposableObserver);
    void rejectFriend(String id, DisposableObserver<String> listDisposableObserver);

    void listFriendship(DisposableObserver<List<Friendship>> listDisposableObserver);
}

