package a2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for reading employee data from a text file.
 */
public final class EmployeeFileReader {

    private static final int MAX_EMPLOYEES = 1000;

    private EmployeeFileReader() {
        // Utility class; prevent instantiation.
    }

    /**
     * Reads employees from the provided file path.
     *
     * @param filePath path to the employee data file
     * @return an array sized to 1000 containing loaded employees at the front
     */
    public static Employee[] readEmployees(String filePath) {
        Employee[] employees = new Employee[MAX_EMPLOYEES];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null && index < MAX_EMPLOYEES) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                Employee employee = parseEmployee(trimmed);
                if (employee != null) {
                    employees[index] = employee;
                    index++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Employee file not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error while reading employee file: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Parses one comma-separated line with format:
     * ID,Name,HoursWorked,HourlyRate,DeductionProvince,DeductionFederal,EducationAllowance
     *
     * Name may contain spaces; numeric fields are read from the end.
     */
    private static Employee parseEmployee(String line) {
        String[] parts = line.split("\\s*,\\s*");

        // At least: ID + Name + 5 numeric fields.
        if (parts.length < 7) {
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            double hoursWorked = Double.parseDouble(parts[parts.length - 5]);
            double hourlyRate = Double.parseDouble(parts[parts.length - 4]);
            double deductionProvince = Double.parseDouble(parts[parts.length - 3]);
            double deductionFederal = Double.parseDouble(parts[parts.length - 2]);
            double educationAllowance = Double.parseDouble(parts[parts.length - 1]);

            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 1; i <= parts.length - 6; i++) {
                if (nameBuilder.length() > 0) {
                    nameBuilder.append(' ');
                }
                nameBuilder.append(parts[i]);
            }

            return new Employee(
                    id,
                    nameBuilder.toString(),
                    hoursWorked,
                    hourlyRate,
                    deductionProvince,
                    deductionFederal,
                    educationAllowance);
        } catch (NumberFormatException e) {
            // Skip malformed lines that do not follow the required numeric format.
            return null;
        }
    }
}