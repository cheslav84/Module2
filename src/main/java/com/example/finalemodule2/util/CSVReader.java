package com.example.finalemodule2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class CSVReader extends Reader {
    private final String LINE_DELIMITER = ";";

    @Override
    protected List<Map<String, Object>> parseData(final BufferedReader bf) throws IOException {
        String line;
        List<String> csvLines = new ArrayList<>();

        while ((line = bf.readLine()) != null) {
            csvLines.add(line);
        }
        List<String[]> separatedData = splitData(csvLines);
        return mapData(separatedData);
    }

    private List<String[]> splitData(List<String> csvLines) {
        return csvLines.stream()
                .map(s -> s.split(LINE_DELIMITER))
                .toList();
    }

    protected List<Map<String, Object>> mapData(List<String[]> data) {
        if (data == null) {
            return null;
        } else {
        String[][] arr = new String[data.size()][];
        arr = data.toArray(arr);
            List<Map<String, Object>> devices = new ArrayList<>();
            for (int i = 1; i < arr.length; i++) {
                Map<String, Object> device = new HashMap<>();
                for (int j = 0; j < arr[i].length; j++) {
                    device.put(arr[0][j], arr[i][j]);
                }
                devices.add(device);
            }
            return devices;
        }
    }

}
