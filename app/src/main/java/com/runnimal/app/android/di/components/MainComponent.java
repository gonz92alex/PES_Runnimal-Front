package com.runnimal.app.android.di.components;

import android.content.Context;

import com.runnimal.app.android.di.modules.MainModule;
import com.runnimal.app.android.di.modules.RepositoryModule;
import com.runnimal.app.android.di.modules.ServiceModule;
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
@Component(modules = {MainModule.class, ServiceModule.class, RepositoryModule.class})
public interface MainComponent {

    void inject(TrainingsActivity activity);

    void inject(RankingActivity activity);

    void inject(TrainingDetailActivity activity);

    void inject(PetsActivity activity);

    void inject(PetDetailActivity activity);

    void inject(PetModifyActivity activity);

    Context context();
}
