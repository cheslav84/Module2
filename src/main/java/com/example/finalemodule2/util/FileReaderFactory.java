package com.example.finalemodule2.util;

import java.util.Optional;

public class FileReaderFactory {
    public Reader getReader(String fileName){
        if(fileName == null){
           throw new IllegalArgumentException("Empty file name.");
        }
        String fileExtension = getExtension(fileName)
                .orElseThrow(() -> new IllegalArgumentException("Bad file extension."));
        if(fileExtension.equalsIgnoreCase("csv")){
            return new CSVReader();
        }
        throw new IllegalArgumentException("Cant read such file extension " + fileExtension);
    }

    private Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
