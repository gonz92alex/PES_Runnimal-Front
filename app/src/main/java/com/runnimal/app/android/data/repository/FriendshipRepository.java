package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Training;

import java.util.List;

import io.reactivex.Observable;

public interface FriendshipRepository {

    Observable<List<Friendship>> list();

    Observable<String> acceptFriend(String id);
    Observable<String> rejectFriend(String id);

    Observable<List<Friendship>> listFriendship();
}
