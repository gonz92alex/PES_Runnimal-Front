package com.runnimal.app.android.view.viewmodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PetViewModel {

    @JsonAlias("_id")
    private String id;
    private String name;
    private String description;
    private URI imageUrl;

    private int weight;
    private String breed;
    private int birth;
    private Pet.PetSize size;

    private OwnerViewModel owner;
}
