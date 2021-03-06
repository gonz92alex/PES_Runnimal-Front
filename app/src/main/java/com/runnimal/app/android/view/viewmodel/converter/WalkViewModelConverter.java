package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Walk;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;

public class WalkViewModelConverter {

    public static WalkViewModel convert(Walk walk) {

        WalkViewModel walkViewModel = new WalkViewModel() //
                .setId(walk.getId()) //
                .setTitle(walk.getTitle()) //
                .setRoute(walk.getRoute()) //
                .setStart(walk.getStart()) //
                .setEnd(walk.getEnd()) //
                .setDistance(walk.getDistance()) //
                .setDuration(walk.getDuration());

        walkViewModel.setOwner(OwnerViewModelConverter.convert(walk.getOwner()));

        return walkViewModel;
    }
}
