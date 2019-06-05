package com.runnimal.app.android.di.modules;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.api.impl.LocalRunnimalApiImpl;
import com.runnimal.app.android.data.api.impl.RunnimalApiImpl;
import com.runnimal.app.android.data.repository.FriendshipRepository;
import com.runnimal.app.android.data.repository.MediaRepository;
import com.runnimal.app.android.data.repository.OwnerRepository;
import com.runnimal.app.android.data.repository.PetRepository;
import com.runnimal.app.android.data.repository.PointRepository;
import com.runnimal.app.android.data.repository.RankingRepository;
import com.runnimal.app.android.data.repository.StatisticsRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.data.repository.impl.FriendshipRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.MediaRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.OwnerRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.PetRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.PointRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.RankingRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.StatisticsRepositoryImpl;
import com.runnimal.app.android.data.repository.impl.TrainingRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;

@Module
@RequiredArgsConstructor
public class DataModule {

    @Provides
    @Singleton
    RunnimalApi runnimalApi(RunnimalApiImpl runnimalApi) {
        return runnimalApi;
    }

    @Provides
    @Singleton
    RequestQueue requestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    @Provides
    @Singleton
    MediaRepository mediaRepository(MediaRepositoryImpl mediaRepository) {
        return mediaRepository;
    }

    @Provides
    @Singleton
    OwnerRepository ownerRepository(OwnerRepositoryImpl ownerRepository) {
        return ownerRepository;
    }

    @Provides
    @Singleton
    PetRepository petsRepository(PetRepositoryImpl petsRepository) {
        return petsRepository;
    }

    @Provides
    @Singleton
    RankingRepository rankingRepository(RankingRepositoryImpl rankingRepository) {
        return rankingRepository;
    }

    @Provides
    @Singleton
    TrainingRepository trainingRepository(TrainingRepositoryImpl trainingsRepository) {
        return trainingsRepository;
    }


    @Provides
    @Singleton
    PointRepository pointRepository(PointRepositoryImpl pointRepository) {
        return pointRepository;
    }



    @Provides
    @Singleton
    FriendshipRepository friendshipRepository(FriendshipRepositoryImpl friendshipRepository) {
        return friendshipRepository;
    }

    @Provides
    @Singleton
    StatisticsRepository statisticsRepository(StatisticsRepositoryImpl statisticsRepository) {
        return statisticsRepository;
    }

}
