package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.FriendsService;
import com.runnimal.app.android.service.FriendshipService;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.FriendsViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.FriendshipViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class FriendsPresenter extends Presenter<FriendsPresenter.View> {

    private FriendshipService friendsService;

    @Inject
    public FriendsPresenter(MediaService mediaService, FriendshipService friendsService) {
        super(mediaService);
        this.friendsService = friendsService;
    }
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        friendsService.listFriendship(new DisposableObserver<List<Friendship>>() {

            @Override
            public void onNext(List<Friendship> friends) {
                List<FriendshipViewModel> friendsViewModels = ConverterUtils.convert(friends, FriendshipViewModelConverter::convert);
                getView().showFriendsList(friendsViewModels);
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

    public interface View extends Presenter.View {

        void showFriendsList(List<FriendshipViewModel> friendsList);

        void openUserScreen(FriendshipViewModel training);
    }
}