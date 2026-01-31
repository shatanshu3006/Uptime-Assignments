package com.analytics;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class SalesAnalyzer {
    private final List<SaleRecord> records;

    public SalesAnalyzer(List<SaleRecord> records) {
        this.records = records;
    }

    // 1. Total Sales by Region (Grouping + Summing)
    // SQL: SELECT region, SUM(total_amount) FROM sales GROUP BY region;
    public Map<String, Double> getTotalSalesByRegion() {
        return records.stream()
            .collect(Collectors.groupingBy(
                SaleRecord::region,
                Collectors.summingDouble(SaleRecord::totalAmount)
            ));
    }

    // 2. Average Sale by Category (Grouping + Averaging)
    // SQL: SELECT product_category, AVG(total_amount) FROM sales GROUP BY product_category;
    public Map<String, Double> getAverageSaleByCategory() {
        return records.stream()
            .collect(Collectors.groupingBy(
                SaleRecord::productCategory,
                Collectors.averagingDouble(SaleRecord::totalAmount)
            ));
    }

    // 3. Top N Salespersons (Grouping + Summing + Sorting + Limiting)
    // SQL: SELECT salesperson, SUM(total_amount) AS total FROM sales GROUP BY salesperson ORDER BY total DESC LIMIT n;
    public List<Map.Entry<String, Double>> getTopSalespersons(int n) {
        return records.stream()
            .collect(Collectors.groupingBy(
                SaleRecord::salesperson,
                Collectors.summingDouble(SaleRecord::totalAmount)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(n)
            .collect(Collectors.toList());
    }

    // 4. Monthly Sales Trend (Grouping by YearMonth)
    // SQL: SELECT EXTRACT(YEAR_MONTH FROM date) AS year_month, SUM(total_amount) FROM sales GROUP BY year_month ORDER BY year_month;
    public Map<YearMonth, Double> getMonthlySalesTrend() {
        return records.stream()
            .collect(Collectors.groupingBy(
                r -> YearMonth.from(r.date()),
                TreeMap::new, // Sort keys (Dates) automatically
                Collectors.summingDouble(SaleRecord::totalAmount)
            ));
    }

    // 5. Filter by Date Range
    // SQL: SELECT * FROM sales WHERE date >= ? AND date <= ?;
    public List<SaleRecord> getSalesByDateRange(LocalDate start, LocalDate end) {
        return records.stream()
            .filter(r -> !r.date().isBefore(start) && !r.date().isAfter(end))
            .collect(Collectors.toList());
    }

    // 6. Generate Summary Report (DoubleSummaryStatistics)
    // SQL: SELECT COUNT(*), SUM(total_amount), AVG(total_amount), MIN(total_amount), MAX(total_amount) FROM sales;
    public DoubleSummaryStatistics generateSummaryReport() {
        return records.stream()
            .collect(Collectors.summarizingDouble(SaleRecord::totalAmount));
    }

    // 7. Complex Grouping: Region -> Category -> Total Sales
    // SQL: SELECT region, product_category, SUM(total_amount) FROM sales GROUP BY region, product_category;
    public Map<String, Map<String, Double>> getSalesByRegionAndCategory() {
        return records.stream()
            .collect(Collectors.groupingBy(
                SaleRecord::region,
                Collectors.groupingBy(
                    SaleRecord::productCategory,
                    Collectors.summingDouble(SaleRecord::totalAmount)
                )
            ));
    }
}