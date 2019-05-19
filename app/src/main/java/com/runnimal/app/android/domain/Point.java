package com.runnimal.app.android.domain;

import java.net.URI;

import lombok.Data;

@Data
public class Point {

    private String title;
    private String description;
    private PointType type;
    private URI photoUrl;
    private Double[] coord;
}
