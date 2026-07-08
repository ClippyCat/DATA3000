package a2;

/**
 * Utility class that performs a recursive binary search over an array sorted in
 * ascending order by its natural ordering.
 *
 * The generic {@link #search(Comparable[], Comparable)} method works for any
 * {@link Comparable} type. Because {@link Employee}'s natural ordering is by
 * name (case-sensitive), searching an Employee array locates employees by name;
 * a convenience overload accepts the name directly.
 *
 * The array passed in MUST already be sorted by the same natural ordering
 * (for example the output of a name sort), otherwise the result is undefined.
 */
public final class BinarySearch {

    private BinarySearch() {
        // Utility class; prevent instantiation.
    }

    /**
     * Searches a sorted array for an element equal, by natural ordering, to
     * {@code target}.
     *
     * If several elements are equal to the target, the index of the FIRST
     * occurrence is returned. If none match, -1 is returned.
     *
     * @param array  array sorted ascending by natural ordering
     * @param target the element to search for
     * @param <T>    a Comparable element type
     * @return index of the first matching element, or -1 if not found
     */
    public static <T extends Comparable<T>> int search(T[] array, T target) {
        if (array == null || target == null) {
            return -1;
        }
        return search(array, target, 0, array.length - 1);
    }

    /**
     * Convenience overload: searches a name-sorted employee array for the given
     * name. Builds a probe Employee whose only meaningful field is the name
     * (Employee.compareTo compares by name only) and delegates to the generic
     * search above.
     *
     * @param employees  array of employees sorted ascending by name
     * @param targetName the employee name to search for
     * @return index of the first matching employee, or -1 if not found
     */
    public static int search(Employee[] employees, String targetName) {
        if (employees == null || targetName == null) {
            return -1;
        }
        Employee probe = new Employee(0, targetName, 0, 0, 0, 0, 0);
        return search(employees, probe);
    }

    /**
     * Recursive helper that searches the inclusive index range [low, high].
     *
     * @param array  the sorted array
     * @param target the element being searched for
     * @param low    lowest index still in range
     * @param high   highest index still in range
     * @param <T>    a Comparable element type
     * @return index of the first matching element within the range, or -1
     */
    private static <T extends Comparable<T>> int search(T[] array, T target, int low, int high) {
        if (low > high) {
            return -1; // Empty range: the target is not present in this section.
        }

        int mid = low + (high - low) / 2; // Avoids overflow compared to (low + high) / 2.
        int comparison = array[mid].compareTo(target);

        if (comparison < 0) {
            // Midpoint comes before the target: search the right half.
            return search(array, target, mid + 1, high);
        } else if (comparison > 0) {
            // Midpoint comes after the target: search the left half.
            return search(array, target, low, mid - 1);
        } else {
            // Match found. Keep searching left so duplicates return the first index.
            int earlier = search(array, target, low, mid - 1);
            return (earlier != -1) ? earlier : mid;
        }
    }
}
