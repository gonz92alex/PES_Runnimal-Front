package com.runnimal.app.android.di.modules;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.api.impl.LocalRunnimalApiImpl;
import com.runnimal.app.android.data.api.impl.RunnimalApiImpl;
import com.runnimal.app.android.data.repository.MediaRepository;
import com.runnimal.app.android.data.repository.PetRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.data.repository.impl.MediaRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.PetRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.TrainingRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;

@Module
@RequiredArgsConstructor
public class DataModule {

    @Provides
    @Singleton
    RunnimalApi runnimalApi(LocalRunnimalApiImpl runnimalApi) {
        return runnimalApi;
    }

    @Provides
    @Singleton
    TrainingRepository trainingRepository(TrainingRepositoryImpl trainingsRepository) {
        return trainingsRepository;
    }

    @Provides
    @Singleton
    PetRepository petsRepository(PetRepositoryImpl petsRepository) {
        return petsRepository;
    }

    @Provides
    @Singleton
    MediaRepository mediaRepository(MediaRepositoryImpl mediaRepository) {
        return mediaRepository;
    }

    @Provides
    @Singleton
    RequestQueue requestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

}
