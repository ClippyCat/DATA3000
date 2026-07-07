package a2;

/**
 * Utility class that performs a recursive binary search over an array of
 * {@link Employee} objects sorted in ascending order by name (for example,
 * the output of QuickSort).
 *
 * Names are compared with {@link String#compareTo(String)}, which is
 * case-sensitive. The array passed in MUST be sorted with this same ordering,
 * otherwise the search result is undefined.
 */
public final class BinarySearch {

    private BinarySearch() {
        // Utility class; prevent instantiation.
    }

    /**
     * Searches a name-sorted employee array for the given name.
     *
     * If the name occurs more than once, the index of the FIRST occurrence is
     * returned. If the name is not present, -1 is returned.
     *
     * @param employees  array of employees sorted ascending by name
     * @param targetName the employee name to search for
     * @return index of the first matching employee, or -1 if not found
     */
    public static int search(Employee[] employees, String targetName) {
        if (employees == null || targetName == null) {
            return -1;
        }
        return search(employees, targetName, 0, employees.length - 1);
    }

    /**
     * Recursive helper that searches the inclusive index range [low, high].
     *
     * @param employees  the name-sorted employee array
     * @param targetName the name being searched for
     * @param low        lowest index still in range
     * @param high       highest index still in range
     * @return index of the first matching employee within the range, or -1
     */
    private static int search(Employee[] employees, String targetName, int low, int high) {
        if (low > high) {
            return -1; // Empty range: the name is not present in this section.
        }

        int mid = low + (high - low) / 2; // Avoids overflow compared to (low + high) / 2.
        int comparison = employees[mid].getName().compareTo(targetName);

        if (comparison < 0) {
            // Midpoint name comes before the target: search the right half.
            return search(employees, targetName, mid + 1, high);
        } else if (comparison > 0) {
            // Midpoint name comes after the target: search the left half.
            return search(employees, targetName, low, mid - 1);
        } else {
            // Match found. Keep searching left so duplicates return the first index.
            int earlier = search(employees, targetName, low, mid - 1);
            return (earlier != -1) ? earlier : mid;
        }
    }
}
