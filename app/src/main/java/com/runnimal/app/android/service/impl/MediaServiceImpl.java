package com.runnimal.app.android.service.impl;

import android.graphics.Bitmap;

import com.runnimal.app.android.data.repository.MediaRepository;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.MediaService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class MediaServiceImpl extends AbstractService implements MediaService {

    private final MediaRepository mediaRepository;

    @Inject
    public MediaServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                            @Named("ui_thread") Scheduler uiThread, //
                            MediaRepository mediaRepository) {
        super(executorThread, uiThread);
        this.mediaRepository = mediaRepository;
    }

    public void uploadImage(Bitmap image, String path, DisposableObserver<String> callback) {
        execute(mediaRepository.uploadImage(image, path), callback);
    }
}
