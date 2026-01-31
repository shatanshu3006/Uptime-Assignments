package com.analytics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SalesAnalyzerTest {

    private SalesAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        // Create a controlled dataset for reliable assertions
        List<SaleRecord> mockData = List.of(
            new SaleRecord("T1", LocalDate.of(2023, 1, 1), "North", "Alice", "Elec", 1, 100.0, 100.0),
            new SaleRecord("T2", LocalDate.of(2023, 1, 2), "North", "Bob",   "Elec", 2, 50.0,  100.0),
            new SaleRecord("T3", LocalDate.of(2023, 1, 5), "South", "Alice", "Books", 1, 20.0,  20.0),
            new SaleRecord("T4", LocalDate.of(2023, 2, 1), "North", "Alice", "Books", 1, 30.0,  30.0)
        );
        analyzer = new SalesAnalyzer(mockData);
    }

    @Test
    @DisplayName("Should correctly sum total sales by region")
    void testGetTotalSalesByRegion() {
        Map<String, Double> result = analyzer.getTotalSalesByRegion();

        assertEquals(230.0, result.get("North"), 0.001); // 100 + 100 + 30
        assertEquals(20.0, result.get("South"), 0.001);
    }

    @Test
    @DisplayName("Should calculate average sales by category")
    void testGetAverageSaleByCategory() {
        Map<String, Double> result = analyzer.getAverageSaleByCategory();

        // Electronics: (100 + 100) / 2 = 100
        assertEquals(100.0, result.get("Elec"), 0.001);
        // Books: (20 + 30) / 2 = 25
        assertEquals(25.0, result.get("Books"), 0.001);
    }

    @Test
    @DisplayName("Should identify top N salespersons")
    void testGetTopSalespersons() {
        var top = analyzer.getTopSalespersons(2);

        // Alice Total: 100 + 20 + 30 = 150
        // Bob Total: 100
        
        assertEquals(2, top.size());
        assertEquals("Alice", top.get(0).getKey());
        assertEquals(150.0, top.get(0).getValue(), 0.001);
    }

    @Test
    @DisplayName("Should group sales by YearMonth")
    void testGetMonthlySalesTrend() {
        Map<YearMonth, Double> result = analyzer.getMonthlySalesTrend();

        YearMonth jan2023 = YearMonth.of(2023, 1);
        YearMonth feb2023 = YearMonth.of(2023, 2);

        assertEquals(220.0, result.get(jan2023), 0.001); // 100 + 100 + 20
        assertEquals(30.0, result.get(feb2023), 0.001);
    }

    @Test
    @DisplayName("Should filter sales strictly within date range")
    void testGetSalesByDateRange() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 3);

        List<SaleRecord> result = analyzer.getSalesByDateRange(start, end);

        assertEquals(2, result.size()); // T1 and T2 only
        assertTrue(result.stream().anyMatch(r -> r.transactionId().equals("T1")));
        assertFalse(result.stream().anyMatch(r -> r.transactionId().equals("T4")));
    }

    @Test
    @DisplayName("Should generate correct statistical summary")
    void testGenerateSummaryReport() {
        DoubleSummaryStatistics stats = analyzer.generateSummaryReport();

        assertEquals(4, stats.getCount());
        assertEquals(250.0, stats.getSum(), 0.001);
        assertEquals(100.0, stats.getMax(), 0.001);
        assertEquals(20.0, stats.getMin(), 0.001);
    }
}