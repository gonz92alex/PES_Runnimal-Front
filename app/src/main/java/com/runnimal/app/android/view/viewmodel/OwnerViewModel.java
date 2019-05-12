package com.runnimal.app.android.view.viewmodel;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OwnerViewModel {

    private String id;
    private String alias;
    private String email;
    private URI imageUrl;
}
