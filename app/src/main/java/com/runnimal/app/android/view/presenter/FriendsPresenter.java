package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.service.FriendsService;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.FriendsViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class FriendsPresenter extends Presenter<FriendsPresenter.View> {

    private FriendsService friendsService;

    @Inject
    public FriendsPresenter(MediaService mediaService, FriendsService friendsService) {
        super(mediaService);
        this.friendsService = friendsService;
    }
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        friendsService.list(new DisposableObserver<List<Friend>>() {

            @Override
            public void onNext(List<Friend> friends) {
                List<FriendsViewModel> friendsViewModels = ConverterUtils.convert(friends, FriendsViewModelConverter::convert);
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

    public void onTrainingClicked(FriendsViewModel friend) {
        getView().openUserScreen(friend);
    }

    public interface View extends Presenter.View {

        void showFriendsList(List<FriendsViewModel> friendsList);

        void openUserScreen(FriendsViewModel training);
    }
}