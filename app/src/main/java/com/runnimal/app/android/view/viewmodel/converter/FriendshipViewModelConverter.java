package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;

import java.net.URI;


public class FriendshipViewModelConverter {

    public static FriendshipViewModel convert(Friendship friendship) {
        if (friendship == null) {
            return null;
        }

        FriendshipViewModel friendshipViewModel = new FriendshipViewModel() //
                .setIdUser(friendship.getId()) //
                .setAlias(friendship.getUser2().getAlias()) //
                .setEmail(friendship.getUser2().getEmail());

        friendshipViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + friendship.getUser2().getEmail()));

        return friendshipViewModel;
    }
}