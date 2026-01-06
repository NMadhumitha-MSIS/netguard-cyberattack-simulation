package netguard;

public interface HeapInterface<T> {

    /**
     * Adding new Element to the heap Property to be maintained parent <= child
     *
     * @param value
     */
    public void insert(T value);

    /**
     * Removes and returns the smallest element
     *
     * @return smallest element
     */
    public T removeMin();

    /**
     * Check if the heap is empty
     *
     * @return true or false
     */
    public boolean isEmpty();

}
