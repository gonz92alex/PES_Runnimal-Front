package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetModifyPresenter extends Presenter<PetModifyPresenter.View> {

    private PetService petsService;
    @Setter
    private String petId;

    @Inject
    public PetModifyPresenter(MediaService mediaService, PetService petsService) {
        super(mediaService);
        this.petsService = petsService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        petsService.get(petId,
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

    public void modifyPet(Pet pet) {
        petsService.modify(pet, //
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
                        getView().hideLoading();
                    }
                });
    }

    public interface View extends Presenter.View {

        void showPet(PetViewModel pet);
    }
}
