package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.domain.StatsWalks;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.StatisticsViewModel;
import com.runnimal.app.android.view.viewmodel.StatisticsWalksViewModel;

import java.net.URI;


public class StatisticsWalksViewModelConverter {

    public static StatisticsWalksViewModel convert(StatsWalks statsWalks) {
        if (statsWalks == null) {
            return null;
        }

        StatisticsWalksViewModel statisticsWalksViewModel = new StatisticsWalksViewModel() //
                .setNumberWalks(statsWalks.getWalksNumber())
                .setDistanceWalks(statsWalks.getTotaldistance())
                .setDurationWalks(statsWalks.getTotalduration());



        return statisticsWalksViewModel;
    }
}
