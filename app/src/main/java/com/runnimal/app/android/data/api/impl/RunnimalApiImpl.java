package com.runnimal.app.android.data.api.impl;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.JacksonFactory;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import lombok.SneakyThrows;

public class RunnimalApiImpl extends AbstractApiClient implements RunnimalApi {

    private final JacksonFactory jacksonFactory;

    @Inject
    public RunnimalApiImpl(RequestQueue requestQueue, JacksonFactory jacksonFactory) {
        super(requestQueue);
        this.jacksonFactory = jacksonFactory;
    }

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        get("http://nidorana.fib.upc.edu/api/trainnings", //
                response -> {
                    return jacksonFactory.toList(response, Training.class);

                }, //
                callback);
    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        get("http://nidorana.fib.upc.edu/api/trainnings/" + id, //
                response -> {
                    return jacksonFactory.toObject(response, Training.class);

                }, //
                callback);
    }

    @Override
    public void listRankings(RunnimalApiCallback<List<Ranking>> callback) {

    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

    }

    @Override
    public void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback) {
        String email = ownerEmail != null ? ownerEmail : SingletonSession.Instance().getMail();
        get("http://nidorana.fib.upc.edu/api/pets/user/" + email, //
                response -> {
                    return jacksonFactory.toList(response, Pet.class);

                }, //
                callback);
    }

    @Override
    public void getPet(String id, RunnimalApiCallback<Pet> callback) {
        get("http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + id, //
                response -> {
                    return jacksonFactory.toObject(response, Pet.class);

                }, //
                callback);
    }

    @Override
    @SneakyThrows
    public void modifyPet(Pet pet, RunnimalApiCallback<String> callback) {
        String url = "http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() + "/" + pet.getId();

        pet.setId(null);
        JSONObject jsonBody = new JSONObject(jacksonFactory.toJsonNode(pet).toString());

        put(url, jsonBody, callback);
    }

    @Override
    @SneakyThrows
    public void createPet(Pet pet, RunnimalApiCallback<String> callback) {
        JSONObject jsonBody = new JSONObject(jacksonFactory.toJsonNode(pet).toString()) //
                .put("owner", SingletonSession.Instance().getMail());
        post("http://nidorana.fib.upc.edu/api/pets/", jsonBody, callback);
    }

    @Override
    public void getOwner(String id, RunnimalApiCallback<Owner> callback) {
        get("http://nidorana.fib.upc.edu/api/users/" + SingletonSession.Instance().getMail() + "/" + id, //
                response -> {
                    return jacksonFactory.toObject(response, Owner.class);

                }, //
                callback);
    }

    @Override
    public void getFriendRequests(String ownerEmail, RunnimalApiCallback<List<FriendRequest>> callback) {
        get("http://nidorana.fib.upc.edu/api/friendRequests/" + ownerEmail, //
                response -> {
                    return jacksonFactory.toList(response, FriendRequest.class);

                }, //
                callback);
    }

    @Override
    public void isFriend(String friendEmail, RunnimalApiCallback<Boolean> callback) {
        get("http://nidorana.fib.upc.edu/api/friends/" + SingletonSession.Instance().getMail() + "/" + friendEmail, //
                response -> {
                    // TODO
                    return true;

                }, //
                callback);
    }

    @Override
    @SneakyThrows
    public void createFriendRequest(String requestedEmail, RunnimalApiCallback<String> callback) {
        JSONObject jsonBody = new JSONObject() //
                .put("requestingEmail", SingletonSession.Instance().getMail()) //
                .put("requestedEmail", requestedEmail);
        post("http://nidorana.fib.upc.edu/api/friendRequests/new", jsonBody, callback);
    }

    @Override
    public void deleteFriend(String ownerId, RunnimalApiCallback<String> callback) {
        delete("http://nidorana.fib.upc.edu/api/friends/delete/" + ownerId, callback);
    }

    @Override
    public void uploadImage(final Bitmap image, RunnimalApiCallback<String> callback) {
        uploadImage(image, "http://nidorana.fib.upc.edu/api/photo/pet/emailDueño/nombrePet", callback);
    }

}
