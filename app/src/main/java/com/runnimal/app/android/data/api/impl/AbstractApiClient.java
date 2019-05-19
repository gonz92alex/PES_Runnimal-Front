package com.runnimal.app.android.data.api.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.util.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.SneakyThrows;

public abstract class AbstractApiClient {

    private final RequestQueue requestQueue;

    public AbstractApiClient(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public <T> void get(String url, Function<String, T> responseFunction, RunnimalApi.RunnimalApiCallback<T> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, //
                url, //
                response -> {
                    callback.responseOK(responseFunction.apply(response));

                }, //
                error -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    public void post(String url, JSONObject jsonBody, RunnimalApi.RunnimalApiCallback<String> callback) {
        bodyRequest(url, Request.Method.POST, jsonBody, callback);
    }

    public void put(String url, JSONObject jsonBody, RunnimalApi.RunnimalApiCallback<String> callback) {
        bodyRequest(url, Request.Method.PUT, jsonBody, callback);
    }

    /*public void delete(String url, JSONObject jsonBody, RunnimalApi.RunnimalApiCallback<String> callback) {
        bodyRequest(url, Request.Method.DELETE, jsonBody, callback);
    }*/

    public void delete(String url, RunnimalApi.RunnimalApiCallback<String> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, //
                url, //
                response -> {
                    //TODO: que mensaje poner como respuesta?
                    callback.responseOK("");
                }, //
                error -> {
                    Log.d("apiError", error.toString());
                    callback.responseError(error);
                }
        );

        requestQueue.add(stringRequest);
    }

    public void uploadImage(Bitmap image, String path, RunnimalApi.RunnimalApiCallback<String> callback) {
        Log.d("refactor", "http://nidorana.fib.upc.edu/api/photo" + path);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, //
                "http://nidorana.fib.upc.edu/api/photo" + path, //
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

    private void bodyRequest(String url, int requestMethod, JSONObject jsonBody, RunnimalApi.RunnimalApiCallback<String> callback) {
        Log.d("refactor", "delete: " + url + " " + jsonBody.toString() + " " + requestMethod + "=" + Request.Method.DELETE);
        StringRequest stringRequest = new StringRequest( //
                requestMethod, //
                url, //
                response -> {
                    //TODO: que mensaje poner como respuesta?
                    callback.responseOK(response);
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
                return jsonBody.toString().getBytes("utf-8");
            }
        };

        requestQueue.add(stringRequest);
    }

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
