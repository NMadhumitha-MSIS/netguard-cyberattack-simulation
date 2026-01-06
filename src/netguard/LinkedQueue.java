package netguard;

/**
 * Linked Queue to represent the Packets waiting Line First in First Out
 *
 * @param <T> Generic
 */
public class LinkedQueue<T> implements LinkedQueueInterface<T>{

    private Node<T> head;
    private Node<T> tail;
    private int count = 0;

    private static class Node<T> {

        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
        }
    }

    /**
     * Add the Packets to the Queue Packets waiting in Queue to enter the
     * network
     *
     * @param item Packet
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        count++;
    }

    /**
     * Remove the packet from the queue Packets entering the network
     *
     * @return Packet
     */
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T value = head.item;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        count--;
        return value;
    }

    /**
     * Check if the queue is Empty
     *
     * @return true or False
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Number of items/packets in the Queue
     *
     * @return count
     */
    public int size() {
        return count;
    }

	
}
