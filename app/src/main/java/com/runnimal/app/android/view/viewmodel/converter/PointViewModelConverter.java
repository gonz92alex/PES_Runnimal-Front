package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.view.viewmodel.PointViewModel;

public class PointViewModelConverter {

    public static PointViewModel convert(Point point) {
        return new PointViewModel() //
                .setTitle(point.getTitle()) //
                .setDescription(point.getDescription()) //
                .setType(point.getType()) //
                .setPhotoUrl(point.getPhotoUrl()) //
                .setLat(point.getCoord()[0]) //
                .setLon(point.getCoord()[1]);
    }
}
