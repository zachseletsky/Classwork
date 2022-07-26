import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Zachary Seletsky
 * @version 1.0
 * @userid zseletsky3
 * @GTID 903360808
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
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
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
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The ArrayList passed in is null");
        }
        int i = 1;
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = 0;
        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException("Data in the passed in ArrayList is null");
            }
            backingArray[i] = datum;
            i++;
            size++;
        }
        T temp;
        for (int j = (size - (size % 2)) / 2; j > 0; j--) {
            int k = j;
            int right = 2 * j + 1;
            int left = 2 * j;
            temp = backingArray[j];
            if (right > size) { //means no last right child
                if (backingArray[j].compareTo(backingArray[left]) > 0) {
                    backingArray[j] = backingArray[left];
                    backingArray[left] = temp;
                    k = left;
                }
            } else if (backingArray[j].compareTo(backingArray[right]) > 0
                    && backingArray[j].compareTo(backingArray[left]) > 0) { // parent greater than both children
                if (backingArray[right].compareTo(backingArray[left]) > 0) { //right child greater than left child
                    backingArray[j] = backingArray[left];
                    backingArray[left] = temp;
                    k = left;
                } else { //left child greater than right
                    backingArray[j] = backingArray[right];
                    backingArray[right] = temp;
                    k = right;
                }
            } else if (backingArray[j].compareTo(backingArray[left]) > 0) { //parent is greater than left child
                backingArray[j] = backingArray[left];
                backingArray[left] = temp;
                k = left;
            } else if (backingArray[j].compareTo(backingArray[right]) > 0) { //parent is greater than right child
                backingArray[j] = backingArray[right];
                backingArray[right] = temp;
                k = right;
            }

            boolean cont = true;
            while (cont) {
                temp = backingArray[k];
                if (k * 2 > size) { //if it has no children
                    cont = false;
                } else if (k * 2 + 1 > size) { //if it has only left child
                    if (backingArray[k * 2].compareTo(backingArray[k]) > 0) { //single child is greater than parent
                        cont = false;
                    } else { //single child is less than parent
                        backingArray[k] = backingArray[k * 2];
                        backingArray[k * 2] = temp;
                        k *= 2;
                    }
                } else if (backingArray[k * 2].compareTo(backingArray[k]) > 0
                        && backingArray[k * 2 + 1].compareTo(backingArray[k]) > 0) { //if it has two children, and
                    //they're both greater than parent
                    cont = false;
                } else if ((backingArray[k * 2].compareTo(backingArray[k]) < 0
                        && backingArray[k * 2 + 1].compareTo(backingArray[k]) < 0)) { //both less than parent
                    if (backingArray[k * 2].compareTo(backingArray[k * 2 + 1]) < 0) { //left child is less than right
                        backingArray[k] = backingArray[k * 2];
                        backingArray[k * 2] = temp;
                        k *= 2;
                    } else { //right child is less than left child
                        backingArray[k] = backingArray[k * 2 + 1];
                        backingArray[k * 2 + 1] = temp;
                        k *= 2;
                        k++;
                    }
                } else if (backingArray[k * 2].compareTo(backingArray[k]) < 0) { //left child is less than parent
                    backingArray[k] = backingArray[k * 2];
                    backingArray[k * 2] = temp;
                    k *= 2;
                } else { //right child is less than parent
                    backingArray[k] = backingArray[k * 2 + 1];
                    backingArray[k * 2 + 1] = temp;
                    k *= 2;
                    k++;
                }
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in to add is null");
        }
        if (size == backingArray.length - 1) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;

        for (int i = size; i > 1; i--) {
            if (i % 2 == 0) {
                if (backingArray[i].compareTo(backingArray[i / 2]) < 0) {
                    T temp = backingArray[i / 2];
                    backingArray[i / 2] = backingArray[i];
                    backingArray[i] = temp;
                }
            } else {
                if (backingArray[i].compareTo(backingArray[(i - 1) / 2]) < 0) {
                    T temp = backingArray[(i - 1) / 2];
                    backingArray[(i - 1) / 2] = backingArray[i];
                    backingArray[i] = temp;
                }
            }

        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        int k = 1;
        T temp;
        boolean cont = true;
        while (cont) {
            if (k * 2 > size) { //if it has no children
                cont = false;
            } else if (k * 2 + 1 > size) { //if it has only left child
                if (backingArray[k * 2].compareTo(backingArray[k]) > 0) { //single child is greater than parent
                    cont = false;
                } else { //single child is less than parent
                    temp = backingArray[k];
                    backingArray[k] = backingArray[k * 2];
                    backingArray[k * 2] = temp;
                    k *= 2;
                }
            } else if (backingArray[k * 2].compareTo(backingArray[k]) > 0
                    && backingArray[k * 2 + 1].compareTo(backingArray[k]) > 0) {
                //if it has two children, and they're both greater than parent
                cont = false;
            } else if ((backingArray[k * 2].compareTo(backingArray[k]) < 0
                    && backingArray[k * 2 + 1].compareTo(backingArray[k]) < 0)) { //both less than parent
                if (backingArray[k * 2].compareTo(backingArray[k * 2 + 1]) < 0) { //left child is less than right child
                    temp = backingArray[k];
                    backingArray[k] = backingArray[k * 2];
                    backingArray[k * 2] = temp;
                    k *= 2;
                } else { //right child is less than left child
                    temp = backingArray[k];
                    backingArray[k] = backingArray[k * 2 + 1];
                    backingArray[k * 2 + 1] = temp;
                    k *= 2;
                    k++;
                }
            } else if (backingArray[k * 2].compareTo(backingArray[k]) < 0) { //left child is less than parent
                temp = backingArray[k];
                backingArray[k] = backingArray[k * 2];
                backingArray[k * 2] = temp;
                k *= 2;
            } else { //right child is less than parent
                temp = backingArray[k];
                backingArray[k] = backingArray[k * 2 + 1];
                backingArray[k * 2 + 1] = temp;
                k *= 2;
                k++;
            }
        }

        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
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
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
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
}
