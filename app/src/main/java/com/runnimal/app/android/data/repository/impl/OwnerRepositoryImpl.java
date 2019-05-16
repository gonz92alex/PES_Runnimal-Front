package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.OwnerRepository;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class OwnerRepositoryImpl implements OwnerRepository {

    private final RunnimalApi api;

    @Inject
    public OwnerRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<String> login(String email, String password) {
        return Observable.create(emitter -> {
            api.login(email, password, //
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
    public Observable<List<Owner>> list() {
        return Observable.create(emitter -> {
            api.listOwners(new RunnimalApi.RunnimalApiCallback<List<Owner>>() {
                               @Override
                               public void responseOK(List<Owner> owners) {
                                   emitter.onNext(owners);
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
    public Observable<List<Owner>> listRequests() {
        return Observable.create(emitter -> {
            api.listRequests(new RunnimalApi.RunnimalApiCallback<List<Owner>>() {
                @Override
                public void responseOK(List<Owner> owners) {
                    emitter.onNext(owners);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            });
        });    }

    @Override
    public Observable<Owner> get(String owner) {
        return Observable.create(emitter -> {
            api.getOwner(owner, //
                    new RunnimalApi.RunnimalApiCallback<Owner>() {
                        @Override
                        public void responseOK(Owner owner) {
                            emitter.onNext(owner);
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
    public Observable<String> modify(Owner owner) {
        return Observable.create(emitter -> {
            api.modifyOwner(owner, //
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
    public Observable<String> create(Owner owner) {
        return Observable.create(emitter -> {
            api.createOwner(owner, //
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

    public Observable<List<FriendRequest>> getFriendRequests(String ownerEmail) {
        return Observable.create(emitter -> {
            api.getFriendRequests(ownerEmail, //
                    new RunnimalApi.RunnimalApiCallback<List<FriendRequest>>() {
                        @Override
                        public void responseOK(List<FriendRequest> friendRequests) {
                            emitter.onNext(friendRequests);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    public Observable<Boolean> isFriend(String friendEmail) {
        return Observable.create(emitter -> {
            api.isFriend(friendEmail, //
                    new RunnimalApi.RunnimalApiCallback<Boolean>() {
                        @Override
                        public void responseOK(Boolean isFriend) {
                            emitter.onNext(isFriend);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    public Observable<String> createFriendRequest(String requestedEmail) {
        return Observable.create(emitter -> {
            api.createFriendRequest(requestedEmail, //
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

    public Observable<String> deteleFriend(String ownerId) {
        return Observable.create(emitter -> {
            api.deleteFriend(ownerId, //
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
