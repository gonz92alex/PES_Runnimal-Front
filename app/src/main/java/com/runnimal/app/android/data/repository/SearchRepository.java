package com.runnimal.app.android.data.repository;


import com.runnimal.app.android.domain.User;


import java.util.List;

import io.reactivex.Observable;

public interface SearchRepository {

    Observable<List<User>> list();

    Observable<User> get(String id);
}
