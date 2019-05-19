package com.runnimal.app.android.view.viewmodel;

import com.runnimal.app.android.domain.PointType;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PointViewModel {

    private String title;
    private String description;
    private PointType type;
    private URI photoUrl;
    private Double lat;
    private Double lon;
}
