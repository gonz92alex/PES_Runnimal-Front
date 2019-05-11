package com.runnimal.app.android.data.repository.impl;

import android.graphics.Bitmap;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.MediaRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MediaRepositoryImpl implements MediaRepository {

    private final RunnimalApi api;

    @Inject
    public MediaRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<String> uploadImage(final Bitmap image) {
        return Observable.create(emitter -> {
            api.uploadImage(image, new RunnimalApi.RunnimalApiCallback<String>() {
                @Override
                public void responseOK(String message) {
                    emitter.onNext(message);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            });
        });
    }
}
