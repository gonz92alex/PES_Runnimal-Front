package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.PointService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.PointViewModel;
import com.runnimal.app.android.view.viewmodel.converter.PointViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class PointsPresenter extends Presenter<PointsPresenter.View> {

    private PointService pointService;

    @Setter
    private String ownerEmail;

    @Inject
    public PointsPresenter(MediaService mediaService, PointService pointService) {
        super(mediaService);
        this.pointService = pointService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        pointService.list(new DisposableObserver<List<Point>>() {

            @Override
            public void onNext(List<Point> points) {
                List<PointViewModel> pointViewModels = ConverterUtils.convert(points, PointViewModelConverter::convert);
                getView().showPointsList(pointViewModels);
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

    public void onPointsChanged(List<PointViewModel> points) {
        getView().onPointsListChanged(points);
    }

    public interface View extends Presenter.View {

        void showPointsList(List<PointViewModel> points);

        void onPointsListChanged(List<PointViewModel> points);
    }
}
