package com.runnimal.app.android.domain;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =  true)
public class Training {

    private String id;
    private String name;
    private String description;
    private URI imageUrl;
    private final List<String> steps = new ArrayList<>();
}
