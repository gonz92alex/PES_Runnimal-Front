package com.runnimal.app.android.domain;

import com.fasterxml.jackson.annotation.JsonAlias;


import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =  true)
public class Ranking {

    @JsonAlias("_id")
    private String id;
    private String name;
    private String points;
    private URI imageUrl;

}
