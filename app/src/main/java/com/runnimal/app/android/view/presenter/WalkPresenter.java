package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Walk;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.WalkService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;
import com.runnimal.app.android.view.viewmodel.converter.WalkViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class WalkPresenter extends Presenter<WalkPresenter.View> {

    private WalkService walkService;

    @Setter
    private String ownerEmail;

    @Inject
    public WalkPresenter(MediaService mediaService, WalkService walkService) {
        super(mediaService);
        this.walkService = walkService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        walkService.list(new DisposableObserver<List<Walk>>() {

            @Override
            public void onNext(List<Walk> walks) {
                List<WalkViewModel> walkViewModels = ConverterUtils.convert(walks, WalkViewModelConverter::convert);
                getView().showWalksList(walkViewModels);
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

        void showWalksList(List<WalkViewModel> walks);

    }
}
