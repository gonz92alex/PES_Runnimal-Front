package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Owner;

import io.reactivex.observers.DisposableObserver;

public interface OwnerService {

    void get(String id, DisposableObserver<Owner> callback);
}
