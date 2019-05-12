package com.runnimal.app.android.data.repository;

import android.graphics.Bitmap;

import io.reactivex.Observable;

public interface MediaRepository {

    Observable<String> uploadImage(Bitmap image, String path);
}
