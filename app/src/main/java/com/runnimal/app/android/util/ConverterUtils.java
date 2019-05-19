package com.runnimal.app.android.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConverterUtils {

    public static <T, R> List<R> convert(List<T> list, Function<T, R> converter) {
        return list.stream() //
                .map(converter) //
                .collect(Collectors.toList());
    }
}
