package com.runnimal.app.android.data.api;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;

import java.util.List;

public interface RunnimalApi {

    void listTrainings(RunnimalApiCallback<List<Training>> callback);

    void listRanking(RunnimalApiCallback<List<Ranking>> callback);

    void getTraining(String id, RunnimalApiCallback<Training> callback);

    void listPets(RunnimalApiCallback<List<Pet>> callback);

    void getPet(String id, RunnimalApiCallback<Pet> callback);

    void modifyPet(Pet pet, RunnimalApiCallback<String> callback);

    void createPet(Pet pet, RunnimalApiCallback<Pet> callback);

    void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback);

    interface RunnimalApiCallback<T> {

        void responseOK(T response);

        void responseError(Exception e);
    }
}
