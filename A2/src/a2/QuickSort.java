package a2;

public class QuickSort {

    // Sorts the employee array by name using the quicksort algorithm
    public static void sort(Employee[] employees) {
        quickSort(employees, 0, employees.length - 1);
    }

    // Sorts the part of the array between low and high
    private static void quickSort(Employee[] employees, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(employees, low, high);
            quickSort(employees, low, pivotIndex - 1);
            quickSort(employees, pivotIndex + 1, high);
        }
    }

    // Puts the pivot in its correct spot and returns its index
    private static int partition(Employee[] employees, int low, int high) {
        Employee pivot = employees[high];
        int i = low - 1;

        // Loop through and move names smaller than the pivot to the left
        for (int j = low; j < high; j++) {
            if (employees[j].getName().compareTo(pivot.getName()) <= 0) {
                i = i + 1;
                Employee temp = employees[i];
                employees[i] = employees[j];
                employees[j] = temp;
            }
        }

        // Put the pivot right after the smaller names
        Employee temp = employees[i + 1];
        employees[i + 1] = employees[high];
        employees[high] = temp;

        return i + 1;
    }
}
