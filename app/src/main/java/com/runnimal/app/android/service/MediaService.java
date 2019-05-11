package com.runnimal.app.android.service;

import android.graphics.Bitmap;

import io.reactivex.observers.DisposableObserver;

public interface MediaService {

    void uploadImage(final Bitmap image, DisposableObserver<String> callback);
}
