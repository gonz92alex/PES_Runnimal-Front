package com.runnimal.app.android.view.viewmodel;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatisticsWalksViewModel {

    private String numberWalks;
    private String durationWalks;
    private String distanceWalks;
}
