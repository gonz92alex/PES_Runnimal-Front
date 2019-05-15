package com.runnimal.app.android.view.presenter;

import android.util.Log;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class PetAddPresenter extends Presenter<PetAddPresenter.View> {

    private PetService petsService;

    @Inject
    public PetAddPresenter(MediaService mediaService, PetService petsService) {
        super(mediaService);
        this.petsService = petsService;
    }

    public void addPet(Pet pet) {
        petsService.create(pet, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        Log.d("refactor", "onNext: akiii " + pet.getName());
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

    public interface View extends Presenter.View {

        void successfullyCreated(PetViewModel pet);
    }
}
