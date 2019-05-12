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

    //toDo al enviar a la api lo esta enviando como breed por lo que peta
    @JsonAlias("race")
    private String breed;

    private int birth;
    private PetSize size;

    private Owner owner;
}
