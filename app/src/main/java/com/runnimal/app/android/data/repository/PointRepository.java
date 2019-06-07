package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Point;

import java.util.List;

import io.reactivex.Observable;

public interface PointRepository {

    Observable<List<Point>> list();
}
