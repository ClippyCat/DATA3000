package a2;

import java.util.Comparator;

public class Employee implements Comparable<Employee> {

    /**
     * Orders employees by calculated hourly salary, ascending. Employee's
     * natural ordering is by name, so use this comparator (for example with
     * {@link SelectionSort#sort(Object[], Comparator)}) when a salary ordering
     * is required.
     */
    public static final Comparator<Employee> BY_SALARY =
            Comparator.comparingDouble(Employee::getCalculatedHourlySalary);

    private final int id;
    private final String name;
    private final double hoursWorked;
    private final double hourlyRate;
    private final double deductionProvince;
    private final double deductionFederal;
    private final double educationAllowance;
    private double calculatedHourlySalary;
    private static boolean compareBySalary = true;

    public Employee(
            int id,
            String name,
            double hoursWorked,
            double hourlyRate,
            double deductionProvince,
            double deductionFederal,
            double educationAllowance) {
        this.id = id;
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.deductionProvince = deductionProvince;
        this.deductionFederal = deductionFederal;
        this.educationAllowance = educationAllowance;
        calcHourlySalary();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getDeductionProvince() {
        return deductionProvince;
    }

    public double getDeductionFederal() {
        return deductionFederal;
    }

    public double getEducationAllowance() {
        return educationAllowance;
    }

    public double getCalculatedHourlySalary() {
        return calculatedHourlySalary;
    }

    /**
     * Calculates and stores the employee's salary using the documented assumption.
     */
    public void calcHourlySalary() {
        double grossPay = (hoursWorked * hourlyRate) + educationAllowance;
        double totalDeductionRate = deductionProvince + deductionFederal;
        calculatedHourlySalary = grossPay * (1.0 - totalDeductionRate);
    }

    public static void setCompareBySalary(boolean isCompareBySalary) 
    {
       compareBySalary = isCompareBySalary;
    }

    /**
     * Natural ordering is by name, compared case-sensitively with
     * {@link String#compareTo(String)}. Use {@link #BY_SALARY} for salary order.
     */
    @Override
    public int compareTo(Employee other) {
        return compareBySalary ? Double.compare(this.calculatedHourlySalary, other.calculatedHourlySalary):this.name.compareToIgnoreCase(other.name);
    }
}
