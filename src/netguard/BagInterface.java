package netguard;

public interface BagInterface {

    /**
     * Adds an item to the bag
     *
     * @param value the item to add
     */
    public void add(int value);

    /**
     * Remove a specified item from the bag
     *
     * @param value the item to remove
     */
    public void remove(int value);

    /**
     * Returns number of items in the Bag
     *
     * @return size
     */
    public int size();

    /**
     * Convert Bag into ArrayList
     *
     * @return List of all items in Bag
     */
    public int[] toArray();

    /**
     * Clear all the items in the bag
     */
    public void clear();
}
