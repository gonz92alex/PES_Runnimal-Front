package com.runnimal.app.android.view.presenter;

import android.graphics.Bitmap;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetAddPresenter extends Presenter<PetAddPresenter.View> {

    private PetService petsService;
    private MediaService mediaService;
    @Setter
    private String petId;

    @Inject
    public PetAddPresenter(PetService petsService, MediaService mediaService) {
        this.mediaService = mediaService;
        this.petsService = petsService;
    }

    public void addPet(Pet pet) {
        petsService.modify(pet, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        getView().successfullyCreated(PetViewModelConverter.convert(pet));
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    public void uploadImage(final Bitmap image) {
        mediaService.uploadImage(image, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        //TODO
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface View extends Presenter.View {

        void successfullyCreated(PetViewModel pet);
    }
}
