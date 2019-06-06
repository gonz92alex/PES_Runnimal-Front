package com.runnimal.app.android.service.impl;

import android.util.Log;

import com.runnimal.app.android.data.repository.WalkRepository;
import com.runnimal.app.android.domain.LatLon;
import com.runnimal.app.android.domain.Walk;
import com.runnimal.app.android.service.AbstractService;
import com.runnimal.app.android.service.WalkService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import lombok.Getter;

public class WalkServiceImpl extends AbstractService implements WalkService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT) //
            .withLocale(Locale.forLanguageTag("es-ES")) //
            .withZone(ZoneId.of("Europe/Paris"));

    private final WalkRepository walkRepository;
    @Getter
    private final List<LatLon> route;

    private Walk walk = null;

    @Inject
    public WalkServiceImpl(@Named("executor_thread") Scheduler executorThread, //
                           @Named("ui_thread") Scheduler uiThread, //
                           WalkRepository walkRepository) {
        super(executorThread, uiThread);
        this.walkRepository = walkRepository;
        this.route = new ArrayList<>();
    }

    @Override
    public void list(DisposableObserver<List<Walk>> callback) {
        execute(walkRepository.list(), callback);
    }

    @Override
    public void start() {
        Log.d("WALK", "start");
        walk = new Walk();
        walk.setStart(Instant.now());
        route.clear();
    }

    @Override
    public void addPoint(LatLon latLon) {
        Log.d("WALK", "add [" + latLon.getLatitude() + ", " + latLon.getLongitude() + "]");
        route.add(latLon);
    }

    @Override
    public void end(float distance, DisposableObserver<Walk> callback) {
        Log.d("WALK", "end");
        if (route.size() > 1) {
            walk.setRoute(route);
            walk.setEnd(Instant.now());
            walk.setDistance(distance);
            walk.setDuration(-1);

            String title = DATE_TIME_FORMATTER.format(walk.getStart()) + " - " + DATE_TIME_FORMATTER.format(walk.getEnd());
            walk.setTitle(title);

            execute(walkRepository.save(walk), callback);
        }
        else {
            callback.onError(new RuntimeException("Invalid walk"));
        }
    }
}
