package com.runnimal.app.android.data.api;

import android.graphics.Bitmap;

import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;

import java.util.List;

public interface RunnimalApi {

    void login(String email, String password, RunnimalApiCallback<String> callback);

    void signup(String email, String password, String alias,RunnimalApiCallback<String> callback);

    void listTrainings(RunnimalApiCallback<List<Training>> callback);

    void getTraining(String id, RunnimalApiCallback<Training> callback);

    void listRankings(RunnimalApiCallback<List<Ranking>> callback);

    void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback);

    void getPet(String id, String email, RunnimalApiCallback<Pet> callback);

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

    void listOwners(RunnimalApiCallback<List<Owner>> listRunnimalApiCallback);

    void addPoint(int points, String email, RunnimalApiCallback<String> stringRunnimalApiCallback);

    void listLocalRanking(RunnimalApiCallback<List<Ranking>> listRunnimalApiCallback);

    void deleteOwner(String id, String mail, RunnimalApiCallback<String> stringRunnimalApiCallback);

    void addOwner(String petId, String email, RunnimalApiCallback<String> callback);

    void listRequests(RunnimalApiCallback<List<Owner>> listRunnimalApiCallback);

    void acceptFriend(String id, RunnimalApiCallback<String> listRunnimalApiCallback);

    void listPoints(RunnimalApiCallback<List<Point>> listRunnimalApiCallback);

    void listFriendshipRequests(RunnimalApiCallback<List<Friendship>> listRunnimalApiCallback);

    void rejectFriendship(String id, RunnimalApiCallback<String> callback);

    void listFriendships(RunnimalApiCallback<List<Friendship>> listRunnimalApiCallback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
