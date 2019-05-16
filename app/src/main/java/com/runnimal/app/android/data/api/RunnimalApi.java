package com.runnimal.app.android.data.api;

import android.graphics.Bitmap;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;

import java.util.List;

public interface RunnimalApi {

    void login(String email, String password, RunnimalApiCallback<String> callback);

    void listTrainings(RunnimalApiCallback<List<Training>> callback);

    void getTraining(String id, RunnimalApiCallback<Training> callback);

    void listRankings(RunnimalApiCallback<List<Ranking>> callback);

    void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback);

    void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback);

    void getPet(String id, RunnimalApiCallback<Pet> callback);

    void modifyPet(Pet pet, RunnimalApiCallback<String> callback);

    void createPet(Pet pet, RunnimalApiCallback<String> callback);

    void deletePet(String email, String petName, RunnimalApiCallback<String> callback);

    void getOwner(String id, RunnimalApiCallback<Owner> callback);

    void modifyOwner(Owner owner, RunnimalApiCallback<String> callback);

    void createOwner(Owner owner, RunnimalApiCallback<String> callback);

    void getFriendRequests(String ownerEmail, RunnimalApiCallback<List<FriendRequest>> callback);

    void isFriend(String friendEmail, RunnimalApiCallback<Boolean> callback);

    void createFriendRequest(String requestedEmail, RunnimalApiCallback<String> callback);

    void deleteFriend(String requestedEmail, RunnimalApiCallback<String> callback);

    void uploadImage(Bitmap image, String path, RunnimalApiCallback<String> callback);

    void listFriends(RunnimalApiCallback<List<Friend>> listRunnimalApiCallback);

    void getFriend(String id, RunnimalApiCallback<Friend> friendRunnimalApiCallback);

    void listUsers(RunnimalApiCallback<List<User>> listRunnimalApiCallback);

    void getUser(String id, RunnimalApiCallback<User> userRunnimalApiCallback);

    void listOwners(RunnimalApiCallback<List<Owner>> listRunnimalApiCallback);

    void addPoint(int points, String email, RunnimalApiCallback<String> stringRunnimalApiCallback);

    void listLocalRanking(RunnimalApiCallback<List<Ranking>> listRunnimalApiCallback);

    void listRequests(RunnimalApiCallback<List<Owner>> listRunnimalApiCallback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
