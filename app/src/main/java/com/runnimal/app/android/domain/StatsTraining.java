package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatsTraining {

    @JsonAlias("_id")
    private String completedTrainnings; //que alguien haga lo de la url bien
}
