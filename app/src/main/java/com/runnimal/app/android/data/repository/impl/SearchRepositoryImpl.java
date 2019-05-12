package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.FriendsRepository;
import com.runnimal.app.android.data.repository.SearchRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;
import com.runnimal.app.android.domain.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class SearchRepositoryImpl implements SearchRepository {

    private final RunnimalApi api;

    @Inject
    public SearchRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    public Observable<List<User>> list() {
        return Observable.create(emitter -> {
            api.listUsers(new RunnimalApi.RunnimalApiCallback<List<User>>() {
                @Override
                public void responseOK(List<User> users) {
                    emitter.onNext(users);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            }/* possible parametro de la api */);
        });
    }

    public Observable<User> get(String id) {
        return Observable.create(emitter -> {
            api.getUser(id, //
                    new RunnimalApi.RunnimalApiCallback<User>() {
                        @Override
                        public void responseOK(User user) {
                            emitter.onNext(user);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }
}
