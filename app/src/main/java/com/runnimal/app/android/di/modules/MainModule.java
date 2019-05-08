package com.runnimal.app.android.di.modules;

import android.content.Context;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.api.impl.RunnimalApiImpl;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.data.repository.impl.TrainingRepositoryImpl;

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
    Context provideApplicationContext() {
        return runnimalApplication;
    }

    @Provides
    @Singleton
    TrainingRepository provideTrainingRepository(TrainingRepositoryImpl trainingRepository) {
        return trainingRepository;
    }

    @Provides
    @Singleton
    RunnimalApi provideRunnimalApi(RunnimalApiImpl runnimalApi) {
        return runnimalApi;
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
