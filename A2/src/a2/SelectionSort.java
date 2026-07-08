package a2;

import java.util.Comparator;

public class SelectionSort {

    /**
     * Performs selection sort on an array of objects and object should implement Comparable interface.
     * @param array array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] array) {

        for (int i = 0; i < array.length - 1; i++) {

            int smallestIndex = i;

            // Find the smallest element in remaining array
            for (int j = i + 1; j < array.length; j++) {

                if (array[j].compareTo(array[smallestIndex]) < 0) {

                    smallestIndex = j;
                }
            }

            // Swap the smallest element with current position
            if (smallestIndex != i) {

                T temp = array[i];
                array[i] = array[smallestIndex];
                array[smallestIndex] = temp;
            }
        }
    }

    /**
     * Performs selection sort on an array using the supplied comparator, for
     * orderings other than the elements' natural ordering (for example
     * {@link Employee#BY_SALARY}).
     * @param array array to be sorted
     * @param comparator ordering to sort by
     * @param <T> element type
     */
    public static <T> void sort(T[] array, Comparator<T> comparator) {

        for (int i = 0; i < array.length - 1; i++) {

            int smallestIndex = i;

            // Find the smallest element in remaining array
            for (int j = i + 1; j < array.length; j++) {

                if (comparator.compare(array[j], array[smallestIndex]) < 0) {

                    smallestIndex = j;
                }
            }

            // Swap the smallest element with current position
            if (smallestIndex != i) {

                T temp = array[i];
                array[i] = array[smallestIndex];
                array[smallestIndex] = temp;
            }
        }
    }
}
