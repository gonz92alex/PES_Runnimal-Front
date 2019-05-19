package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.FriendsRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class FriendsRepositoryImpl implements FriendsRepository {

    private final RunnimalApi api;

    @Inject
    public FriendsRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    public Observable<List<Friend>> list() {
        return Observable.create(emitter -> {
            api.listFriends(new RunnimalApi.RunnimalApiCallback<List<Friend>>() {
                @Override
                public void responseOK(List<Friend> friends) {
                    emitter.onNext(friends);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            }/* possible parametro de la api */);
        });
    }

    public Observable<Friend> get(String id) {
        return Observable.create(emitter -> {
            api.getFriend(id, //
                    new RunnimalApi.RunnimalApiCallback<Friend>() {
                        @Override
                        public void responseOK(Friend friend) {
                            emitter.onNext(friend);
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
