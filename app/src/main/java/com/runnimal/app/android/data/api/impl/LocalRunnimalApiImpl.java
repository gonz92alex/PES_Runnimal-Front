package com.runnimal.app.android.data.api.impl;

import android.graphics.Bitmap;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.IOUtils;
import com.runnimal.app.android.util.JacksonFactory;

import java.util.List;

import javax.inject.Inject;

public class LocalRunnimalApiImpl implements RunnimalApi {

    private static final String TRAININGS_FILE = "json/trainings.json";
    private static final String RANKINGS_FILE = "json/rankings.json";
    private static final String TRAINING_DETAIL_FILE = "json/training-detail.json";
    private static final String PETS_FILE = "json/pets.json";
    private static final String PET_DETAIL_FILE = "json/pet-detail.json";
    private static final String OWNER_DETAIL_FILE = "json/owner-detail.json";

    private final JacksonFactory jacksonFactory;

    @Inject
    public LocalRunnimalApiImpl(JacksonFactory jacksonFactory) {
        this.jacksonFactory = jacksonFactory;
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
    public void listRanking(RunnimalApiCallback<List<Ranking>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(RANKINGS_FILE), Ranking.class));
        }
        catch (Exception e) {
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
    public void listPets(String ownerEmail, RunnimalApiCallback<List<Pet>> callback) {
        try {
            callback.responseOK(jacksonFactory.toList(IOUtils.getResource(PETS_FILE), Pet.class));
        } catch (Exception e) {
            callback.responseError(e);
        }
    }

    @Override
    public void getRanking(String id, RunnimalApiCallback<Ranking> rankingRunnimalApiCallback) {

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
    public void createPet(Pet pet, RunnimalApiCallback<Pet> callback) {
        callback.responseOK(new Pet());
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
    public void uploadImage(final Bitmap image, RunnimalApiCallback<String> callback) {
        callback.responseOK("OK");
    }
}
