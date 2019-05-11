package com.runnimal.app.android.di.components;

import android.content.Context;

import com.runnimal.app.android.di.modules.MainModule;
import com.runnimal.app.android.di.modules.DataModule;
import com.runnimal.app.android.di.modules.ServiceModule;
import com.runnimal.app.android.view.activity.PetAddActivity;
import com.runnimal.app.android.view.activity.PetDetailActivity;
import com.runnimal.app.android.view.activity.PetModifyActivity;
import com.runnimal.app.android.view.activity.PetsActivity;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.view.activity.RankingActivity;
import com.runnimal.app.android.view.activity.TrainingDetailActivity;
import com.runnimal.app.android.view.activity.TrainingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ServiceModule.class, DataModule.class})
public interface MainComponent {

    void inject(TrainingsActivity activity);

    void inject(RankingActivity activity);

    void inject(TrainingDetailActivity activity);

    void inject(PetsActivity activity);

    void inject(PetDetailActivity activity);

    void inject(PetModifyActivity activity);

    void inject(PetAddActivity activity);

    Context context();
}
