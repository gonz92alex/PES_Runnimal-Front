package com.runnimal.app.android.data.api.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
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

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/trainnings";

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

        queue.add(stringRequest);
    }

    @Override
    public void listRanking(RunnimalApiCallback<List<Ranking>> callback) {

    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/trainnings/" + id;

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

        queue.add(stringRequest);
    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

    }

    @Override
    public void listPets(RunnimalApiCallback<List<Pet>> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/pets/user/" + SingletonSession.Instance().getMail();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                url, //
                (response) -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    callback.responseOK(jacksonFactory.toList(response, Pet.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        queue.add(stringRequest);
    }

    @Override
    public void getPet(String id, RunnimalApiCallback<Pet> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                url, //
                (response) -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    callback.responseOK(jacksonFactory.toObject(response, Pet.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        queue.add(stringRequest);
    }

    @Override
    public void modifyPet(Pet pet, RunnimalApiCallback<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + pet.getId();

        pet.setId(null);

        StringRequest stringRequest = new StringRequest( //
                Request.Method.PUT, //
                url, //
                reponse -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    //TODO
                    callback.responseOK("");
                },
                error -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                } //
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            @SneakyThrows
            public byte[] getBody() {
                return jacksonFactory.toJsonNode(pet).toString().getBytes("utf-8");
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void createPet(Pet pet, RunnimalApiCallback<Pet> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://nidorana.fib.upc.edu/api/pets/";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest( //
                Request.Method.POST, //
                url, //
                response -> {
                    Log.d("apiRes", "onResponse: respondido!");
                    //TODO: Añadir id a "pet"
                    callback.responseOK(pet);
                }
                , //
                error -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                } //
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            @SneakyThrows
            public byte[] getBody() {
                JsonNode node = jacksonFactory.toJsonNode(pet);
                JSONObject message = new JSONObject(node.toString()) //
                        .put("owner", SingletonSession.Instance().getMail());
                return message.toString().getBytes("utf-8");
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
