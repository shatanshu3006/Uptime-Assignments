import java.util.List;

public class Producer implements Runnable {
    private List<Item> sourceContainer;
    private SharedQueue sharedQueue;
    private String producerName;
    
    public Producer(List<Item> sourceContainer, SharedQueue sharedQueue, String producerName) {
        this.sourceContainer = sourceContainer;
        this.sharedQueue = sharedQueue;
        this.producerName = producerName;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(producerName + " started");
            for (Item item : sourceContainer) {
                sharedQueue.enqueue(item, producerName);
                Thread.sleep(100); // Simulate some processing time
            }
            System.out.println(producerName + " finished producing all items");
        } catch (InterruptedException e) {
            System.out.println(producerName + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}