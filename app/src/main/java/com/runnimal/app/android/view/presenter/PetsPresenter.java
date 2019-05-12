package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PetService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetsPresenter extends Presenter<PetsPresenter.View> {

    private PetService petsService;

    @Setter
    private String ownerEmail;

    @Inject
    public PetsPresenter(MediaService mediaService, PetService petsService) {
        super(mediaService);
        this.petsService = petsService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        petsService.list(ownerEmail, new DisposableObserver<List<Pet>>() {

            @Override
            public void onNext(List<Pet> pets) {
                List<PetViewModel> petViewModels = ConverterUtils.convert(pets, PetViewModelConverter::convert);
                getView().showPetsList(petViewModels);
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

    public void onPetClicked(PetViewModel pet) {
        getView().openPetScreen(pet);
    }

    public interface View extends Presenter.View {

        void showPetsList(List<PetViewModel> pets);

        void openPetScreen(PetViewModel pet);
    }
}
