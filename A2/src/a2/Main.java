package a2;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final String SALARY_OUTPUT_FILE = "sortedemployeeBySalary.csv";
    private static final String NAME_OUTPUT_FILE = "sortedemployeeByName.csv";

    public static void main(String[] args) {

        // Starting message using JOptionPane
        JOptionPane.showMessageDialog(
                null,
                "Employee Data Sorting and Searching Program!\n\nPress OK to Start",
                "Welcome to",
                JOptionPane.INFORMATION_MESSAGE
        );

        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the full employee file path
        System.out.print("Enter the full path of employee data file > ");
        String inputFilePath = scanner.nextLine().trim();

        System.out.println("Read employee data from file " + inputFilePath);

        // Read employee data from the file
        Employee[] employees = EmployeeFileReader.readEmployees(inputFilePath);

        // Count only valid employees because the array may contain null spaces
        int employeeCount = countEmployees(employees);

        if (employeeCount == 0) {
            System.out.println("No employee data was loaded. Program ended.");
            scanner.close();
            return;
        }

        // Create separate arrays because sorting changes the order of the array
        Employee[] employeesBySalary = copyEmployees(employees, employeeCount);
        Employee[] employeesByName = copyEmployees(employees, employeeCount);

        // Measure Selection Sort running time
        long selectionStartTime = System.currentTimeMillis();

        SelectionSort.sort(employeesBySalary, Employee.BY_SALARY);

        long selectionEndTime = System.currentTimeMillis();
        long selectionSortTime = selectionEndTime - selectionStartTime;

        // Measure Quick Sort running time
        long quickStartTime = System.currentTimeMillis();

        QuickSort.sort(employeesByName);

        long quickEndTime = System.currentTimeMillis();
        long quickSortTime = quickEndTime - quickStartTime;

        // Print sorting performance results
        System.out.println();
        System.out.println("The performance of our sorting algorithms");
        System.out.println("###########################################");
        System.out.println("Selection Sort Time > " + selectionSortTime + " ms");
        System.out.println("Quick Sort Time > " + quickSortTime + " ms");
        System.out.println("###########################################");
        System.out.println();

        // Build output file paths in the same folder as the input file
        String salaryOutputPath = buildOutputPath(inputFilePath, SALARY_OUTPUT_FILE);
        String nameOutputPath = buildOutputPath(inputFilePath, NAME_OUTPUT_FILE);

        // Write sorted employee data into output files
        try {
            writeEmployeesToCsv(employeesBySalary, salaryOutputPath);
            System.out.println("Write employee data sorted by their hourly salaries into file > " + salaryOutputPath);

            writeEmployeesToCsv(employeesByName, nameOutputPath);
            System.out.println("Write employee data sorted by their names into file > " + nameOutputPath);

        } catch (IOException e) {
            System.out.println("Error writing output files: " + e.getMessage());
        }

        System.out.println();

        // Ask user for the employee name to search
        System.out.print("Enter the name of the employee to search > ");
        String targetName = scanner.nextLine().trim();

        // Binary search must be performed on the array sorted by name
        int foundIndex = BinarySearch.search(employeesByName, targetName);

        // Print the binary search result
        if (foundIndex != -1) {
            System.out.println("Employee found at index > " + foundIndex);
            System.out.println("Employee name > " + employeesByName[foundIndex].getName());
        } else {
            System.out.println("Employee name was not found.");
        }

        scanner.close();
    }

    // Counts the number of non-null employees in the array
    private static int countEmployees(Employee[] employees) {
        int count = 0;

        if (employees == null) {
            return count;
        }

        for (Employee employee : employees) {
            if (employee != null) {
                count++;
            }
        }

        return count;
    }

    // Copies only valid employee objects into a new smaller array
    private static Employee[] copyEmployees(Employee[] employees, int employeeCount) {
        Employee[] copy = new Employee[employeeCount];
        int index = 0;

        for (Employee employee : employees) {
            if (employee != null && index < employeeCount) {
                copy[index] = employee;
                index++;
            }
        }

        return copy;
    }

    // Creates the output file path in the same folder as the input file
    private static String buildOutputPath(String inputFilePath, String outputFileName) {
        File inputFile = new File(inputFilePath);
        File parentFolder = inputFile.getParentFile();

        if (parentFolder == null) {
            return outputFileName;
        }

        return new File(parentFolder, outputFileName).getAbsolutePath();
    }

    // Writes employee data to CSV file using the required format
    private static void writeEmployeesToCsv(Employee[] employees, String outputFilePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {

            for (Employee employee : employees) {
                writer.printf(
                        Locale.US,
                        "%d,%s,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                        employee.getId(),
                        employee.getName(),
                        employee.getHoursWorked(),
                        employee.getHourlyRate(),
                        employee.getDeductionProvince(),
                        employee.getDeductionFederal(),
                        employee.getEducationAllowance()
                );
            }
        }
    }
}
