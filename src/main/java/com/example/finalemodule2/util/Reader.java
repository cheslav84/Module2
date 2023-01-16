package com.example.finalemodule2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public abstract class Reader {

    public List<Map<String, Object>> readData(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("Empty file name.");
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        List<Map<String, Object>> data;
        try (InputStream input = contextClassLoader.getResourceAsStream(fileName);
             BufferedReader bf = new BufferedReader(new InputStreamReader(input))) {
            data = parseData(bf);
        }
        return data;
    }

    protected abstract List<Map<String, Object>> parseData(BufferedReader bf) throws IOException;

}
