package com.runnimal.app.android.view.viewmodel;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =  true)
public class SearchViewModel {

    private String id;
    private String name;
    private URI imageUrl;
}
