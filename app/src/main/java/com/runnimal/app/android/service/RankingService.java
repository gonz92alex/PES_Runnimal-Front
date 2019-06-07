package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Ranking;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface RankingService {

    void list(DisposableObserver<List<Ranking>> callback);

    void localRank(String mail, DisposableObserver<List<Ranking>> callback);
}
