package com.runnimal.app.android.domain;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class Walk {

    private List<LatLon> route;
    private Instant start;
    private Instant end;
    private int duration;
    private int distance;
    private Owner owner;
}
