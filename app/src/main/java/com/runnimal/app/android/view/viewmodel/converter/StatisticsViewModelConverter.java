package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.StatisticsViewModel;

import java.net.URI;


public class StatisticsViewModelConverter {

    public static StatisticsViewModel convert(StatsTraining statsTraining) {
        if (statsTraining == null) {
            return null;
        }

        StatisticsViewModel statisticsViewModel = new StatisticsViewModel() //
                .setNumber(statsTraining.getNumber());



        return statisticsViewModel;
    }
}
