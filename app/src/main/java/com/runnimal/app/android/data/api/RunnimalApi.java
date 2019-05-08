package com.runnimal.app.android.data.api;

import com.runnimal.app.android.domain.Training;

import java.util.List;

public interface RunnimalApi {

    void getTrainings(RunnimalApiCallback<List<Training>> callback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
