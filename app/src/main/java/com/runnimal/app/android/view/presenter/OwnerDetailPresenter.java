package com.runnimal.app.android.view.presenter;

import android.util.Log;

import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.FriendRequestState;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class OwnerDetailPresenter extends Presenter<OwnerDetailPresenter.View> {

    private OwnerService ownerService;

    @Setter
    private String ownerId;
    @Setter
    private String friendshipID;
    @Setter
    private String ownerEmail;

    @Inject
    public OwnerDetailPresenter(MediaService mediaService, OwnerService ownerService) {
        super(mediaService);
        this.ownerService = ownerService;
    }

    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        ownerService.get(ownerEmail,
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

    public void createFriendRequest() {
        ownerService.createFriendRequest(ownerEmail,
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        //TODO
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

    public void deleteFriend() {
        ownerService.deleteFriend(friendshipID,
                new DisposableObserver<String>() {

                    @Override
                    public void onNext(String message) {
                        getView().showFriendRequestState(FriendRequestState.KO);
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

    public void checkFriend() {
        ownerService.isFriend(ownerEmail, new DisposableObserver<String>() {
            @Override
            public void onNext(String res) {
                try {
                    JSONArray jsonArray = new JSONArray(res);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("type").equals("FRIEND")) {
                        friendshipID = jsonObject.getString("_id");
                        getView().showFriendRequestState(FriendRequestState.FRIEND);
                    } else {
                        getView().showFriendRequestState(FriendRequestState.PENDING);
                    }
                } catch (JSONException e) {
                    getView().showFriendRequestState(FriendRequestState.KO);
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

        void showOwner(OwnerViewModel owner);

        void showFriendRequestState(FriendRequestState friendRequestType);
    }
}
