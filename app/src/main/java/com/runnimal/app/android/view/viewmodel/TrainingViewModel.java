package com.runnimal.app.android.view.viewmodel;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =  true)
public class TrainingViewModel {

    private String id;
    private String name;
    private String description;
    private URI imageUrl;
    private final List<String> steps = new ArrayList<>();
}
