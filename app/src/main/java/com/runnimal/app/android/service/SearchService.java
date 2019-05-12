package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.User;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface SearchService {

    void list(DisposableObserver<List<User>> callback);

    void get(String id, DisposableObserver<User> callback);
}
