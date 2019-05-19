package com.runnimal.app.android.util;

import java.io.InputStream;

public class IOUtils {

    public static InputStream getResource(String path) {
        return IOUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
