package com.runnimal.app.android.di.modules;

import com.runnimal.app.android.service.PetsService;
import com.runnimal.app.android.service.impl.PetsServiceImpl;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.service.impl.TrainingServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;

@Module
@RequiredArgsConstructor
public class ServiceModule {

    @Provides
    @Singleton
    TrainingService trainingService(TrainingServiceImpl trainingService) {
        return trainingService;
    }

    @Provides
    @Singleton
    PetsService petsService(PetsServiceImpl petsService) {
        return petsService;
    }

}
