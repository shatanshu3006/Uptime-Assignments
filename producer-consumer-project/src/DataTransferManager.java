import java.util.ArrayList;
import java.util.List;

/**
 * DataTransferManager coordinates producer and consumer threads
 * using a shared bounded queue.
 *
 * Responsibilities:
 * - Initialize and manage the shared queue
 * - Start and stop producer/consumer workflows
 * - Support single and multiple producer-consumer configurations
 * - Track lifecycle and execution state of transfer threads
 *
 * This class acts as the orchestration layer for the Producer-Consumer system.
 */
public class DataTransferManager {
    // Shared bounded queue used for producer-consumer communication
    private SharedQueue sharedQueue;
    // Tracks all producer threads for lifecycle management
    private List<Thread> producerThreads;
    // Tracks all consumer threads for lifecycle management
    private List<Thread> consumerThreads;
    // Indicates whether a data transfer operation is currently active
    private boolean isRunning;
    
    /**
     * Creates a DataTransferManager with a bounded shared queue.
     *
     * @param queueCapacity maximum number of items the shared queue can hold
     */
    public DataTransferManager(int queueCapacity) {
        this.sharedQueue = new SharedQueue(queueCapacity);
        this.producerThreads = new ArrayList<Thread>();
        this.consumerThreads = new ArrayList<Thread>();
        this.isRunning = false;
    }
    
    /**
     * Starts a data transfer using a single producer and a single consumer.
     *
     * @param sourceContainer      source list containing items to be produced
     * @param destinationContainer destination list where consumed items are stored
     */
    public void startTransfer(List<Item> sourceContainer, List<Item> destinationContainer) {
        // Prevent concurrent transfer executions
        if (isRunning) {
            System.out.println("Transfer already running");
            return;
        }
        
        isRunning = true;
        // Initialize producer and consumer instances
        Producer producer = new Producer(sourceContainer, sharedQueue, "Producer-1");
        Consumer consumer = new Consumer(sharedQueue, destinationContainer, sourceContainer.size(), "Consumer-1");
        
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        
        producerThreads.add(producerThread);
        consumerThreads.add(consumerThread);
        
        // Start producer and consumer threads
        producerThread.start();
        consumerThread.start();
        
        System.out.println("Data transfer started with 1 producer and 1 consumer");
    }
    
    /**
     * Starts a data transfer with multiple producers and multiple consumers.
     *
     * Items are evenly distributed among consumers. Any remainder is
     * handled by the last consumer.
     *
     * @param sourceContainers list of source containers for producers
     * @param destinationContainer shared destination container
     * @param numProducers number of producer threads to start
     * @param numConsumers number of consumer threads to start
     */
    public void startTransferMultiple(List<List<Item>> sourceContainers, List<Item> destinationContainer, int numProducers, int numConsumers) {
        if (isRunning) {
            System.out.println("Transfer already running");
            return;
        }
        
        isRunning = true;
        
        // Calculate total number of items to be produced across all sources
        int totalItems = 0;
        for (List<Item> source : sourceContainers) {
            totalItems += source.size();
        }
        
        // Distribute workload as evenly as possible among consumers
        int itemsPerConsumer = totalItems / numConsumers;
        int remainingItems = totalItems % numConsumers;
        
        // Start producer threads (one per source container)
        for (int i = 0; i < numProducers && i < sourceContainers.size(); i++) {
            Producer producer = new Producer(sourceContainers.get(i), sharedQueue, "Producer-" + (i + 1));
            Thread producerThread = new Thread(producer);
            producerThreads.add(producerThread);
            producerThread.start();
        }
        
        // Start consumer threads with assigned consumption quotas
        for (int i = 0; i < numConsumers; i++) {
            int itemsToConsume = itemsPerConsumer;
            if (i == numConsumers - 1) {
                itemsToConsume += remainingItems; // Last consumer handles remaining items
            }
            Consumer consumer = new Consumer(sharedQueue, destinationContainer, itemsToConsume, "Consumer-" + (i + 1));
            Thread consumerThread = new Thread(consumer);
            consumerThreads.add(consumerThread);
            consumerThread.start();
        }
        
        System.out.println("Data transfer started with " + numProducers + " producers and " + numConsumers + " consumers");
        System.out.println("Total items to transfer: " + totalItems);
    }
    
    /**
     * Stops the data transfer by interrupting all producer and consumer threads.
     */
    public void stopTransfer() {
        // Interrupt all active producer and consumer threads
        for (Thread thread : producerThreads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        for (Thread thread : consumerThreads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        isRunning = false;
        System.out.println("Data transfer stopped");
    }
    
    /**
     * Returns a human-readable status of the shared queue.
     *
     * @return queue status string
     */
    public String getQueueStatus() {
        return sharedQueue.getQueueStatus();
    }
    
    /**
     * Blocks until all producer and consumer threads have completed execution.
     *
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public void waitForCompletion() throws InterruptedException {
        for (Thread thread : producerThreads) {
            if (thread != null) {
                thread.join();
            }
        }
        for (Thread thread : consumerThreads) {
            if (thread != null) {
                thread.join();
            }
        }
        isRunning = false;
        System.out.println("Data transfer completed");
    }
}