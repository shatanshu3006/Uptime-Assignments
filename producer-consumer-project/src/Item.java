/**
 * Item represents a single unit of data transferred through the
 * Producer-Consumer pipeline.
 *
 * This is a simple immutable-style data holder (except timestamp initialization)
 * designed to be safely shared across threads after construction.
 *
 * Typical usage:
 * - Created by Producer
 * - Enqueued into SharedQueue
 * - Dequeued and processed by Consumer
 */
public class Item {
    // Unique identifier for the item
    private int id;
    // Payload or content carried by this item
    private String data;
    // Creation timestamp (milliseconds since epoch)
    private long timestamp;
    
    /**
     * Constructs an Item with an identifier and payload.
     * The timestamp is automatically set at creation time.
     *
     * @param id   unique identifier for the item
     * @param data payload/content of the item
     */
    public Item(int id, String data) {
        this.id = id;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Returns the unique identifier of this item.
     *
     * @return item id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the payload/content of this item.
     *
     * @return item data
     */
    public String getData() {
        return data;
    }
    
    /**
     * Returns the creation timestamp of this item.
     *
     * @return timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Returns a human-readable string representation of the item.
     *
     * Useful for logging and debugging.
     *
     * @return string representation of the item
     */
    @Override
    public String toString() {
        return "Item[id=" + id + ", data=" + data + ", timestamp=" + timestamp + "]";
    }
}