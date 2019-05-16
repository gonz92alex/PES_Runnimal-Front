package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Ranking;

import java.util.List;

import io.reactivex.Observable;

public interface RankingRepository {

    Observable<List<Ranking>> list();

    Observable<Ranking> get(String id);

    Observable<List<Ranking>>  localRank(String mail);
}
