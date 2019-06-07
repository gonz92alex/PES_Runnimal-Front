package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FriendRequest {

    @JsonAlias("_id")
    private String id;
    private Owner user1;
    private FriendRequestState type;
}
