import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create source container with sample items
        List<Item> sourceContainer = new ArrayList<Item>();
        for (int i = 1; i <= 20; i++) {
            sourceContainer.add(new Item(i, "Data-" + i));
        }
        
        // Create destination container
        List<Item> destinationContainer = new ArrayList<Item>();
        
        // Create data transfer manager with queue capacity of 10
        DataTransferManager manager = new DataTransferManager(10);
        
        System.out.println("Starting producer-consumer demonstration");
        System.out.println("Source items: " + sourceContainer.size());
        System.out.println("Queue capacity: 10");
        System.out.println("========================================");
        
        // Start the transfer
        manager.startTransfer(sourceContainer, destinationContainer);
        
        // Monitor queue status periodically
        Thread statusThread = new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    Thread.sleep(500);
                    System.out.println("Status: " + manager.getQueueStatus());
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
        
        System.out.println("========================================");
        System.out.println("Transfer complete!");
        System.out.println("Items transferred: " + destinationContainer.size());
        System.out.println("Final queue status: " + manager.getQueueStatus());
    }
}