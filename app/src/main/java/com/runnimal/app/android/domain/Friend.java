package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Friend {

    @JsonAlias("_id")
    private String id;
    private String name;
    private URI imageUrl; //que alguien haga lo de la url bien

}
