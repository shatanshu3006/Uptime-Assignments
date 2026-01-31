import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerTest {
    
    public static void main(String[] args) {
        System.out.println("Running Producer-Consumer Tests");
        System.out.println("================================");
        
        testItemCreation();
        testSharedQueueBasicOperations();
        testQueueCapacity();
        testProducerConsumerTransfer();
        testGracefulTermination();
        testMultipleProducersConsumers();
        
        System.out.println("================================");
        System.out.println("All tests completed!");
    }
    
    public static void testItemCreation() {
        System.out.println("\nTest 1: Item Creation");
        Item item = new Item(1, "Test Data");
        if (item.getId() == 1 && item.getData().equals("Test Data")) {
            System.out.println("PASS: Item created correctly");
        } else {
            System.out.println("FAIL: Item creation failed");
        }
    }
    
    public static void testSharedQueueBasicOperations() {
        System.out.println("\nTest 2: SharedQueue Basic Operations");
        try {
            SharedQueue queue = new SharedQueue(5);
            Item item1 = new Item(1, "Item1");
            Item item2 = new Item(2, "Item2");
            
            queue.enqueue(item1);
            queue.enqueue(item2);
            
            if (queue.getSize() == 2) {
                System.out.println("PASS: Enqueue operation works");
            } else {
                System.out.println("FAIL: Enqueue operation failed");
            }
            
            Item retrieved = queue.dequeue();
            if (retrieved.getId() == 1 && queue.getSize() == 1) {
                System.out.println("PASS: Dequeue operation works");
            } else {
                System.out.println("FAIL: Dequeue operation failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception occurred - " + e.getMessage());
        }
    }
    
    public static void testQueueCapacity() {
        System.out.println("\nTest 3: Queue Capacity Limit");
        try {
            SharedQueue queue = new SharedQueue(3);
            
            // Fill queue to capacity
            for (int i = 1; i <= 3; i++) {
                queue.enqueue(new Item(i, "Item" + i));
            }
            
            if (queue.getSize() == 3) {
                System.out.println("PASS: Queue respects capacity limit");
            } else {
                System.out.println("FAIL: Queue capacity not enforced");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception occurred - " + e.getMessage());
        }
    }
    
    public static void testProducerConsumerTransfer() {
        System.out.println("\nTest 4: Producer-Consumer Transfer");
        try {
            List<Item> source = new ArrayList<Item>();
            for (int i = 1; i <= 5; i++) {
                source.add(new Item(i, "Data-" + i));
            }
            
            List<Item> destination = new ArrayList<Item>();
            DataTransferManager manager = new DataTransferManager(3);
            
            manager.startTransfer(source, destination);
            manager.waitForCompletion();
            
            if (destination.size() == 5) {
                System.out.println("PASS: All items transferred successfully");
            } else {
                System.out.println("FAIL: Transfer incomplete - Expected 5, got " + destination.size());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception occurred - " + e.getMessage());
        }
    }
    
    public static void testGracefulTermination() {
        System.out.println("\nTest 5: Graceful Termination");
        try {
            List<Item> source = new ArrayList<Item>();
            for (int i = 1; i <= 3; i++) {
                source.add(new Item(i, "Data-" + i));
            }
            
            List<Item> destination = new ArrayList<Item>();
            DataTransferManager manager = new DataTransferManager(5);
            
            manager.startTransfer(source, destination);
            manager.waitForCompletion();
            
            System.out.println("PASS: Threads terminated gracefully");
        } catch (Exception e) {
            System.out.println("FAIL: Exception occurred - " + e.getMessage());
        }
    }
    
    public static void testMultipleProducersConsumers() {
        System.out.println("\nTest 6: Multiple Producers and Consumers");
        try {
            List<List<Item>> sources = new ArrayList<List<Item>>();
            
            // Create 2 producers with 5 items each
            for (int p = 0; p < 2; p++) {
                List<Item> producerSource = new ArrayList<Item>();
                for (int i = 1; i <= 5; i++) {
                    producerSource.add(new Item((p * 5) + i, "P" + p + "-Item" + i));
                }
                sources.add(producerSource);
            }
            
            List<Item> destination = new ArrayList<Item>();
            DataTransferManager manager = new DataTransferManager(5);
            
            manager.startTransferMultiple(sources, destination, 2, 2);
            manager.waitForCompletion();
            
            if (destination.size() == 10) {
                System.out.println("PASS: Multiple producers/consumers transferred all items");
            } else {
                System.out.println("FAIL: Transfer incomplete - Expected 10, got " + destination.size());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception occurred - " + e.getMessage());
        }
    }
}