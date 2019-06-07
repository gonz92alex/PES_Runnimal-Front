package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Point;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface PointService {

    void list(DisposableObserver<List<Point>> callback);
}
