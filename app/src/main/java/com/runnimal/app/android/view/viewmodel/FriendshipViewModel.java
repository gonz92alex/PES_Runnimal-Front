package com.runnimal.app.android.view.viewmodel;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FriendshipViewModel {

    private String idFriendship;
    private String idUser;
    private String alias;
    private String email;
    private URI imageUrl;
}