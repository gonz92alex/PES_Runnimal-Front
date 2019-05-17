package com.runnimal.app.android.data.entity;

import lombok.Data;
import lombok.Getter;

@Data
public class Training {

    @Getter
    private final String attribute1;

    private int attribute2;


    public String test() {
        return "";
    }
}
