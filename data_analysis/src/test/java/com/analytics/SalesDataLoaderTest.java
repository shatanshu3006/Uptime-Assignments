package com.analytics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesDataLoaderTest {

    @TempDir
    Path tempDir; // JUnit creates and deletes this folder automatically

    @Test
    @DisplayName("Should load valid records and skip header")
    void testLoadSalesData_Success() throws IOException {
        // 1. Create a temp file
        Path csvFile = tempDir.resolve("test_sales.csv");
        String content = """
                ID,Date,Region,Salesperson,Category,Qty,Price,Total
                T1,2023-01-01,North,Alice,A,1,10,10
                T2,2023-01-02,South,Bob,B,2,20,40
                """;
        Files.writeString(csvFile, content);

        // 2. Run Loader
        SalesDataLoader loader = new SalesDataLoader();
        List<SaleRecord> records = loader.loadSalesData(csvFile.toString());

        // 3. Assert
        assertEquals(2, records.size());
        assertEquals("North", records.get(0).region());
        assertEquals("South", records.get(1).region());
    }

    @Test
    @DisplayName("Should handle file not found gracefully")
    void testLoadSalesData_FileNotFound() {
        SalesDataLoader loader = new SalesDataLoader();
        List<SaleRecord> records = loader.loadSalesData("non_existent_file.csv");
        
        // Based on current implementation, it returns empty list and prints error
        assertTrue(records.isEmpty());
    }

    @Test
    @DisplayName("Should skip invalid rows but load valid ones")
    void testLoadSalesData_MixedValidity() throws IOException {
        Path csvFile = tempDir.resolve("mixed_sales.csv");
        String content = """
                ID,Date,Region,Salesperson,Category,Qty,Price,Total
                T1,2023-01-01,North,Alice,A,1,10,10
                INVALID_ROW_DATA
                T2,2023-01-02,South,Bob,B,2,20,40
                """;
        Files.writeString(csvFile, content);

        SalesDataLoader loader = new SalesDataLoader();
        List<SaleRecord> records = loader.loadSalesData(csvFile.toString());

        assertEquals(2, records.size()); // Should have skipped the middle row
    }
}