package com.runnimal.app.android.view.viewmodel;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TrainingViewModel {

    private final List<String> steps = new ArrayList<>();
    private String name;
    private String description;
    private URI imageUrl;
}
