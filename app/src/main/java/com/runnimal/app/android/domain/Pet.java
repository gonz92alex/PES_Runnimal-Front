package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Pet {

    public enum PetSize {

        SMALL, MEDIUM, BIG
    }

    @JsonAlias("_id")
    private String id;
    private String name;
    private String description;

    private int weight;
    @JsonAlias("race")
    private String breed;
    private int birth;
    private PetSize size;

    private Owner owner;
}
