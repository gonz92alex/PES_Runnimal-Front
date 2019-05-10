package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.PetsService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PetViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PetDetailPresenter extends Presenter<PetDetailPresenter.View> {

    private PetsService petsService;
    @Setter
    private String petId;

    @Inject
    public PetDetailPresenter(PetsService petsService) {
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

    public interface View extends Presenter.View {

        void showPet(PetViewModel pet);
    }
}
