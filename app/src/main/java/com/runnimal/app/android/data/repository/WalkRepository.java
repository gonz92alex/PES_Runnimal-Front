package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Walk;

import java.util.List;

import io.reactivex.Observable;

public interface WalkRepository {

    Observable<List<Walk>> list();
}
