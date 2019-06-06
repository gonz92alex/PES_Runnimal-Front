package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class SearchPresenter extends Presenter<SearchPresenter.View> {

    private OwnerService ownerService;

    @Inject
    public SearchPresenter(MediaService mediaService, OwnerService ownerService) {
        super(mediaService);
        this.ownerService = ownerService;
    }
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        ownerService.list(new DisposableObserver<List<Owner>>() {

            @Override
            public void onNext(List<Owner> users) {
                List<OwnerViewModel> ownerViewModels = ConverterUtils.convert(users, OwnerViewModelConverter::convert);
                getView().showUsersList(ownerViewModels);
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

    public void onTrainingClicked(OwnerViewModel friend) {
        getView().openUserScreen(friend);
    }

    public interface View extends Presenter.View {

        void showUsersList(List<OwnerViewModel> usersList);

        void openUserScreen(OwnerViewModel user);
    }
}