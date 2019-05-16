package com.runnimal.app.android.data.api.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;
import com.runnimal.app.android.util.IOUtils;
import com.runnimal.app.android.util.JacksonFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import lombok.SneakyThrows;

public class LocalRunnimalApiImpl implements RunnimalApi {

    private static final String TRAININGS_FILE = "json/trainings.json";
    private static final String RANKINGS_FILE = "json/rankings.json";
    private static final String RANKINGS_LOCAL_FILE = "json/rankings-local.json";
    private static final String TRAINING_DETAIL_FILE = "json/training-detail.json";
    private static final String PETS_FILE = "json/pets.json";
    private static final String PET_DETAIL_FILE = "json/pet-detail.json";
    private static final String OWNER_DETAIL_FILE = "json/owner-detail.json";
    private static final String FRIEND_REQUESTS_FILE = "json/friend-requests.json";

    private final JacksonFactory jacksonFactory;

    @Inject
    public LocalRunnimalApiImpl(JacksonFactory jacksonFactory) {
        this.jacksonFactory = jacksonFactory;
    }

    @Override
    @SneakyThrows
    public void login(String email, String password, RunnimalApiCallback<String> callback) {
        if (email.equals("email") && password.equals("password")) {
            callback.responseOK("OK");
        } else {
            callback.responseError(new RuntimeException("Invalid login"));
        }
    }

    @Override
    public void listTrainings(RunnimalApiCallback<List<Training>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(TRAININGS_FILE), Training.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getTraining(String id, RunnimalApiCallback<Training> callback) {
        try {
            callback.responseOK(jacksonFactory.toObject(IOUtils.getResource(TRAINING_DETAIL_FILE), Training.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void listRankings(RunnimalApiCallback<List<Ranking>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(RANKINGS_FILE), Ranking.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

    }

    @Override
    public void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(PETS_FILE), Pet.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getPet(String id, RunnimalApiCallback<Pet> callback) {
        try {
            callback.responseOK(jacksonFactory.toObject(IOUtils.getResource(PET_DETAIL_FILE), Pet.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void modifyPet(Pet pet, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void createPet(Pet pet, RunnimalApiCallback<String> callback) {
        callback.responseOK("5c9518c262d914013dd5af3b");
    }

    @Override
    public void deletePet(String email, String petName, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void getOwner(String id, RunnimalApiCallback<Owner> callback) {
        try {
            callback.responseOK(jacksonFactory.toObject(IOUtils.getResource(OWNER_DETAIL_FILE), Owner.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void modifyOwner(Owner owner, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void createOwner(Owner owner, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void getFriendRequests(String ownerEmail, RunnimalApiCallback<List<FriendRequest>> callback) {
        callback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIEND_REQUESTS_FILE), FriendRequest.class));
    }

    @Override
    public void isFriend(String friendEmail, RunnimalApiCallback<Boolean> callback) {
        callback.responseOK(true);
    }

    @Override
    @SneakyThrows
    public void createFriendRequest(String requestedEmail, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void deleteFriend(String requestedEmail, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void uploadImage(Bitmap image, String url, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void listFriends(RunnimalApiCallback<List<Friend>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(RANKINGS_FILE), Friend.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void getFriend(String id, RunnimalApiCallback<Friend> friendRunnimalApiCallback) {

    }

    @Override
    public void listUsers(RunnimalApiCallback<List<User>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(RANKINGS_FILE), User.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void getUser(String id, RunnimalApiCallback<User> userRunnimalApiCallback) {

    }

    @Override
    public void listOwners(RunnimalApiCallback<List<Owner>> listRunnimalApiCallback) {
        Log.d("refactor", "local Hola");
    }

    @Override
    public void addPoint(int points, String email, RunnimalApiCallback<String> stringRunnimalApiCallback) {
        Log.d("refactor", "addPoint: hola");
    }

    @Override
    public void listLocalRanking(RunnimalApiCallback<List<Ranking>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(RANKINGS_LOCAL_FILE), Ranking.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void listRequests(RunnimalApiCallback<List<Owner>> callback) {

        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIEND_REQUESTS_FILE), Owner.class));
        } catch (Exception e) {
            callback.responseError(e);
        }

    }

    @Override
    public void listFriendship(RunnimalApiCallback<List<Friendship>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIEND_REQUESTS_FILE), Friendship.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }


    }

    @Override
    public void acceptFriend(String id, RunnimalApiCallback<String> listRunnimalApiCallback) {

    }


}
