package com.runnimal.app.android.di.modules;

import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.service.impl.PetServiceImpl;
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
    PetService petsService(PetServiceImpl petsService) {
        return petsService;
    }

}
