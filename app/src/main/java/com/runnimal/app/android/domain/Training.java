package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Training {

    @JsonAlias("_id")
    private String id;
    private String name;
    private String description;
    private List<String> steps = new ArrayList<>();
}
