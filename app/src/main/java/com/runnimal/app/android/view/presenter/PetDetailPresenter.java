package com.runnimal.app.android.view.presenter;

import android.util.Log;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetDetailPresenter extends Presenter<PetDetailPresenter.View> {

    private PetService petsService;
    @Setter
    private String petId;
    @Setter
    private String OwnerEmail;

    @Inject
    public PetDetailPresenter(MediaService mediaService, PetService petsService) {
        super(mediaService);
        this.petsService = petsService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        petsService.get(petId, OwnerEmail,
                new DisposableObserver<Pet>() {

                    @Override
                    public void onNext(Pet pet) {
                        PetViewModel petViewModel = PetViewModelConverter.convert(pet);
                        getView().showPet(petViewModel);
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

    public void deleteOwner(String id, String mail) {
        petsService.deleteOwner(id, mail,
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String response) {
                        //toDO
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //toDO
                    }
                });
    }


    public interface View extends Presenter.View {

        void showPet(PetViewModel pet);
    }
}
