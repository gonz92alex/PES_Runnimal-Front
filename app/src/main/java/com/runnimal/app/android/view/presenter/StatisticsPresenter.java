package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.domain.StatsWalks;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.StatisticsService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.view.viewmodel.StatisticsViewModel;
import com.runnimal.app.android.view.viewmodel.StatisticsWalksViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.StatisticsViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.StatisticsWalksViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class StatisticsPresenter extends Presenter<StatisticsPresenter.View> {

    private StatisticsService statisticsService;



    @Inject
    public StatisticsPresenter(MediaService mediaService, StatisticsService statisticsService) {
        super(mediaService);
        this.statisticsService = statisticsService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        statisticsService.getTrainingStats(new DisposableObserver<StatsTraining>() {

            @Override
            public void onNext(StatsTraining statsTraining) {
                StatisticsViewModel statisticsViewModel = StatisticsViewModelConverter.convert(statsTraining);
                getView().showStats(statisticsViewModel);
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



        statisticsService.getWalkStats(new DisposableObserver<StatsWalks>() {

            @Override
            public void onNext(StatsWalks statsWalks) {
                StatisticsWalksViewModel statisticsWalksViewModel = StatisticsWalksViewModelConverter.convert(statsWalks);
                getView().showStatsWalks(statisticsWalksViewModel);
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

        void showStats(StatisticsViewModel stats);

        void showStatsWalks(StatisticsWalksViewModel stats);
    }
}
