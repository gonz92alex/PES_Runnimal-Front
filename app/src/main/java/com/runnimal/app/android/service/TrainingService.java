package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.Training;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface TrainingService {

    void list(String idioma, DisposableObserver<List<Training>> callback);

    void get(String id, DisposableObserver<Training> callback);

    void addPoints(String trainingId, DisposableObserver<String> stringDisposableObserver);
}
