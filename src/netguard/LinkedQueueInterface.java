package netguard;

public interface LinkedQueueInterface<T> {

    /**
     * Add the item to the Queue
     *
     * @param item Packet
     */
    public void enqueue(T item);

    /**
     * Remove the item from the queue
     *
     * @return Packet
     */
    public T dequeue();

    /**
     * Check if the queue is Empty
     *
     * @return true or False
     */
    public boolean isEmpty();

    /**
     * Number of items/packets in the Queue
     *
     * @return count
     */
    public int size();
}
