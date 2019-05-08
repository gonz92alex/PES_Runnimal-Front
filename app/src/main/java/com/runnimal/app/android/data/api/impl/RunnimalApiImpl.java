package com.runnimal.app.android.data.api.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.Training;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.SneakyThrows;

public class RunnimalApiImpl implements RunnimalApi {

    @Inject
    public RunnimalApiImpl() {

    }

    @SneakyThrows
    public List<Training> getTrainings() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(new Training() //
                .setName("Training name") //
                .setImageUrl(URI.create("https://t2.uc.ltmcdn.com/images/0/5/2/img_como_ensenar_a_un_perro_a_dar_la_pata_22250_600.jpg")) //
                .setDescription("Training description"));
        return trainings;
    }
}
