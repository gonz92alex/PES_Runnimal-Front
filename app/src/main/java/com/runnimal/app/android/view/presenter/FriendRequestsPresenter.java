package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.service.FriendshipService;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.converter.FriendshipViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class FriendRequestsPresenter extends Presenter<FriendRequestsPresenter.View> {

    private FriendshipService friendshipService;

    @Inject
    public FriendRequestsPresenter(MediaService mediaService, FriendshipService friendshipService) {
        super(mediaService);
        this.friendshipService = friendshipService;
    }
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        //ToDo añadir
        friendshipService.listRequestsFriendship(new DisposableObserver<List<Friendship>>() {

            @Override
            public void onNext(List<Friendship> users) {
                List<FriendshipViewModel> friendshipViewModels = ConverterUtils.convert(users, FriendshipViewModelConverter::convert);
                getView().showUsersList(friendshipViewModels);
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

    public void onTrainingClicked(FriendshipViewModel friend) {
        getView().openUserScreen(friend);
    }

    public void acceptFriend(String id) {
        friendshipService.acceptFriend(id,new DisposableObserver<String>() {

            @Override
            public void onNext(String users) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
    public void rejectFriend(String id) {
        friendshipService.rejectFriend(id,new DisposableObserver<String>() {

            @Override
            public void onNext(String users) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public interface View extends Presenter.View {

        void showUsersList(List<FriendshipViewModel> usersList);

        void openUserScreen(FriendshipViewModel user);
    }
}