package com.analytics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a single sales transaction record.

 * This record encapsulates all relevant details of a sale, including transaction ID,
 * date, region, salesperson, product category, quantity sold, unit price, and total amount.
 * 
 * Using a record provides immutability, thread-safety, and value-based semantics,
 * making it well-suited for analytics and ETL pipelines where data integrity and consistency are critical.
 */

public record SaleRecord(
    String transactionId,
    LocalDate date,
    String region,
    String salesperson,
    String productCategory,
    int quantity,
    double unitPrice,
    double totalAmount
) {
    // Static factory method to parse a CSV line into a SaleRecord

    /**
     * Parses a CSV line into a SaleRecord instance.

     * The expected CSV format is a line with exactly 8 comma-separated values in the following order:
     * transactionId, date (ISO format), region, salesperson, productCategory, quantity, unitPrice, totalAmount.

     * Throws IllegalArgumentException if the CSV line is malformed or contains invalid data types.
     * @param csvLine a single line of CSV representing a sale record
     * @return a SaleRecord instance parsed from the CSV line
     * @throws IllegalArgumentException if the input line is malformed or data parsing fails
     * IllegalArgumentException is a type of Runtime Exception (for logical bugs and programming exceptions)
     * DateTimeParseException extends DateTimeException and  DateTimeException extends RuntimeException
     */

    // Garbage Collection actually gets expensive and might consume a lot of CPU cycles here
    // on scale this goes to EDEN space and triggers a minor GC 
    // even if 1% of exceptions arise, for large number of rows, GC takes up everything and this is a bottleneck
    // taking only a single pass, that is calling substring only when needed!

    public static SaleRecord fromCsv(String csvLine) {

        // Split the input CSV line by commas into parts
        String[] parts = csvLine.split(",");

                
        // Validate that the CSV line has exactly 8 columns as expected
        if (parts.length != 8) {
            throw new IllegalArgumentException("Malformed CSV line: " + csvLine);
        }
        try {
        // Parse and trim each field from the CSV parts
            String id = parts[0].trim();
        
        // Parse the date string into a LocalDate instance (expects ISO_LOCAL_DATE format)
            LocalDate date = LocalDate.parse(parts[1].trim());
            String region = parts[2].trim();
            String salesperson = parts[3].trim();
            String category = parts[4].trim();
        
        // Parse quantity as integer
            int qty = Integer.parseInt(parts[5].trim());
    
        // Parse unit price as double
            double price = Double.parseDouble(parts[6].trim());
            double total = Double.parseDouble(parts[7].trim()); // Or calculate qty * price

        // Construct and return a new SaleRecord with the parsed values
            return new SaleRecord(id, date, region, salesperson, category, qty, price, total);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing data in line: " + csvLine, e);
        }
    }
}