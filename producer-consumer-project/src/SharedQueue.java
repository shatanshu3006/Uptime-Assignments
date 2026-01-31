import java.util.LinkedList;
import java.util.Queue;

public class SharedQueue {
    private Queue<Item> queue;
    private int capacity;
    private Object lock;
    
    public SharedQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<Item>();
        this.lock = new Object();
    }
    
    public void enqueue(Item item, String producerName) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() >= capacity) {
                lock.wait();
            }
            queue.add(item);
            System.out.println(producerName + " produced: " + item);
            lock.notifyAll();
        }
    }
    
    public Item dequeue(String consumerName) throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            Item item = queue.remove();
            System.out.println(consumerName + " consumed: " + item);
            lock.notifyAll();
            return item;
        }
    }
    
    // Keep original methods for backward compatibility
    public void enqueue(Item item) throws InterruptedException {
        enqueue(item, "Producer");
    }
    
    public Item dequeue() throws InterruptedException {
        return dequeue("Consumer");
    }
    
    public String getQueueStatus() {
        synchronized (lock) {
            return "Queue size: " + queue.size() + "/" + capacity;
        }
    }
    
    public int getSize() {
        synchronized (lock) {
            return queue.size();
        }
    }
}