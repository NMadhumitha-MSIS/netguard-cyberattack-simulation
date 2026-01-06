package netguard;

/**
 * Linked Bag for storing the neighboring Nodes
 */
public class Bag implements BagInterface{

    private Node head;
    private int count = 0;

    private static class Node {

        int item;
        Node next;

        Node(int item) {
            this.item = item;
        }
    }

    /**
     * Add next node(neighbor) into the bag
     *
     * @param item
     */
    public void add(int item) {
        Node n = new Node(item);
        n.next = head;
        head = n;
        count++;
    }

    /**
     * Convert Bag into ArrayList
     *
     * @return List of all neighbor nodes
     */
    public int[] toArray() {
        int[] arr = new int[count];
        int i = 0;

        for (Node x = head; x != null; x = x.next) {
            arr[i++] = x.item;
        }

        return arr;
    }

    /**
     * Remove all the items from the Bag
     */
    public void clear() {
        head = null;
        count = 0;
    }

    /**
     * Remove only one item from the Bag
     *
     * @param value item to remove from the bag
     */
    public void remove(int value) {
        Node prev = null;
        Node cur = head;

        while (cur != null) {
            if (cur.item == value) {
                if (prev == null) {
                    head = cur.next;
                } else {
                    prev.next = cur.next;
                }
                count--;
                return;
            }
            prev = cur;
            cur = cur.next;
        }
    }

    /**
     * Number of items remaining in the Bag
     *
     * @return Count of items
     */
    public int size() {
        return count;
    }
}
