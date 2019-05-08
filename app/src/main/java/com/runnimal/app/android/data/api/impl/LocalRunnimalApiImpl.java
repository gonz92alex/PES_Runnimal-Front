package com.runnimal.app.android.data.api.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Training;

import java.util.ArrayList;
import java.util.List;

public class LocalRunnimalApiImpl implements RunnimalApi {

    public List<Training> getTrainings() {
        return new ArrayList<>();
    }
}
