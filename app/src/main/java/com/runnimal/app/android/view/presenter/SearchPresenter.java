package com.runnimal.app.android.view.presenter;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;
import com.runnimal.app.android.service.FriendsService;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.SearchService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.util.ConverterUtils;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.runnimal.app.android.view.viewmodel.converter.FriendsViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.SearchViewModelConverter;
import com.runnimal.app.android.view.viewmodel.converter.TrainingViewModelConverter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class SearchPresenter extends Presenter<SearchPresenter.View> {

    private SearchService searchService;

    @Inject
    public SearchPresenter(MediaService mediaService, SearchService searchService) {
        super(mediaService);
        this.searchService = searchService;
    }
    @Override
    public void initialize() {
        super.initialize();
        getView().showLoading();
        searchService.list(new DisposableObserver<List<User>>() {

            @Override
            public void onNext(List<User> users) {
                List<SearchViewModel> searchViewModels = ConverterUtils.convert(users, SearchViewModelConverter::convert);
                getView().showUsersList(searchViewModels);
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

    public void onTrainingClicked(SearchViewModel friend) {
        getView().openUserScreen(friend);
    }

    public interface View extends Presenter.View {

        void showUsersList(List<SearchViewModel> usersList);

        void openUserScreen(SearchViewModel user);
    }
}