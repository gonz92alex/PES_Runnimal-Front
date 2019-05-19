package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.RankingService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.RankingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class RankingPresenter extends Presenter<RankingPresenter.View> {

    private RankingService rankingService;

    @Inject
    public RankingPresenter(MediaService mediaService, RankingService rankingService) {
        super(mediaService);
        this.rankingService = rankingService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        rankingService.list(new DisposableObserver<List<Ranking>>() {

            @Override
            public void onNext(List<Ranking> rankings){
            List<RankingViewModel> rankingViewModels = ConverterUtils.convert(rankings, RankingViewModelConverter::convert);
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

    public void localRank(String mail) {
        rankingService.localRank(mail, //
                new DisposableObserver<List<Ranking>>() {
                    @Override
                    public void onNext(List<Ranking> rankings){
                        List<RankingViewModel> rankingViewModels = ConverterUtils.convert(rankings, RankingViewModelConverter::convert);
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


    public interface View extends Presenter.View {

        void showRankingList(List<RankingViewModel> trainingList);

        void openUserScreen(RankingViewModel training);
    }

}