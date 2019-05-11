package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.domain.Owner;

import java.util.List;

import io.reactivex.Observable;

public interface OwnerRepository {

    Observable<Owner> get(String id);

    Observable<List<FriendRequest>> getFriendRequests(String ownerEmail);

    Observable<Boolean> isFriend(String friendEmail);

    Observable<String> createFriendRequest(String requestedEmail);

    Observable<String> deteleFriend(String ownerId);
}
