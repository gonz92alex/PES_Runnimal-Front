package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public interface OwnerRepository {

    Observable<String> login(String email, String password);

    Observable<Owner> get(String id);

    Observable<String> modify(Owner owner);

    Observable<String> create(Owner owner);

    Observable<List<FriendRequest>> getFriendRequests(String ownerEmail);

    Observable<Boolean> isFriend(String friendEmail);

    Observable<String> createFriendRequest(String requestedEmail);

    Observable<String> deteleFriend(String ownerId);

    Observable<List<Owner>> list();

    Observable<List<Owner>>  listRequests();
}
