package com.runnimal.app.android.data.api;

import com.runnimal.app.android.domain.Training;

import java.util.List;

public interface RunnimalApi {

    void listTrainings(RunnimalApiCallback<List<Training>> callback);

    void getTraining(String id, RunnimalApiCallback<Training> callback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
