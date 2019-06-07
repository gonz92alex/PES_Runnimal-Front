package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Owner {

    @JsonAlias("_id")
    private String id;
    private String alias;
    private String email;
    private String password;
}
