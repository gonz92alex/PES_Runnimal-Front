package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class Walk {

    @JsonAlias("_id")
    private String id;
    private List<LatLon> route;
    private Instant start;
    private Instant end;
    private float distance;
    private Owner owner; //TODO: User
}
