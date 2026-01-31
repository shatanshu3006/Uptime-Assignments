import java.util.ArrayList;
import java.util.List;

public class MainMultiple {
    public static void main(String[] args) {
        System.out.println("=== Multiple Producers and Consumers Demo ===");
        System.out.println();
        
        // Configure the system
        int numProducers = 3;
        int numConsumers = 2;
        int itemsPerProducer = 10;
        
        // Create source containers for each producer
        List<List<Item>> sourceContainers = new ArrayList<List<Item>>();
        
        for (int p = 0; p < numProducers; p++) {
            List<Item> producerSource = new ArrayList<Item>();
            for (int i = 1; i <= itemsPerProducer; i++) {
                int itemId = (p * itemsPerProducer) + i;
                producerSource.add(new Item(itemId, "Data-P" + (p + 1) + "-" + i));
            }
            sourceContainers.add(producerSource);
        }
        
        // Create shared destination container
        List<Item> destinationContainer = new ArrayList<Item>();
        
        // Calculate totals
        int totalItems = numProducers * itemsPerProducer;
        
        System.out.println("Configuration:");
        System.out.println("- Number of Producers: " + numProducers);
        System.out.println("- Number of Consumers: " + numConsumers);
        System.out.println("- Items per Producer: " + itemsPerProducer);
        System.out.println("- Total Items: " + totalItems);
        System.out.println("- Queue Capacity: 10");
        System.out.println("========================================");
        System.out.println();
        
        // Create data transfer manager with queue capacity of 10
        DataTransferManager manager = new DataTransferManager(10);
        
        // Start the transfer with multiple producers and consumers
        manager.startTransferMultiple(sourceContainers, destinationContainer, numProducers, numConsumers);
        
        // Monitor queue status periodically
        Thread statusThread = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(500);
                    System.out.println("[STATUS] " + manager.getQueueStatus() + " | Items in destination: " + destinationContainer.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        statusThread.start();
        
        // Wait for completion
        try {
            manager.waitForCompletion();
            statusThread.interrupt();
            statusThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            manager.stopTransfer();
        }
        
        System.out.println();
        System.out.println("========================================");
        System.out.println("Transfer Complete!");
        System.out.println("- Total items transferred: " + destinationContainer.size());
        System.out.println("- Expected items: " + totalItems);
        System.out.println("- Final queue status: " + manager.getQueueStatus());
        
        if (destinationContainer.size() == totalItems) {
            System.out.println("- Status: SUCCESS ✓");
        } else {
            System.out.println("- Status: MISMATCH ✗");
        }
    }
}