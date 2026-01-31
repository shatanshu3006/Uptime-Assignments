package com.analytics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

// To run this java file:

// compile:
// javac -d out src/main/java/com/analytics/*.java
// run:
// java -cp out com.analytics.SalesAnalyticsApp

// with millions of rows, application will slow down, spend most CPU time in GC, 
// and eventually crash with OOM because it tries to keep all data in memory.

// Not loading all rows into memory—process the CSV as a stream and aggregate on the fly so objects die immediately and GC never gets pressured.
// Map Reduce solution coul really solve this problem here on a distributed system.

public class SalesAnalyticsApp {

    private static final String FILE_NAME = "sales_data.csv";

    public static void main(String[] args) {
        // 1. Setup: Create a dummy CSV file for demonstration
        createSampleData();

        // 2. Load Data
        System.out.println("Loading data...");
        SalesDataLoader loader = new SalesDataLoader();
        var records = loader.loadSalesData(FILE_NAME);
        System.out.printf("Loaded %d valid records.\n\n", records.size());

        // 3. Initialize Analyzer
        SalesAnalyzer analyzer = new SalesAnalyzer(records);

        // --- Demo Operations ---

        // A. Total Sales by Region
        System.out.println("--- Total Sales by Region ---");
        analyzer.getTotalSalesByRegion().forEach((region, total) -> 
            System.out.printf("%-10s: $%,.2f%n", region, total));

        // B. Average Sale by Category
        System.out.println("\n--- Average Sale by Category ---");
        analyzer.getAverageSaleByCategory().forEach((cat, avg) -> 
            System.out.printf("%-15s: $%,.2f%n", cat, avg));

        // C. Top 3 Salespersons
        System.out.println("\n--- Top 3 Salespersons ---");
        analyzer.getTopSalespersons(3).forEach(entry -> 
            System.out.printf("%-10s: $%,.2f%n", entry.getKey(), entry.getValue()));

        // D. Monthly Trend
        System.out.println("\n--- Monthly Sales Trend ---");
        analyzer.getMonthlySalesTrend().forEach((month, total) -> 
            System.out.println(month + ": $" + String.format("%,.2f", total)));

        // E. Date Range Filter
        LocalDate start = LocalDate.parse("2023-01-01");
        LocalDate end = LocalDate.parse("2023-01-31");
        System.out.printf("\n--- Sales between %s and %s ---%n", start, end);
        var janSales = analyzer.getSalesByDateRange(start, end);
        System.out.println("Count: " + janSales.size());

        // F. Statistical Summary
        System.out.println("\n--- Statistical Summary ---");
        DoubleSummaryStatistics stats = analyzer.generateSummaryReport();
        System.out.printf("Total Records: %d%n", stats.getCount());
        System.out.printf("Total Revenue: $%,.2f%n", stats.getSum());
        System.out.printf("Max Sale:      $%,.2f%n", stats.getMax());
        System.out.printf("Min Sale:      $%,.2f%n", stats.getMin());
        System.out.printf("Average Sale:  $%,.2f%n", stats.getAverage());
    }

    // Helper method to create a temporary CSV file
    private static void createSampleData() {
        String csvContent = """
            TransactionID,Date,Region,Salesperson,Category,Quantity,UnitPrice,TotalAmount
            T001,2023-01-15,North,Alice,Electronics,2,500.00,1000.00
            T002,2023-01-20,South,Bob,Books,5,20.00,100.00
            T003,2023-02-10,East,Charlie,Electronics,1,1200.00,1200.00
            T004,2023-02-15,North,Alice,Books,3,25.00,75.00
            T005,2023-03-05,West,David,Clothing,10,30.00,300.00
            T006,2023-01-25,South,Bob,Electronics,1,800.00,800.00
            T007,2023-03-12,North,Alice,Clothing,4,40.00,160.00
            T008,INVALID_DATE,North,Alice,Clothing,4,40.00,160.00
            """;
        
        try {
            Files.write(Paths.get(FILE_NAME), csvContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}