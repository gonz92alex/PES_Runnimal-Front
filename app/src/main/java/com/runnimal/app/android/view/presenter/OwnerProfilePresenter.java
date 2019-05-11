package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class OwnerProfilePresenter extends Presenter<OwnerProfilePresenter.View> {

    private OwnerService ownerService;
    
    @Setter
    private String ownerId;

    @Inject
    public OwnerProfilePresenter(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        ownerService.get(ownerId,
                new DisposableObserver<Owner>() {

                    @Override
                    public void onNext(Owner owner) {
                        OwnerViewModel ownerViewModel = OwnerViewModelConverter.convert(owner);
                        getView().showOwner(ownerViewModel);
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

        void showOwner(OwnerViewModel owner);
    }
}
