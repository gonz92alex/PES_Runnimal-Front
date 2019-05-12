package com.runnimal.app.android.service;

import android.graphics.Bitmap;

import io.reactivex.observers.DisposableObserver;

public interface MediaService {

    void uploadImage(Bitmap image, String path, DisposableObserver<String> callback);
}
