import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Nitya Jani
 * @version 1.0
 * @userid njani8
 * @GTID 903598748
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null so you can't create a heap.");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Something in the list is null so the heap can't be built.");
            } else {
                backingArray[i + 1] = data.get(i);
                size = size + 1;
            }
        }
        for (int o = size / 2; o > 0; o--) {
            downHeap(o);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing and can't be added.");
        }
        if (size + 1 == backingArray.length) {
            T[] tempArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < size + 1; i++) {
                tempArr[i] = backingArray[i];
            }
            backingArray = tempArr;
        }
        backingArray[size + 1] = data;
        size = size + 1;
        upHeap(size);

    }

    /**
     * puts data in the right place in the heap
     *
     * @param i is the index
     */
    public void upHeap(int i) {
        if (i > 1 && backingArray[i / 2].compareTo(backingArray[i]) > 0) {
            T temp = backingArray[i / 2];
            backingArray[i / 2] = backingArray[i];
            backingArray[i] = temp;
            upHeap(i / 2);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("You can't remove anything because the heap is empty.");
        }
        T data = backingArray[1]; //this is the data in the root
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size = size - 1;
        downHeap(1);
        return data;
    }

    /**
     * puts the data in the right place by going down
     *
     * @param i is the current index
     */
    public void downHeap(int i) {
        if (i * 2 <= size) {
            int smallestKidIndex = i * 2;
            if (i * 2 + 1 <= size) {
                if (backingArray[i * 2 + 1].compareTo(backingArray[i * 2]) < 0) {
                    smallestKidIndex = i * 2 + 1;
                }
            }
            if (backingArray[smallestKidIndex].compareTo(backingArray[i]) < 0) {
                T temp = backingArray[smallestKidIndex];
                backingArray[smallestKidIndex] = backingArray[i];
                backingArray[i] = temp;
                downHeap(smallestKidIndex);
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty so there is no min.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /*public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<Integer>();
        heap.add(3);
        heap.add(389);
        heap.add(10);
        heap.add(48);
        heap.add(109);
        heap.add(101);
        heap.add(32);
        heap.add(102);
        heap.add(120);
        heap.add(38);
        heap.add(19);
        heap.add(100);
        heap.add(47);
        heap.add(78);
        heap.add(94);
        heap.add(329);

    }*/
}
