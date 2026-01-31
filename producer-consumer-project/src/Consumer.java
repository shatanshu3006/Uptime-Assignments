import java.util.List;

/**
 * Consumer represents a consumer thread in the Producer-Consumer pattern.
 *
 * Responsibilities:
 * - Continuously dequeues items from a shared thread-safe queue
 * - Stores consumed items into a destination container
 * - Simulates processing latency to mimic real-world workloads
 *
 * This class is designed to be executed by a Thread or ExecutorService.
 */
public class Consumer implements Runnable {
    // Shared queue from which items are consumed (thread-safe)
    private SharedQueue sharedQueue;
    // Destination container to store consumed items
    private List<Item> destinationContainer;
    // Number of items this consumer is expected to consume
    private int itemsToConsume;
    // Logical name of the consumer (used for logging/debugging)
    private String consumerName;
    
    /**
     * Constructs a Consumer instance.
     *
     * @param sharedQueue          the shared queue to consume items from
     * @param destinationContainer container where consumed items are stored
     * @param itemsToConsume       total number of items to consume
     * @param consumerName         name of the consumer for logging purposes
     */
    public Consumer(SharedQueue sharedQueue, List<Item> destinationContainer, int itemsToConsume, String consumerName) {
        this.sharedQueue = sharedQueue;
        this.destinationContainer = destinationContainer;
        this.itemsToConsume = itemsToConsume;
        this.consumerName = consumerName;
    }
    
    /**
     * Entry point for the consumer thread.
     *
     * Continuously dequeues items from the shared queue and adds them to the
     * destination container. Synchronization is applied while writing to the
     * destination container to ensure thread safety.
     */
    @Override
    public void run() {
        try {
            System.out.println(consumerName + " started");
            // Consume the configured number of items
            for (int i = 0; i < itemsToConsume; i++) {
                // Blocking call: waits until an item is available in the queue
                Item item = sharedQueue.dequeue(consumerName);
                // Synchronize to protect concurrent writes to the destination container
                synchronized (destinationContainer) {
                    destinationContainer.add(item);
                }
                // Simulate processing time for the consumed item
                Thread.sleep(150); // Simulate some processing time
            }
            System.out.println(consumerName + " finished consuming all items");
        } catch (InterruptedException e) {
            System.out.println(consumerName + " interrupted");
            // Restore interrupt status so higher-level logic can handle it
            Thread.currentThread().interrupt();
        }
    }
}