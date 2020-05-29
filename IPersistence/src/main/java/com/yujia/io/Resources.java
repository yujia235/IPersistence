package com.yujia.io;

import java.io.InputStream;

public class Resources {

    public static InputStream load(String url) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(url);
    }
}
