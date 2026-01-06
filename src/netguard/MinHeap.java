/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netguard;

/**
 * To find the root or minimum path
 */
public class MinHeap<T extends Comparable<T>> implements HeapInterface<T>{

    private T[] heap = (T[]) new Comparable[64];
    private int size = 0;

    /**
     * Adding new Element to the heap 
     * Property to be maintained parent <= child
     * 
     * @param value
     */
    public void insert(T value) {
        if (size == heap.length - 1) {
            resize();
        }
        heap[++size] = value;
        swim(size);
    }

    /**
     * Removes and returns the smallest element
     * @return smallest element
     */
    public T removeMin() {
        if (size == 0) {
            return null;
        }

        T min = heap[1];
        heap[1] = heap[size--];
        sink(1);
        return min;
    }

    /**
     * Check if the heap is empty
     * @return true or false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Place the minimum element to its correct position to restore heap property
     * Moves element up to heap
     * @param k 
     */
    private void swim(int k) {
        while (k > 1 && heap[k].compareTo(heap[k / 2]) < 0) {
            swap(k, k / 2);
            k /= 2;
        }
    }

    /**
     * Sink element down to the correct position
     * @param k 
     */
    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && heap[j + 1].compareTo(heap[j]) < 0) {
                j++;
            }
            if (heap[k].compareTo(heap[j]) <= 0) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    /**
     * Swap the parameters or the elements
     * @param a
     * @param b 
     */
    private void swap(int a, int b) {
        T t = heap[a];
        heap[a] = heap[b];
        heap[b] = t;
    }

    /**
     * Double the size of the heap if its full
     */
    private void resize() {
        T[] newH = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, newH, 0, heap.length);
        heap = newH;
    }
}
