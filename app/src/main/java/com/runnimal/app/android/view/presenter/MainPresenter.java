package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class MainPresenter extends Presenter<MainPresenter.View> {

    private OwnerService ownerService;


    @Inject
    public MainPresenter(MediaService mediaService, OwnerService ownerService) {
        super(mediaService);
        this.ownerService = ownerService;
    }


    public interface View extends Presenter.View {

        void loginOk(Owner owner);
    }
}
