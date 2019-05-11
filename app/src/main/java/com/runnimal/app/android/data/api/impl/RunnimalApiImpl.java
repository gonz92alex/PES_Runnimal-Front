package com.runnimal.app.android.data.api.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.VolleyMultipartRequest;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.JacksonFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.SneakyThrows;

public class RunnimalApiImpl implements RunnimalApi {

    private final Context context;
    private final JacksonFactory jacksonFactory;
    private final RequestQueue requestQueue;

    @Inject
    public RunnimalApiImpl(Context context, JacksonFactory jacksonFactory, RequestQueue requestQueue) {
        this.context = context;
        this.jacksonFactory = jacksonFactory;
        this.requestQueue = requestQueue;
    }

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                "http://nidorana.fib.upc.edu/api/trainnings", //
                (response) -> {
                    callback.responseOK(jacksonFactory.toList(response, Training.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    public void listRanking(RunnimalApiCallback<List<Ranking>> callback) {

    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                "http://nidorana.fib.upc.edu/api/trainnings/" + id, //
                (response) -> {
                    callback.responseOK(jacksonFactory.toObject(response, Training.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

    }

    @Override
    public void listPets(RunnimalApiCallback<List<Pet>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                "http://nidorana.fib.upc.edu/api/pets/user/" + SingletonSession.Instance().getMail(), //
                (response) -> {
                    callback.responseOK(jacksonFactory.toList(response, Pet.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    public void getPet(String id, RunnimalApiCallback<Pet> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                "http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + id, //
                (response) -> {
                    callback.responseOK(jacksonFactory.toObject(response, Pet.class));

                }, //
                (error) -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    public void modifyPet(Pet pet, RunnimalApiCallback<String> callback) {
        String url = "http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + pet.getId();
        pet.setId(null);

        StringRequest stringRequest = new StringRequest( //
                Request.Method.PUT, //
                url, //
                reponse -> {
                    //TODO: que mensaje poner como respuesta?
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

        requestQueue.add(stringRequest);
    }

    @Override
    public void createPet(Pet pet, RunnimalApiCallback<Pet> callback) {
        StringRequest stringRequest = new StringRequest( //
                Request.Method.POST, //
                "http://nidorana.fib.upc.edu/api/pets/", //
                response -> {
                    //TODO: Añadir id a "pet" creado??
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

        requestQueue.add(stringRequest);
    }

    @Override
    public void uploadImage(final Bitmap image, RunnimalApiCallback<String> callback) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, //
                "http://nidorana.fib.upc.edu/api/photo/pet/emailDueño/nombrePet", //
                response -> {
                    //TODO: que mensaje poner como respuesta?
                    callback.responseOK("");
                }, //
                error -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }//
        ) {
            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("tags", "ccccc");  add string parameters
                return params;
            }

            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("filename", new DataPart(imagename + ".png", getFileDataFromDrawable(image)));
                return params;
            }
        };
        requestQueue.add(volleyMultipartRequest);
    }

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
