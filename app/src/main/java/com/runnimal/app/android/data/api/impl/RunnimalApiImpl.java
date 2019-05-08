package com.runnimal.app.android.data.api.impl;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.JacksonFactory;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import lombok.SneakyThrows;

public class RunnimalApiImpl implements RunnimalApi {

    private final Context context;
    private final JacksonFactory jacksonFactory;

    @Inject
    public RunnimalApiImpl(Context context, JacksonFactory jacksonFactory) {
        this.context = context;
        this.jacksonFactory = jacksonFactory;
    }

    @SneakyThrows
    public List<Training> getTrainings() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/trainnings";

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
        requestQueue.add(request);

        JSONObject response = future.get();

        return jacksonFactory.toList(response.toString(), Training.class);
    }
}
