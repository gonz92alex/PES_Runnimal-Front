package com.runnimal.app.android.view.viewmodel.converter;

import com.runnimal.app.android.domain.Point;
import com.runnimal.app.android.view.viewmodel.PointViewModel;

public class PointViewConverter {

    public static PointViewModel convert(Point point) {
        return new PointViewModel() //
                .setTitle(point.getTitle()) //
                .setDescription(point.getDescription()) //
                .setType(point.getType()) //
                .setPhotoUrl(point.getPhotoUrl()) //
                .setCoord(point.getCoord());
    }
}
