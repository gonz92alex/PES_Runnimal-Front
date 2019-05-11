package com.runnimal.app.android.data.api.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.JacksonFactory;

import java.util.List;

import javax.inject.Inject;

public class RunnimalApiImpl implements RunnimalApi {

    private final Context context;
    private final JacksonFactory jacksonFactory;

    @Inject
    public RunnimalApiImpl(Context context, JacksonFactory jacksonFactory) {
        this.context = context;
        this.jacksonFactory = jacksonFactory;
    }

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/trainnings";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                url, //
                (response) -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    callback.responseOK(jacksonFactory.toList(response, Training.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void listRanking(RunnimalApiCallback<List<Ranking>> callback) {

    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/trainnings/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                url, //
                (response) -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    callback.responseOK(jacksonFactory.toObject(response, Training.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

    }
}
