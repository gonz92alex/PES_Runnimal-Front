package com.runnimal.app.android.di.modules;

import android.content.Context;

import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.api.impl.LocalRunnimalApiImpl;
import com.runnimal.app.android.data.api.impl.RunnimalApiImpl;
import com.runnimal.app.android.data.repository.RankingRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.data.repository.impl.RankingRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.TrainingRepositoryImpl;
import com.runnimal.app.android.service.RankingService;
import com.runnimal.app.android.service.RankingServiceImpl;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.service.TrainingServiceImpl;
import com.runnimal.app.android.util.JacksonFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@Module
@RequiredArgsConstructor
public class MainModule {

    private final RunnimalApplication runnimalApplication;

    @Provides
    @Singleton
    Context context() {
        return runnimalApplication;
    }

    @Provides
    @Singleton
    TrainingService trainingService(TrainingServiceImpl trainingService) {
        return trainingService;
    }

    @Provides
    @Singleton
    TrainingRepository trainingRepository(TrainingRepositoryImpl trainingRepository) {
        return trainingRepository;
    }

    @Provides
    @Singleton
    RankingRepository rankingRepository(RankingRepositoryImpl rankingRepository) {
        return rankingRepository;
    }

    @Provides
    @Singleton
    RankingService rankingService(RankingServiceImpl rankingService) {
        return rankingService;
    }


    @Provides
    @Singleton
    RunnimalApi runnimalApi(LocalRunnimalApiImpl runnimalApi) {
        return runnimalApi;
    }

    @Provides
    @Singleton
    JacksonFactory jacksonFactory() {
        return new JacksonFactory() //
                .registerJavaTime();
    }

    @Provides
    @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides
    @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }
}
