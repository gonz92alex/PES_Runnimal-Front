package com.runnimal.app.android.data.repository;

import com.runnimal.app.android.domain.Friend;


import java.util.List;

import io.reactivex.Observable;

public interface FriendsRepository {

    Observable<List<Friend>> list();

    Observable<Friend> get(String id);
}
