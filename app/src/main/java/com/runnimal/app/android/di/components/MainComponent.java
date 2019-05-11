package com.runnimal.app.android.di.components;

import android.content.Context;

import com.runnimal.app.android.di.modules.MainModule;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.view.activity.RankingActivity;
import com.runnimal.app.android.view.activity.TrainingDetailActivity;
import com.runnimal.app.android.view.activity.TrainingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(TrainingsActivity activity);

    void inject(RankingActivity activity);

    void inject(TrainingDetailActivity activity);

    Context context();
}
