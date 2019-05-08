package com.runnimal.app.android.view.presenter;

import android.support.annotation.NonNull;

import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.mapper.TrainingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TrainingsPresenter extends Presenter<TrainingsPresenter.View> {

    private TrainingService trainingService;

    @Inject
    public TrainingsPresenter(@NonNull TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        trainingService.execute(new DisposableObserver<List<Training>>() {

            @Override
            public void onNext(List<Training> trainings) {
                List<TrainingViewModel> trainingViewModels = TrainingViewModelConverter.convert(trainings);
                getView().showTrainingList(trainingViewModels);
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

    public void onTrainingClicked(TrainingViewModel training) {
        getView().openTrainingScreen(training);
    }

    public void destroy() {
        this.trainingService.dispose();
        setView(null);
    }

    public interface View extends Presenter.View {

        void showTrainingList(List<TrainingViewModel> trainingList);

        void openTrainingScreen(TrainingViewModel training);
    }
}
