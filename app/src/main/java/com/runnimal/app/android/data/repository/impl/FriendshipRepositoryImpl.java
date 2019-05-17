package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.FriendshipRepository;
import com.runnimal.app.android.data.repository.TrainingRepository;
import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class FriendshipRepositoryImpl implements FriendshipRepository {

    private final RunnimalApi api;

    @Inject
    public FriendshipRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Friendship>> list() {
        return Observable.create(emitter -> {
            api.listFriendshipRequests(new RunnimalApi.RunnimalApiCallback<List<Friendship>>() {
                @Override
                public void responseOK(List<Friendship> friendships) {
                    emitter.onNext(friendships);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            });
        });
    }

    @Override
    public Observable<String> acceptFriend(String id){
        return Observable.create(emitter -> {
            api.acceptFriend(id, //
                    new RunnimalApi.RunnimalApiCallback<String>() {
                        @Override
                        public void responseOK(String message) {
                            emitter.onNext(message);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Observable<String> rejectFriend(String id)  {
        return Observable.create(emitter -> {
        api.rejectFriendship(id, //
                new RunnimalApi.RunnimalApiCallback<String>() {
                    @Override
                    public void responseOK(String message) {
                        emitter.onNext(message);
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
