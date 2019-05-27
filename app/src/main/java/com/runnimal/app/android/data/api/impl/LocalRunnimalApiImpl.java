package com.runnimal.app.android.data.api.impl;

import android.graphics.Bitmap;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.domain.StatsWalks;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;
import com.runnimal.app.android.domain.Walk;
import com.runnimal.app.android.util.IOUtils;
import com.runnimal.app.android.util.JacksonFactory;
import com.runnimal.app.android.util.ConverterUtils;

import java.nio.charset.Charset;
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
    private static final String POINTS_FILE = "json/points.json";
    private static final String OWNERS_FILE = "json/owners.json";
    private static final String FRIENDS_FILE = "json/friends.json";
    private static final String LOGIN_FILE = "json/login.json";
    private static final String STATS_FILE = "json/trainingStats.json";
    private static final String WALK_STATS_FILE = "json/walkStats.json";
    private static final String WALKS_FILE = "json/walks.json";

    private final JacksonFactory jacksonFactory;

    @Inject
    public LocalRunnimalApiImpl(JacksonFactory jacksonFactory) {
        this.jacksonFactory = jacksonFactory;
    }

    @Override
    @SneakyThrows
    public void login(String email, String password, RunnimalApiCallback<String> callback) {
        if (email.equals("ash@pokemon.com") && password.equals("pikachu")) {
            callback.responseOK(ConverterUtils.convert_to_string(IOUtils.getResource(LOGIN_FILE), Charset.defaultCharset()));
        } else {
            callback.responseError(new RuntimeException("Invalid login"));
        }
    }

    @Override
    public void signup(String email, String password, String alias, RunnimalApiCallback<String> callback) {
        //toDO
        callback.responseOK("8895cf3e80bd52ed5067a1b9946");
    }

    @Override
    public void listTrainings(String idioma, RunnimalApiCallback<List<Training>> callback) {
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
    public void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(PETS_FILE), Pet.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getPet(String id, String email, RunnimalApiCallback<Pet> callback) {
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
        callback.responseOK("8895cf3e80bd52ed5067a1b9946");
    }

    @Override
    public void getFriendRequests(String ownerEmail, RunnimalApiCallback<List<FriendRequest>> callback) {
        callback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIEND_REQUESTS_FILE), FriendRequest.class));
    }

    @Override
    public void isFriend(String friendEmail, RunnimalApiCallback<String> callback) {
        callback.responseOK("[{\"_id\":\"5cf4329ed8449607555a43c5\",\"user1\":{\"_id\":\"5cf3e80bd52ed5067a1b9946\",\"alias\":\"nuevoUser\",\"email\":\"nuevoUser@pokemon.com\",\"password\":\"$2b$10$hpSFI911JPdWW8Y2TnavautRMVLeXb.EugNQDJME4NPjd18cUr4IG\",\"__v\":0,\"points\":70},\"user2\":{\"_id\":\"5c9518c262d914013dd5af3b\",\"alias\":\"Swafta\",\"email\":\"ash@pokemon.com\",\"password\":\"pikachu\",\"__v\":0,\"points\":1640},\"date\":\"Sun Jun 02 2019 20:33:34 GMT+0000 (Coordinated Universal Time)\",\"type\":\"friend\",\"__v\":0}]");
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
    public void listOwners(RunnimalApiCallback<List<Owner>> callback) {
        callback.responseOK(jacksonFactory.toList(IOUtils.getResource(OWNERS_FILE), Owner.class));
    }

    @Override
    public void addPoint(String trainingId, RunnimalApiCallback<String> stringRunnimalApiCallback) {

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
    public void deleteOwner(String id, String mail, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }

    @Override
    public void addOwner(String petId, String email, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
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
    public void acceptFriend(String id, RunnimalApiCallback<String> listRunnimalApiCallback) {

    }

    @Override
    public void listPoints(RunnimalApiCallback<List<Point>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(POINTS_FILE), Point.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void listFriendshipRequests(RunnimalApiCallback<List<Friendship>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIEND_REQUESTS_FILE), Friendship.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void rejectFriendship(String id, RunnimalApiCallback<String> callback) {

    }

    @Override
    public void listFriendships(RunnimalApiCallback<List<Friendship>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(FRIENDS_FILE), Friendship.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void getStatsTraining(RunnimalApiCallback<StatsTraining> statsTrainingRunnimalApiCallback) {
        try {
            statsTrainingRunnimalApiCallback.responseOK(jacksonFactory.toObject(IOUtils.getResource(STATS_FILE), StatsTraining.class));
        } catch (Exception e) {
            statsTrainingRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void getStatsWalks(RunnimalApiCallback<StatsWalks> statsWalksRunnimalApiCallback) {
        try {
            statsWalksRunnimalApiCallback.responseOK(jacksonFactory.toObject(IOUtils.getResource(WALK_STATS_FILE), StatsWalks.class));
        } catch (Exception e) {
            statsWalksRunnimalApiCallback.responseError(e);
        }
    }
    @Override
    public void listWalks(RunnimalApiCallback<List<Walk>> listRunnimalApiCallback) {
        try {
            listRunnimalApiCallback.responseOK(jacksonFactory.toList(IOUtils.getResource(WALKS_FILE), Walk.class));
        } catch (Exception e) {
            listRunnimalApiCallback.responseError(e);
        }
    }

    @Override
    public void createWalk(Walk walk, RunnimalApiCallback<String> callback) {
        callback.responseOK("1dab12c262d91ab13215a541");
    }


}
