package com.runnimal.app.android.view.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.runnimal.app.android.service.MediaService;

import io.reactivex.observers.DisposableObserver;

public class Presenter<T extends Presenter.View> {

    private T view;
    private MediaService mediaService;

    public Presenter(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void initialize() {

    }

    public void uploadImage(Bitmap image, String path) {
        Log.d("refactor", "uploadImage: ");
        mediaService.uploadImage(image, //
                path, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        //TODO
                        Log.d("refactor", "onNext: ");
                        getView().onUploadPhoto();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("refactor", "onComplete: ");
                    }
                });
    }

    public interface View {

        void showLoading();

        void hideLoading();

        default void onUploadPhoto() {
        }
    }
}