package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.LatLon;
import com.runnimal.app.android.domain.Walk;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface WalkService {

    void list(DisposableObserver<List<Walk>> callback);

    void start();

    void addPoint(LatLon latLon);

    void end(int distance, DisposableObserver<Walk> callback);
}
