package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Walk;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface WalkService {

    void list(DisposableObserver<List<Walk>> callback);
}
