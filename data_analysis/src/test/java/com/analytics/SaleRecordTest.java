package com.analytics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SaleRecordTest {

    @Test
    @DisplayName("Should parse valid CSV line correctly")
    void testFromCsv_Valid() {
        String csv = "T001, 2023-05-20, West, John, Tools, 5, 10.0, 50.0";
        SaleRecord record = SaleRecord.fromCsv(csv);

        assertNotNull(record);
        assertEquals("T001", record.transactionId());
        assertEquals(LocalDate.of(2023, 5, 20), record.date());
        assertEquals("West", record.region());
        assertEquals(50.0, record.totalAmount());
    }

    @Test
    @DisplayName("Should throw exception for missing columns")
    void testFromCsv_Malformed() {
        String invalidCsv = "T001, 2023-05-20, West"; // Missing fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SaleRecord.fromCsv(invalidCsv);
        });
        assertTrue(exception.getMessage().contains("Malformed CSV"));
    }

    @Test
    @DisplayName("Should throw exception for invalid number formats")
    void testFromCsv_InvalidNumber() {
        String invalidCsv = "T1, 2023-01-01, R, S, C, Five, 10.0, 50.0"; // 'Five' is not an int
        assertThrows(IllegalArgumentException.class, () -> {
            SaleRecord.fromCsv(invalidCsv);
        });
    }
}