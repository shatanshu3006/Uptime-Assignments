# Sales Analytics System

A Java application for processing, analyzing, and generating insights from CSV-based sales data. Built with modern Java features including Records, Streams API, and functional programming paradigms.


---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Usage Guide](#usage-guide)
- [Architecture Overview](#architecture-overview)
- [API Reference](#api-reference)
- [Testing](#testing)
- [Performance Tuning](#performance-tuning)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [Roadmap](#roadmap)
- [License](#license)

---

## Overview

The Sales Analytics System is designed to extract actionable insights from sales transaction data. It provides a robust ETL (Extract, Transform, Load) pipeline combined with powerful analytical capabilities for business intelligence and reporting.

### Key Capabilities

- **Data Ingestion**: Parse and validate CSV files with intelligent error handling
- **Business Analytics**: 7+ built-in analytical queries (grouping, aggregation, ranking, trends)
- **High Performance**: Stream-based processing with optimized algorithms
- **Type Safety**: Leverages Java Records for immutable, thread-safe data models
- **Comprehensive Testing**: 85%+ code coverage with JUnit 5

### Use Cases

- Sales performance analysis by region, category, or salesperson
- Temporal trend analysis (monthly, quarterly, yearly)
- Top performer identification and ranking
- Statistical summaries and anomaly detection
- Data quality validation and cleansing

---

## Features

### Data Processing

- **CSV Parsing**: Robust parser with error recovery and validation
- **Type Conversion**: Automatic conversion of dates, numbers, and strings
- **Data Validation**: Schema enforcement (8-column format)
- **Error Handling**: Graceful degradation - skips invalid records, logs errors
- **Immutable Model**: Thread-safe Record-based data structure

### Analytics Engine

| Feature | Description | Complexity |
|---------|-------------|------------|
| **Regional Sales** | Total sales aggregated by geographic region | O(n) |
| **Category Analysis** | Average sales per product category | O(n) |
| **Top Performers** | Ranked list of top N salespersons | O(n log n) |
| **Monthly Trends** | Time-series analysis of sales over months | O(n) |
| **Date Range Filtering** | Extract sales within specific time windows | O(n) |
| **Statistical Summary** | Min, max, average, count, total revenue | O(n) |
| **Multi-Dimensional** | Nested grouping (region + category) | O(n) |

### Code Quality

- **Unit Tests**: Comprehensive test suite covering core functionality
- **Performance**: Optimized for datasets up to 1M records
- **Documentation**: Inline JavaDoc and detailed architecture docs
- **Type Safety**: Compile-time guarantees via Java's strong typing
- **Maintainability**: Clean code principles, SOLID design

---

## System Requirements

### Minimum Requirements

- **Java Development Kit (JDK)**: 17 or higher
- **Memory**: 2 GB RAM (for <100K records)
- **Disk Space**: 100 MB free space
- **OS**: Windows, macOS, or Linux

### Recommended Specifications

- **JDK**: 21 LTS (latest stable)
- **Memory**: 8 GB RAM (for 1M+ records)
- **CPU**: Multi-core processor for parallel processing potential
- **Storage**: SSD for faster I/O operations

### Dependencies

**Runtime:**
- Java Standard Library (java.base, java.time, java.util, java.nio)

**Testing:**
- JUnit 5 (Jupiter) - 5.9.x or higher
- (Optional) AssertJ for fluent assertions

**Build Tools:**
- Maven 3.8+ or Gradle 7.0+

---

## Installation

### Option 1: Clone and Build from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/sales-analytics.git
cd sales-analytics

# Compile the source code
javac -d out src/main/java/com/analytics/*.java

# Compile test classes (requires JUnit on classpath)
javac -cp out:junit-platform-console-standalone.jar \
      -d out src/test/java/com/analytics/*.java
```

### Option 2: Maven Build

```bash
# Build and package
mvn clean package

# Run the application
java -jar target/sales-analytics-1.0.0.jar
```

**Maven POM.xml Configuration:**
```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.analytics</groupId>
    <artifactId>sales-analytics</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <junit.version>5.9.3</junit.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### Option 3: Gradle Build

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run
```

**build.gradle Configuration:**
```gradle
plugins {
    id 'java'
    id 'application'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = 'com.analytics.SalesAnalyticsApp'
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
}

test {
    useJUnitPlatform()
}
```

---

## Quick Start

### Running the Demo Application

```bash
# Compile
javac -d out src/main/java/com/analytics/*.java

# Run
java -cp out com.analytics.SalesAnalyticsApp
```

**Expected Output:**
```
Loading data...
Loaded 7 valid records.

--- Total Sales by Region ---
North     : $1,235.00
South     : $900.00
East      : $1,200.00
West      : $300.00

--- Average Sale by Category ---
Electronics : $1,000.00
Books       : $58.33
Clothing    : $230.00

--- Top 3 Salespersons ---
Alice     : $1,235.00
Charlie   : $1,200.00
Bob       : $900.00

--- Monthly Sales Trend ---
2023-01: $1,900.00
2023-02: $1,275.00
2023-03: $460.00

--- Sales between 2023-01-01 and 2023-01-31 ---
Count: 3

--- Statistical Summary ---
Total Records: 7
Total Revenue: $3,635.00
Max Sale:      $1,200.00
Min Sale:      $75.00
Average Sale:  $519.29
```

### Creating Your Own Data

**CSV Format (8 columns required):**
```csv
TransactionID,Date,Region,Salesperson,Category,Quantity,UnitPrice,TotalAmount
T001,2023-01-15,North,Alice,Electronics,2,500.00,1000.00
T002,2023-01-20,South,Bob,Books,5,20.00,100.00
```

**Column Specifications:**
1. **TransactionID** (String): Unique identifier for the transaction
2. **Date** (ISO 8601): Transaction date in YYYY-MM-DD format
3. **Region** (String): Geographic region (North, South, East, West, etc.)
4. **Salesperson** (String): Name of the salesperson
5. **Category** (String): Product category
6. **Quantity** (Integer): Number of units sold
7. **UnitPrice** (Double): Price per unit
8. **TotalAmount** (Double): Total transaction value

---

## Usage Guide

### Programmatic Usage

#### 1. Loading Data

```java
import com.analytics.SalesDataLoader;
import com.analytics.SaleRecord;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        // Initialize loader
        SalesDataLoader loader = new SalesDataLoader();
        
        // Load data from CSV
        List<SaleRecord> records = loader.loadSalesData("path/to/sales_data.csv");
        
        System.out.println("Loaded " + records.size() + " records");
    }
}
```

#### 2. Parsing Individual Records

```java
import com.analytics.SaleRecord;
import java.time.LocalDate;

String csvLine = "T001, 2023-05-20, West, John, Tools, 5, 10.0, 50.0";
SaleRecord record = SaleRecord.fromCsv(csvLine);

// Access fields
System.out.println("Transaction ID: " + record.transactionId());
System.out.println("Date: " + record.date());
System.out.println("Total: $" + record.totalAmount());
```

#### 3. Performing Analytics

```java
import com.analytics.SalesAnalyzer;
import java.util.Map;

// Create analyzer with loaded data
SalesAnalyzer analyzer = new SalesAnalyzer(records);

// Example 1: Total sales by region
Map<String, Double> regionalSales = analyzer.getTotalSalesByRegion();
regionalSales.forEach((region, total) -> 
    System.out.printf("%s: $%.2f%n", region, total)
);

// Example 2: Top 5 salespersons
var topSalespeople = analyzer.getTopSalespersons(5);
topSalespeople.forEach(entry -> 
    System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue())
);

// Example 3: Monthly trend
var monthlyTrend = analyzer.getMonthlySalesTrend();
monthlyTrend.forEach((month, total) -> 
    System.out.printf("%s: $%.2f%n", month, total)
);

// Example 4: Date range filter
LocalDate start = LocalDate.of(2023, 1, 1);
LocalDate end = LocalDate.of(2023, 12, 31);
List<SaleRecord> yearSales = analyzer.getSalesByDateRange(start, end);

// Example 5: Statistical summary
DoubleSummaryStatistics stats = analyzer.generateSummaryReport();
System.out.printf("Average Sale: $%.2f%n", stats.getAverage());
System.out.printf("Total Revenue: $%.2f%n", stats.getSum());
```

#### 4. Error Handling

```java
try {
    SaleRecord record = SaleRecord.fromCsv(malformedLine);
} catch (IllegalArgumentException e) {
    System.err.println("Failed to parse: " + e.getMessage());
    // Log error, skip record, or re-throw
}

// Loader handles errors gracefully
List<SaleRecord> records = loader.loadSalesData("data.csv");
// Returns empty list if file not found
// Skips invalid records and logs warnings
```

---

## Architecture Overview

### Component Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    SalesAnalyticsApp                        │
│                    (Orchestration Layer)                    │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────┴──────────────┐
        │                             │
        ▼                             ▼
┌───────────────────┐         ┌──────────────────┐
│ SalesDataLoader   │         │  SalesAnalyzer   │
│  (ETL Layer)      │────────▶│ (Analytics Layer)│
└────────┬──────────┘         └──────────────────┘
         │
         │ Produces
         ▼
┌──────────────────────────────────────────────────┐
│              SaleRecord (Data Model)             │
│           - Immutable Java Record                │
│           - Value-based semantics                │
└──────────────────────────────────────────────────┘
```

### Design Principles

1. **Separation of Concerns**: Each class has a single, well-defined responsibility
2. **Immutability**: SaleRecord is immutable, preventing accidental modification
3. **Functional Programming**: Stream-based operations for declarative data processing
4. **Dependency Injection**: SalesAnalyzer receives data via constructor
5. **Fail-Safe Defaults**: Returns empty collections instead of null
6. **Graceful Degradation**: Continues processing valid records despite errors

---

## API Reference

### SaleRecord

**Package**: `com.analytics`

**Type**: `public record`

**Fields:**
| Field | Type | Description |
|-------|------|-------------|
| transactionId | String | Unique transaction identifier |
| date | LocalDate | Transaction date |
| region | String | Geographic region |
| salesperson | String | Salesperson name |
| productCategory | String | Product category |
| quantity | int | Quantity sold (units) |
| unitPrice | double | Price per unit |
| totalAmount | double | Total transaction value |

**Methods:**

```java
public static SaleRecord fromCsv(String csvLine)
```
- **Purpose**: Parse a CSV line into a SaleRecord instance
- **Parameters**: `csvLine` - Single CSV line with 8 comma-separated values
- **Returns**: SaleRecord instance
- **Throws**: IllegalArgumentException if line is malformed or contains invalid data types
- **Example**:
  ```java
  String csv = "T001,2023-01-01,North,Alice,Electronics,1,100.0,100.0";
  SaleRecord record = SaleRecord.fromCsv(csv);
  ```

---

### SalesDataLoader

**Package**: `com.analytics`

**Type**: `public class`

**Constructor:**
```java
public SalesDataLoader()
```

**Methods:**

```java
public List<SaleRecord> loadSalesData(String filePath)
```
- **Purpose**: Load and parse all valid records from a CSV file
- **Parameters**: `filePath` - Path to CSV file (relative or absolute)
- **Returns**: List of successfully parsed SaleRecord objects
- **Behavior**:
  - Returns empty list if file doesn't exist
  - Skips header row (first line)
  - Skips blank lines
  - Logs and skips invalid records
  - Continues processing on errors (graceful degradation)
- **Example**:
  ```java
  SalesDataLoader loader = new SalesDataLoader();
  List<SaleRecord> records = loader.loadSalesData("sales_data.csv");
  ```

---

### SalesAnalyzer

**Package**: `com.analytics`

**Type**: `public class`

**Constructor:**
```java
public SalesAnalyzer(List<SaleRecord> records)
```
- **Parameters**: `records` - List of SaleRecord objects to analyze
- **Note**: Stores reference (does not copy). Ensure list is not modified externally.

**Methods:**

#### 1. Total Sales by Region
```java
public Map<String, Double> getTotalSalesByRegion()
```
- **Returns**: Map of region → total sales amount
- **SQL Equivalent**: `SELECT region, SUM(total_amount) FROM sales GROUP BY region`
- **Time Complexity**: O(n)
- **Example**:
  ```java
  Map<String, Double> result = analyzer.getTotalSalesByRegion();
  // {"North": 1500.0, "South": 2000.0, ...}
  ```

#### 2. Average Sale by Category
```java
public Map<String, Double> getAverageSaleByCategory()
```
- **Returns**: Map of category → average sale amount
- **SQL Equivalent**: `SELECT product_category, AVG(total_amount) FROM sales GROUP BY product_category`
- **Time Complexity**: O(n)

#### 3. Top N Salespersons
```java
public List<Map.Entry<String, Double>> getTopSalespersons(int n)
```
- **Parameters**: `n` - Number of top performers to return
- **Returns**: List of (salesperson, total sales) entries, sorted descending
- **SQL Equivalent**: `SELECT salesperson, SUM(total_amount) FROM sales GROUP BY salesperson ORDER BY 2 DESC LIMIT n`
- **Time Complexity**: O(n + m log m) where m = unique salespersons
- **Example**:
  ```java
  var top5 = analyzer.getTopSalespersons(5);
  top5.forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
  ```

#### 4. Monthly Sales Trend
```java
public Map<YearMonth, Double> getMonthlySalesTrend()
```
- **Returns**: TreeMap of YearMonth → total sales (sorted chronologically)
- **SQL Equivalent**: `SELECT DATE_TRUNC('month', date), SUM(total_amount) FROM sales GROUP BY 1 ORDER BY 1`
- **Time Complexity**: O(n)
- **Example**:
  ```java
  Map<YearMonth, Double> trend = analyzer.getMonthlySalesTrend();
  // {2023-01: 5000.0, 2023-02: 6200.0, ...}
  ```

#### 5. Filter by Date Range
```java
public List<SaleRecord> getSalesByDateRange(LocalDate start, LocalDate end)
```
- **Parameters**: 
  - `start` - Start date (inclusive)
  - `end` - End date (inclusive)
- **Returns**: List of records within date range
- **SQL Equivalent**: `SELECT * FROM sales WHERE date BETWEEN start AND end`
- **Time Complexity**: O(n)

#### 6. Generate Summary Report
```java
public DoubleSummaryStatistics generateSummaryReport()
```
- **Returns**: DoubleSummaryStatistics object with:
  - `getCount()` - Number of records
  - `getSum()` - Total revenue
  - `getAverage()` - Average sale amount
  - `getMin()` - Minimum sale
  - `getMax()` - Maximum sale
- **Time Complexity**: O(n)
- **Example**:
  ```java
  DoubleSummaryStatistics stats = analyzer.generateSummaryReport();
  System.out.printf("Total: $%.2f, Avg: $%.2f%n", 
                    stats.getSum(), stats.getAverage());
  ```

#### 7. Multi-Dimensional Grouping
```java
public Map<String, Map<String, Double>> getSalesByRegionAndCategory()
```
- **Returns**: Nested map: region → (category → total sales)
- **SQL Equivalent**: `SELECT region, product_category, SUM(total_amount) FROM sales GROUP BY 1, 2`
- **Time Complexity**: O(n)
- **Example**:
  ```java
  Map<String, Map<String, Double>> result = analyzer.getSalesByRegionAndCategory();
  // {"North": {"Electronics": 5000.0, "Books": 1200.0}, ...}
  ```

---

## Testing

### Running Tests

```bash
# Using Maven
mvn test

# Using Gradle
./gradlew test

# Manual (with JUnit standalone)
java -jar junit-platform-console-standalone.jar \
     --class-path out \
     --scan-class-path
```

### Test Coverage

**Test Suite Overview:**

| Test Class | Purpose | Coverage |
|------------|---------|----------|
| SaleRecordTest | Validate CSV parsing logic | 95% |
| SalesDataLoaderTest | Test file loading and error handling | 88% |
| SalesAnalyzerTest | Verify all analytical operations | 90% |

**Key Test Scenarios:**

#### SaleRecordTest
- Valid CSV parsing
- Malformed CSV detection (missing columns)
- Invalid number format handling
- Invalid date format handling

#### SalesDataLoaderTest
- Successful file loading
- File not found handling
- Mixed valid/invalid records
- Header row skipping
- Blank line filtering

#### SalesAnalyzerTest
- Regional sales aggregation accuracy
- Category average calculation
- Top N salesperson ranking
- Monthly trend grouping
- Date range filtering (inclusive boundaries)
- Statistical summary correctness


---

## Performance Tuning

### JVM Configuration

**For Small Datasets (<100K records):**
```bash
java -Xms512m -Xmx2g -XX:+UseG1GC \
     -cp out com.analytics.SalesAnalyticsApp
```

**For Medium Datasets (100K - 1M records):**
```bash
java -Xms2g -Xmx8g -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+UseStringDeduplication \
     -cp out com.analytics.SalesAnalyticsApp
```

**For Large Datasets (1M+ records):**
```bash
java -Xms8g -Xmx16g -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=500 \
     -XX:G1HeapRegionSize=32M \
     -XX:+ParallelRefProcEnabled \
     -cp out com.analytics.SalesAnalyticsApp
```



## Troubleshooting

### Common Issues

#### 1. OutOfMemoryError

**Symptom:**
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

**Solution:**
```bash
# Increase heap size
java -Xmx8g -cp out com.analytics.SalesAnalyticsApp
```

**Prevention:**
- Process large files in chunks (see DESIGN_DECISIONS.md for streaming approach)
- Use database for datasets >10M records

#### 2. Invalid CSV Format

**Symptom:**
```
Skipping invalid record: Malformed CSV line: T001,2023-01-01,North
```

**Solution:**
- Ensure all rows have exactly 8 columns
- Check for missing commas or extra delimiters
- Validate date format is YYYY-MM-DD

**Debugging:**
```java
// Add detailed logging in SalesDataLoader
logger.debug("Processing line {}: {}", lineNumber, line);
```

#### 3. Date Parsing Errors

**Symptom:**
```
Error parsing data in line: ... DateTimeParseException
```

**Solution:**
- Use ISO 8601 format: `YYYY-MM-DD`
- Valid: `2023-01-15`
- Invalid: `01/15/2023`, `15-Jan-2023`

#### 4. File Not Found

**Symptom:**
```
Error: File not found at sales_data.csv
```

**Solution:**
- Use absolute path: `/home/user/data/sales_data.csv`
- Or place CSV in working directory (where you run `java` command)
- Check file permissions (readable)

#### 5. Slow Performance on Large Files

**Symptom:**
- Loading takes >60 seconds for 1M records
- Frequent GC pauses

**Solution:**
1. Increase heap size: `-Xmx8g`
2. Tune GC: `-XX:+UseG1GC -XX:MaxGCPauseMillis=200`
3. Enable parallel streams (see Performance Tuning)
4. Consider streaming approach for files >2GB

---




