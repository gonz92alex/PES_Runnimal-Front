package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Pet {

    public enum PetSize {

        Small, Medium, Big
    }

    @JsonAlias("_id")
    private String id;
    private String name;
    private String description;

    private int weight;

    private String breed;

    private int birth;
    private PetSize size;

    private Owner owner;
    private Owner[] otherOwners;
}
