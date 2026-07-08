package a2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public final class RuntimeMeasurement {

    public static final String SALARY_SORTED_FILE = "sortedemployeeBySalary.csv";
    public static final String NAME_SORTED_FILE = "sortedemployeeByName.csv";

    private RuntimeMeasurement() {
        // Utility class; prevent instantiation.
    }

    /**
     * Runs both sorting algorithms on copies of the given array, reports
     * timing to the console, and writes the results to CSV files in the
     * current working directory.
     *
     * @param employees the loaded, unsorted employee array (not modified)
     * @return a SortResult bundling both sorted arrays and their timings
     * @throws IOException if either output file cannot be written
     */
    public static SortResult runPerformanceTest(Employee[] employees) throws IOException {
        // Sort independent copies so neither algorithm affects the other's input.
        Employee[] selectionArray = Arrays.copyOf(employees, employees.length);
        Employee[] quickArray = Arrays.copyOf(employees, employees.length);

        long selectionStart = System.currentTimeMillis();
        SelectionSort.sort(selectionArray);
        long selectionTime = System.currentTimeMillis() - selectionStart;

        long quickStart = System.currentTimeMillis();
        QuickSort.sort(quickArray);
        long quickTime = System.currentTimeMillis() - quickStart;

        printPerformanceSummary(selectionTime, quickTime);

        writeEmployeesToCsv(selectionArray, SALARY_SORTED_FILE);
        System.out.println("Write employee data sorted by their hourly salaries into file -> "
                + SALARY_SORTED_FILE);

        writeEmployeesToCsv(quickArray, NAME_SORTED_FILE);
        System.out.println("Write employee data sorted by their names into file -> "
                + NAME_SORTED_FILE);

        return new SortResult(selectionArray, quickArray, selectionTime, quickTime);
    }

    /**
     * Prints the running-time comparison banner, matching the format shown
     * in the assignment's sample runs.
     */
    private static void printPerformanceSummary(long selectionTimeMs, long quickTimeMs) {
        System.out.println();
        System.out.println("The performance of our sorting algorithms");
        System.out.println("###########################################");
        System.out.println("Selection Sort Time -> " + selectionTimeMs + " ms");
        System.out.println("Quick Sort Time -> " + quickTimeMs + " ms");
        System.out.println("###########################################");
        System.out.println();
    }

    /**
     * Writes an employee array to a CSV file using the same field order as
     * the input file, comma-separated:
     * id,name,hoursWorked,hourlyRate,deductionProvince,deductionFederal,educationAllowance
     *
     * The file is created (or overwritten) in the current working directory
     * and the resource is always closed via try-with-resources.
     *
     * @param employees the array to write, in its current order
     * @param filePath  destination CSV file path
     * @throws IOException if the file cannot be created or written
     */
    public static void writeEmployeesToCsv(Employee[] employees, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                if (employee == null) {
                    continue; // Skip unused slots in a fixed-size array.
                }
                writer.write(formatCsvLine(employee));
                writer.newLine();
            }
        }
    }

    /**
     * Formats a single employee as a comma-separated line matching the
     * required output format, e.g.:
     * 1,John Smith,40,15.50,0.05,0.10,1000.00
     */
    private static String formatCsvLine(Employee employee) {
        return employee.getId() + ","
                + employee.getName() + ","
                + String.format("%.2f", employee.getHoursWorked()) + ","
                + String.format("%.2f", employee.getHourlyRate()) + ","
                + String.format("%.2f", employee.getDeductionProvince()) + ","
                + String.format("%.2f", employee.getDeductionFederal()) + ","
                + String.format("%.2f", employee.getEducationAllowance());
    }

    /**
     * Data holder returned by {@link #runPerformanceTest}.
     *
     * Bundles both sorted arrays together with how long each sort took, so
     * the Main program (Member 6) can grab the name-sorted array to feed
     * into BinarySearch without re-running anything.
     */
    public static final class SortResult {

        private final Employee[] selectionSortedBySalary;
        private final Employee[] quickSortedByName;
        private final long selectionSortTimeMs;
        private final long quickSortTimeMs;

        public SortResult(
                Employee[] selectionSortedBySalary,
                Employee[] quickSortedByName,
                long selectionSortTimeMs,
                long quickSortTimeMs) {
            this.selectionSortedBySalary = selectionSortedBySalary;
            this.quickSortedByName = quickSortedByName;
            this.selectionSortTimeMs = selectionSortTimeMs;
            this.quickSortTimeMs = quickSortTimeMs;
        }

        /** Employees sorted ascending by calculated hourly salary (SelectionSort output). */
        public Employee[] getSelectionSortedBySalary() {
            return selectionSortedBySalary;
        }

        /** Employees sorted ascending by name (QuickSort output) — use this for BinarySearch. */
        public Employee[] getQuickSortedByName() {
            return quickSortedByName;
        }

        public long getSelectionSortTimeMs() {
            return selectionSortTimeMs;
        }

        public long getQuickSortTimeMs() {
            return quickSortTimeMs;
        }
    }
}
