package com.runnimal.app.android.di.components;

import android.content.Context;

import com.runnimal.app.android.di.modules.DataModule;
import com.runnimal.app.android.di.modules.MainModule;
import com.runnimal.app.android.di.modules.ServiceModule;
import com.runnimal.app.android.view.activity.FriendRequestsActivity;
import com.runnimal.app.android.view.activity.FriendsActivity;
import com.runnimal.app.android.view.activity.LoginActivity;
import com.runnimal.app.android.view.activity.MapActivity;
import com.runnimal.app.android.view.activity.OwnerDetailActivity;
import com.runnimal.app.android.view.activity.OwnerModifyActivity;
import com.runnimal.app.android.view.activity.PetAddActivity;
import com.runnimal.app.android.view.activity.PetDetailActivity;
import com.runnimal.app.android.view.activity.PetModifyActivity;
import com.runnimal.app.android.view.activity.PetsActivity;
import com.runnimal.app.android.view.activity.RankingActivity;
import com.runnimal.app.android.view.activity.SearchActivity;
import com.runnimal.app.android.view.activity.SignUpActivity;
import com.runnimal.app.android.view.activity.TrainingDetailActivity;
import com.runnimal.app.android.view.activity.TrainingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ServiceModule.class, DataModule.class})
public interface MainComponent {

    void inject(FriendRequestsActivity activity);

    void inject(SignUpActivity activity);

    void inject(LoginActivity activity);

    void inject(MapActivity activity);

    void inject(TrainingsActivity activity);

    void inject(RankingActivity activity);

    void inject(TrainingDetailActivity activity);

    void inject(PetsActivity activity);

    void inject(PetDetailActivity activity);

    void inject(PetModifyActivity activity);

    void inject(PetAddActivity activity);

    void inject(OwnerDetailActivity activity);

    void inject(OwnerModifyActivity activity);

    void inject(FriendsActivity activity);

    void inject(SearchActivity activity);

    Context context();
}
