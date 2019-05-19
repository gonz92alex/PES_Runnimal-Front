package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FriendRequest {

    @JsonAlias("_id")
    private String id;
    private String requestingId;
    private String requestedId;
    private FriendRequest state; //TODO: añadir en la API
}
