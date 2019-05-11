package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Ranking;

import com.runnimal.app.android.service.RankingServiceImpl;
import com.runnimal.app.android.service.TrainingServiceImpl;
import com.runnimal.app.android.view.presenter.Presenter;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.RankingViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class RankingPresenter extends Presenter<RankingPresenter.View>{

    private RankingServiceImpl rankingService;

    @Inject
    public RankingPresenter(RankingServiceImpl rankingService) {
        this.rankingService = rankingService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        rankingService.list(new DisposableObserver<List<Ranking>>() {

            @Override
            public void onNext(List<Ranking> rankings) {
                List<RankingViewModel> rankingViewModels = RankingViewModelConverter.convert(rankings);
                getView().showRankingList(rankingViewModels);
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


    public void onUserClicked(RankingViewModel ranking) {
        getView().openUserScreen(ranking);
    }

    public void destroy() {
        setView(null);
    }


    public interface View extends Presenter.View {

        void showRankingList(List<RankingViewModel> trainingList);

        void openUserScreen(RankingViewModel training);
    }

}