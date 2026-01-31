# Producer-Consumer Pattern Implementation

## Overview
This is a simple implementation of the producer-consumer pattern in Java using thread synchronization with wait/notify mechanism. **Now supports multiple producers and consumers!**

## Files Included
- `Item.java` - Represents data items with id, data, and timestamp
- `SharedQueue.java` - Thread-safe queue with capacity limit
- `Producer.java` - Produces items and adds them to the shared queue
- `Consumer.java` - Consumes items from the shared queue
- `DataTransferManager.java` - Manages the producer-consumer transfer process
- `Main.java` - Demonstrates single producer/consumer
- `MainMultiple.java` - **NEW: Demonstrates multiple producers/consumers**
- `ProducerConsumerTest.java` - Unit tests for core functionality
- `README.md` - This file

## Setup Instructions

### Compilation
Compile all Java files:
```bash
javac *.java
```

### Running the Programs

**Single Producer/Consumer:**
```bash
java Main
```

**Multiple Producers/Consumers:**
```bash
java MainMultiple
```

**Run Tests:**
```bash
java ProducerConsumerTest
```

## Features Implemented

### 1. Thread Synchronization
- Uses `wait()` and `notifyAll()` for thread coordination
- Proper synchronization using locks
- Thread-safe destination container access

### 2. Queue Capacity Management
- Maximum capacity of 10 items (configurable)
- Producer waits when queue is full
- Consumer waits when queue is empty

### 3. Graceful Termination
- All threads terminate after processing all data
- Proper cleanup and status reporting

### 4. Exception Handling
- Handles InterruptedException
- Thread interruption support

### 5. **Multiple Producers/Consumers Support**
- Configure any number of producers and consumers
- Automatic load distribution among consumers
- Each producer/consumer has unique identifier
- Thread-safe concurrent access to shared resources

## How Multiple Producers/Consumers Work

### Architecture
```
Producer-1 ──┐
Producer-2 ──┤──→ [Shared Queue] ──┤──→ Consumer-1
Producer-3 ──┘     (Capacity: 10)   └──→ Consumer-2
```

### Key Points:
1. **Multiple Producers**: Each producer has its own source container and produces items independently
2. **Shared Queue**: All producers add to the same queue; all consumers read from it
3. **Load Distribution**: Items are automatically distributed among consumers
4. **Thread Safety**: Synchronized access ensures no data corruption
5. **Identification**: Each producer/consumer has a unique name for tracking

### Example Configuration:
- 3 Producers (each with 10 items) = 30 total items
- 2 Consumers (each consumes 15 items)
- Queue Capacity: 10 items
- Result: All 30 items transferred successfully

## Sample Input/Output

### Single Producer/Consumer (Main.java)
```
Starting producer-consumer demonstration
Source items: 20
Queue capacity: 10
========================================
Data transfer started with 1 producer and 1 consumer
Producer-1 started
Consumer-1 started
Producer-1 produced: Item[id=1, data=Data-1, ...]
Consumer-1 consumed: Item[id=1, data=Data-1, ...]
...
Transfer complete!
Items transferred: 20
```

### Multiple Producers/Consumers (MainMultiple.java)
```
=== Multiple Producers and Consumers Demo ===

Configuration:
- Number of Producers: 3
- Number of Consumers: 2
- Items per Producer: 10
- Total Items: 30
- Queue Capacity: 10
========================================

Data transfer started with 3 producers and 2 consumers
Producer-1 started
Producer-2 started
Producer-3 started
Consumer-1 started
Consumer-2 started

Producer-1 produced: Item[id=1, data=Data-P1-1, ...]
Producer-2 produced: Item[id=11, data=Data-P2-1, ...]
Consumer-1 consumed: Item[id=1, data=Data-P1-1, ...]
...

Transfer Complete!
- Total items transferred: 30
- Expected items: 30
- Status: SUCCESS ✓
```

## Assumptions
1. Items are processed in FIFO order
2. Multiple producers can produce concurrently
3. Multiple consumers can consume concurrently
4. Queue capacity is set to 10 (configurable)
5. All items from all sources must be transferred
6. Consumers share the workload evenly

## Testing
Run `ProducerConsumerTest.java` to verify:
1. Item creation
2. Queue enqueue/dequeue operations
3. Queue capacity limits
4. Complete data transfer (single producer/consumer)
5. Graceful thread termination
6. **Multiple producers/consumers transfer**

## Code Structure

### Folder Structure for VS Code:
```
producer-consumer/
├── src/
│   ├── Main.java
│   ├── MainMultiple.java          (NEW)
│   ├── Item.java
│   ├── SharedQueue.java
│   ├── Producer.java
│   ├── Consumer.java
│   └── DataTransferManager.java
├── test/
│   └── ProducerConsumerTest.java
└── README.md
```

## Usage Examples

### Example 1: Single Producer/Consumer
```java
List<Item> source = createItems(20);
List<Item> destination = new ArrayList<>();
DataTransferManager manager = new DataTransferManager(10);

manager.startTransfer(source, destination);
manager.waitForCompletion();
```

### Example 2: Multiple Producers/Consumers
```java
List<List<Item>> sources = new ArrayList<>();
sources.add(createItems(10)); // Producer 1
sources.add(createItems(10)); // Producer 2
sources.add(createItems(10)); // Producer 3

List<Item> destination = new ArrayList<>();
DataTransferManager manager = new DataTransferManager(10);

manager.startTransferMultiple(sources, destination, 3, 2);
manager.waitForCompletion();
```

## Key Methods

### DataTransferManager
- `startTransfer(source, destination)` - Single producer/consumer
- `startTransferMultiple(sources, destination, numProducers, numConsumers)` - **NEW**
- `stopTransfer()` - Stop all threads
- `getQueueStatus()` - Get current queue status
- `waitForCompletion()` - Wait for all threads to finish

### SharedQueue
- `enqueue(item, producerName)` - Add item with producer identification
- `dequeue(consumerName)` - Remove item with consumer identification
- `getQueueStatus()` - Returns "Queue size: X/Y"
- `getSize()` - Get current queue size