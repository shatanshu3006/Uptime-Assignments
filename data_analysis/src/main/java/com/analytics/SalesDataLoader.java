package com.analytics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Responsible for reading a CSV file from disk, parsing each row into a SaleRecord, 
// handling bad records safely, and returning a list of valid record

//Converting a streaming data source into a fully materialized in-memory dataset.

//Recommended fix : process each CSV row in a streaming manner (push to an aggregator/consumer) instead of collecting all SaleRecord objects into memory,
//  keeping heap usage constant and preventing GC pressure at scale.

//A producer consumer pipeline can actaully solve this issue effectively

public class SalesDataLoader {

    public List<SaleRecord> loadSalesData(String filePath) {
        Path path = Paths.get(filePath);
        
        if (!Files.exists(path)) {
            System.err.println("Error: File not found at " + filePath);
            return Collections.emptyList();
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines
                .skip(1) // Skip CSV header
                .filter(line -> !line.isBlank())
                .map(line -> {
                    try {
                        return SaleRecord.fromCsv(line);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Skipping invalid record: " + e.getMessage());
                        return null;
                    }
                })
                .filter(record -> record != null) // Remove failed parses
                .collect(Collectors.toList());      //All parsed records are stored in memory at once.

        } catch (IOException e) {
            System.err.println("I/O Error reading file: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}