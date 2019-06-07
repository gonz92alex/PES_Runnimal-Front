package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Friendship;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;

import java.net.URI;


public class FriendshipViewModelConverter {

    public static FriendshipViewModel convert(Friendship friendship) {
        if (friendship == null) {
            return null;
        }
        if(SingletonSession.Instance().getId().equals(friendship.getUser1().getId())) {
            FriendshipViewModel friendshipViewModel = new FriendshipViewModel() //
                    .setIdFriendship(friendship.getId()) //
                    .setAlias(friendship.getUser2().getAlias()) //
                    .setEmail(friendship.getUser2().getEmail());

            friendshipViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + friendship.getUser2().getEmail()));

            return friendshipViewModel;
        }
        else{
            FriendshipViewModel friendshipViewModel = new FriendshipViewModel() //
                    .setIdFriendship(friendship.getId()) //
                    .setAlias(friendship.getUser1().getAlias()) //
                    .setEmail(friendship.getUser1().getEmail());

            friendshipViewModel.setImageUrl(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + friendship.getUser1().getEmail()));

            return friendshipViewModel;
        }
    }
}