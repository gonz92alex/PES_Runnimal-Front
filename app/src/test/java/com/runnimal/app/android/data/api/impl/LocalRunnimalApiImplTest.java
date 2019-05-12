package com.runnimal.app.android.data.api.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.util.JacksonFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.List;

public class LocalRunnimalApiImplTest {

    private LocalRunnimalApiImpl api;

    @Before
    public void init() {
        JacksonFactory jacksonFactory = new JacksonFactory() //
                .registerJavaTime();
        api = new LocalRunnimalApiImpl(jacksonFactory);
    }

    @Test
    public void testListTrainingsResultSize() {
        api.listTrainings(new RunnimalApi.RunnimalApiCallback<List<Training>>() {
            @Override
            public void responseOK(List<Training> trainings) {
                Assert.assertEquals(4, trainings.size());
            }

            @Override
            public void responseError(Exception e) {
            }
        });
    }

    @Test
    public void testTrainingGetInfo() {
        api.getTraining("", new RunnimalApi.RunnimalApiCallback<Training>() {
            @Override
            public void responseOK(Training training) {
                Assert.assertEquals("5caf8caaa996140c5c759f76", training.getId());
                Assert.assertEquals("entrenamiento postman 3", training.getName());
                Assert.assertEquals("descripción postman", training.getDescription());
                Assert.assertEquals(URI.create("https://t2.uc.ltmcdn.com/images/0/5/2/img_como_ensenar_a_un_perro_a_dar_la_pata_22250_600.jpg"), training.getImageUrl());
                Assert.assertEquals(2, training.getSteps().size());
                Assert.assertEquals("first step", training.getSteps().get(0));
                Assert.assertEquals("second step", training.getSteps().get(1));
            }

            @Override
            public void responseError(Exception e) {
            }
        });
    }

    @Test
    public void testListPetsResultSize() {
        api.listPets("", new RunnimalApi.RunnimalApiCallback<List<Pet>>() {
            @Override
            public void responseOK(List<Pet> pets) {
                Assert.assertEquals(5, pets.size());
            }

            @Override
            public void responseError(Exception e) {
            }
        });
    }
}

