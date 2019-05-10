package com.runnimal.app.android.data.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.IOUtils;
import com.runnimal.app.android.util.JacksonFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LocalRunnimalApiImpl implements RunnimalApi {

    private static final String TRAININGS_FILE = "json/trainings.json";
    private static final String TRAINING_DETAIL_FILE = "json/training-detail.json";

    private final JacksonFactory jacksonFactory;

    @Inject
    public LocalRunnimalApiImpl(JacksonFactory jacksonFactory) {
        this.jacksonFactory = jacksonFactory;
    }

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(TRAININGS_FILE), Training.class));
        }
        catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        try {
            callback.responseOK(jacksonFactory.toObject(IOUtils.getResource(TRAINING_DETAIL_FILE), Training.class));
        }
        catch (Exception e) {
            callback.responseError(e);
        }
    }
}
