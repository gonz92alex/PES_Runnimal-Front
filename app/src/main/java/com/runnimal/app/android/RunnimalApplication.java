package com.runnimal.app.android;

import android.app.Application;

import com.runnimal.app.android.di.components.DaggerMainComponent;
import com.runnimal.app.android.di.components.MainComponent;
import com.runnimal.app.android.di.modules.MainModule;

import lombok.Getter;

public class RunnimalApplication extends Application {

    @Getter
    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
    }
}
