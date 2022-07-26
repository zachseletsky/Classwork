import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("insertionSort input is null");
        }

        for (int i = 0; i < arr.length; i++) {
            int j = i;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j--;
            }
        }
    }


    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("cocktailSort input is null");
        }
        boolean swapsMade = true;
        int start = 0;
        int end = arr.length - 1;

        while (swapsMade) {
            swapsMade = false;
            int j = end;
            for (int i = start; i < j; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    end = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                j = start;
                for (int i = end; i > j; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = temp;
                        swapsMade = true;
                        start = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("mergeSort input is null");
        }
        rMergeSort(arr, comparator);
    }

    /**
     * Recursive helper method for mergeSort
     *
     * @param arr Array being sorted
     * @param comparator Comparator object being used to sort
     * @param <T> data type being sorted
     */
    private static <T> void rMergeSort(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[length - midIndex];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = midIndex; i < length; i++) {
            right[i - midIndex] = arr[i];
        }
        rMergeSort(left, comparator);
        rMergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) > 0) {
                arr[i + j] = right[j];
                j++;
            } else {
                arr[i + j] = left[i];
                i++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }


    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("quickSort input is null");
        }
        rQuickSort(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Recursive Helper Method for quickSort
     *
     * @param arr the array that is being sorted
     * @param comparator Comparator object being used to sort
     * @param rand Random object used to determine pivot position
     * @param start starting index of sub-array
     * @param end ending index (inclusive) of sub-array
     * @param <T> data type being sorted
     */
    public static <T> void rQuickSort(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        if (end - start == 1) {
            if (comparator.compare(arr[start], arr[end]) > 0) {
                T temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        } else {
            int pivotIdx = rand.nextInt(end - start + 1) + start;
            T pivotVal = arr[pivotIdx];
            arr[pivotIdx] = arr[start];
            arr[start] = pivotVal;
            int i = start + 1;
            int j = end;
            while (j - i > -1) {
                while (j - i > -1 && comparator.compare(pivotVal, arr[i]) > -1) {
                    i++;
                }
                while (j - i > -1 && comparator.compare(arr[j], pivotVal) > -1) {
                    j--;
                }
                if (j - i > -1) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    i++;
                    j--;
                }
            }
            arr[start] = arr[j];
            arr[j] = pivotVal;
            rQuickSort(arr, comparator, rand, start, j - 1);
            rQuickSort(arr, comparator, rand, j + 1, end);
        }

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("lsdRadixSort input is null");
        }
        int k = 0; //max digits
        int tk; //temp digits
        for (int temp : arr) {
            tk = 1;
            while (temp / 10 != 0) {
                temp = temp / 10;
                tk++;
            }
            if (tk > k) {
                k = tk;
            }
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int h = 0;
                int digit = arr[j];
                while (h < i) {
                    digit = digit / 10;
                    h++;
                }
                digit = digit % 10;
                buckets[digit + 9].add(arr[j]);
            }
            int idx = 0;
            for (LinkedList bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx] = (int) bucket.remove();
                    idx++;
                }
            }

        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("heapSort input is null");
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = queue.remove();
        }
        return sorted;

    }
}
