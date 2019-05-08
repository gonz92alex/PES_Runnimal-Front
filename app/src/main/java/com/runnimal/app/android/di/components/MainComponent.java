package com.runnimal.app.android.di.components;

import android.content.Context;

import com.runnimal.app.android.di.modules.MainModule;
import com.runnimal.app.android.view.activity.TrainingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(TrainingsActivity activity);

    Context context();
}
