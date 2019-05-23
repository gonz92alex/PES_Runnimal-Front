package com.runnimal.app.android.view.viewmodel;

import com.runnimal.app.android.domain.LatLon;
import com.runnimal.app.android.domain.Owner;

import java.time.Instant;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =  true)
public class WalkViewModel {

    private String title;
    private List<LatLon> route;
    private Instant start;
    private Instant end;
    private int distance;
    private OwnerViewModel owner;
}
