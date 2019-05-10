package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String race;
    private int birth;
    private PetSize size;

    private Owner owner;
}
