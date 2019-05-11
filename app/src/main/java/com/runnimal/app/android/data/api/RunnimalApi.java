package com.runnimal.app.android.data.api;

import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;

import java.util.List;

public interface RunnimalApi {

    void listTrainings(RunnimalApiCallback<List<Training>> callback);

    void listRanking(RunnimalApiCallback<List<Ranking>> callback);

    void getTraining(String id, RunnimalApiCallback<Training> callback);

    void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
