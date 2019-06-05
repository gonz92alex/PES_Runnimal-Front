package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatsWalks {

    @JsonAlias("_id")
    private String walksNumber;
    private String totalduration;
    private String totaldistance;//que alguien haga lo de la url bien
}
