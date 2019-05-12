package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class OwnerModifyPresenter extends Presenter<OwnerModifyPresenter.View> {

    private OwnerService ownerService;
    @Setter
    private String ownerId;
    @Setter
    private String ownerEmail;

    @Inject
    public OwnerModifyPresenter(MediaService mediaService, OwnerService ownerService) {
        super(mediaService);
        this.ownerService = ownerService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
    }

    public void modifyOwner(Owner owner) {
        ownerService.modify(owner, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        getView().onUpdatedOwner(OwnerViewModelConverter.convert(owner));
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

        void onUpdatedOwner(OwnerViewModel owner);
    }
}
