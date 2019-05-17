package com.runnimal.app.android.view.domain;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InfoWindowData {

    private String title;
    private String description;
    private URI photo;
}
