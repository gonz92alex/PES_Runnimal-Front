package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.Ranking;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class FriendsViewModelConverter {

    public static FriendsViewModel convert(Friend friend) {
        FriendsViewModel friendsViewModel = new FriendsViewModel() //
                .setId(friend.getId()) //
                .setName(friend.getName())
                .setImageUrl(friend.getImageUrl());

        return friendsViewModel;
    }

    public static List<FriendsViewModel> convert(List<Friend> friend) {
        return friend.stream() //
                .map(FriendsViewModelConverter::convert) //
                .collect(Collectors.toList());
    }
}
