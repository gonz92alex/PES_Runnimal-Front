package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Training;

import java.util.List;

import io.reactivex.Observable;

public interface TrainingRepository {

    Observable<List<Training>> list();

    Observable<Training> get(String id);

    Observable<String> addPoints(String trainingId);
}
