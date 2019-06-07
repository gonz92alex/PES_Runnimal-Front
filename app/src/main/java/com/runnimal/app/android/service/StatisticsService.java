package com.runnimal.app.android.service;

import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.domain.StatsWalks;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface StatisticsService {


    void getTrainingStats( DisposableObserver<StatsTraining> callback);

    void getWalkStats( DisposableObserver<StatsWalks> callback);


}
