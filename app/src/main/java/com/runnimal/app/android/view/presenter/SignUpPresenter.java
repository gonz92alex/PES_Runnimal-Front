package com.runnimal.app.android.view.presenter;

import android.content.SharedPreferences;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class SignUpPresenter extends Presenter<SignUpPresenter.View> {

    private OwnerService ownerService;
    @Inject
    public SignUpPresenter(MediaService mediaService, OwnerService ownerService) {
        super(mediaService);
        this.ownerService = ownerService;
    }

    public void createOwner(Owner owner) {
        ownerService.create(owner, //
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        getView().successfullyCreated(OwnerViewModelConverter.convert(owner));
                        try {
                            JSONObject res = new JSONObject(message);
                            getView().setToken(res.getString("token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

        void successfullyCreated(OwnerViewModel owner);
        void setToken(String message);
    }
}
