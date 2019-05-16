package com.runnimal.app.android.view.presenter;

import android.provider.MediaStore;
import android.util.Log;

import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetAddOwnerPresenter extends Presenter<PetAddOwnerPresenter.View> {

    private PetService petsService;
    @Setter
    private String petId;

    @Inject
    public PetAddOwnerPresenter(MediaService mediaService, PetService petsService){
        super(mediaService);
        this.petsService = petsService;
    }

    @Override
    public void initialize(){
        super.initialize();
        getView().hideLoading();
    }

    public void addOwner(String email) {
        petsService.addOwner(petId, email, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String response) {
                        Log.d("refactor", "onNext: hecho");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("refactor", "onComplete: hecho");
                    }
                });
    }

    public interface View extends Presenter.View {

        void onOwnerAdded();
    }
}
