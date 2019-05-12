package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.FriendRequestState;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.OwnerService;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.converter.OwnerViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import lombok.Setter;

public class OwnerDetailPresenter extends Presenter<OwnerDetailPresenter.View> {

    private OwnerService ownerService;

    @Setter
    private String ownerId;
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

    public void checkFriendRequestState() {
        String currentUserId = SingletonSession.Instance().getId();
        ownerService.getFriendRequests(ownerEmail,
                new DisposableObserver<List<FriendRequest>>() {

                    @Override
                    public void onNext(List<FriendRequest> requests) {
                        if (requests.stream() //
                                .anyMatch(r -> r.getRequestedId().equals(currentUserId) || r.getRequestingId().equals(currentUserId))) {
                            getView().showFriendRequestState(FriendRequestState.PENDING);
                        } else {
                            checkFriend();
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
        ownerService.deleteFriend(ownerId,
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

    private void checkFriend() {
        ownerService.isFriend(ownerEmail, new DisposableObserver<Boolean>() {

            @Override
            public void onNext(Boolean isFriend) {
                if (isFriend) {
                    getView().showFriendRequestState(FriendRequestState.OK);
                } else {
                    getView().showFriendRequestState(FriendRequestState.KO);
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
