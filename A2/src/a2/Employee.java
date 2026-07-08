package a2;

public class Employee implements Comparable<Employee> {
    private final int id;
    private final String name;
    private final double hoursWorked;
    private final double hourlyRate;
    private final double deductionProvince;
    private final double deductionFederal;
    private final double educationAllowance;
    private double calculatedHourlySalary;

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

    /**
     * Compare by calculated hourly salary only.
     */
    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.calculatedHourlySalary, other.calculatedHourlySalary);
    }
}