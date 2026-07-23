package ADTStack;

// This class is used to test the PostfixCalculator.
// It checks normal cases and edge cases to make sure the calculator works correctly.
public class PostfixCalculatorEdgeCaseTest {

    // Counts how many tests passed
    private static int passed = 0;

    // Counts how many tests failed
    private static int failed = 0;

    // The main method runs all test cases
    public static void main(String[] args) {

        // Test normal postfix expression with numbers
        testValidNumberExpression();

        // Test postfix expression with variables
        testValidVariableExpression();

        // Test empty input
        testEmptyExpression();

        // Test a variable that was not added to the BST
        testUnknownVariable();

        // Test division by zero
        testDivisionByZero();

        // Test an invalid symbol/token
        testMalformedToken();

        // Test an expression with not enough operands
        testMissingOperands();

        // Test an expression with extra operands left over
        testExtraOperands();

        // Print final test summary
        System.out.println();
        System.out.println("SUMMARY: " + passed + " passed, " + failed + " failed.");

        // If any test failed, exit the program with error status
        if (failed > 0) {
            System.exit(1);
        }
    }

    // This method checks if a test condition is true or false.
    // If the condition is true, the test passes.
    // If the condition is false, the test fails.
    private static void check(String description, boolean condition) {

        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }

    // This method is used when we expect the calculator to throw an exception.
    // For example, division by zero should create an exception.
    private static void expectException(String description, Runnable action) {

        try {
            // Try to run the test action
            action.run();

            // If no exception happens, the test fails because we expected an error
            failed++;
            System.out.println("FAIL: " + description + " (no exception)");

        } catch (Exception e) {
            // If an exception happens, the test passes
            passed++;

            // Print the type of exception and its message
            System.out.println("PASS: " + description + " -> "
                    + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    // This test checks a normal postfix expression using numbers only.
    // Expression: 2 3 +
    // Meaning: 2 + 3
    // Expected result: 5
    private static void testValidNumberExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        int result = calculator.evaluatePostfixExpression("2 3 +");

        check("normal expression 2 3 + returns 5", result == 5);
    }

    // This test checks a postfix expression using variables.
    // x = 5, y = 3, z = 4
    // Expression: x y + z *
    // Meaning: (x + y) * z
    // Expected result: (5 + 3) * 4 = 32
    private static void testValidVariableExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        // Add variables into the Binary Search Tree
        calculator.setVariable("x", 5);
        calculator.setVariable("y", 3);
        calculator.setVariable("z", 4);

        int result = calculator.evaluatePostfixExpression("x y + z *");

        check("variable expression x y + z * returns 32", result == 32);
    }

    // This test checks what happens when the expression is empty.
    // The calculator should reject it and throw an exception.
    private static void testEmptyExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("empty expression is rejected",
                () -> calculator.evaluatePostfixExpression(""));
    }

    // This test checks what happens when a variable is missing.
    // Only x is added, but y is used in the expression.
    // The calculator should throw an exception because y is unknown.
    private static void testUnknownVariable() {

        PostfixCalculator calculator = new PostfixCalculator();

        calculator.setVariable("x", 5);

        expectException("unknown variable is rejected",
                () -> calculator.evaluatePostfixExpression("x y +"));
    }

    // This test checks division by zero.
    // Expression: 5 0 /
    // Meaning: 5 divided by 0
    // The calculator should throw an exception.
    private static void testDivisionByZero() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("division by zero is rejected",
                () -> calculator.evaluatePostfixExpression("5 0 /"));
    }

    // This test checks an invalid token.
    // The @ symbol is not a number, variable, or valid operator.
    // The calculator should reject it.
    private static void testMalformedToken() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("malformed token is rejected",
                () -> calculator.evaluatePostfixExpression("5 2 @"));
    }

    // This test checks an expression with missing operands.
    // Expression: 5 +
    // The + operator needs two operands, but only one is given.
    // The calculator should throw an exception.
    private static void testMissingOperands() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("missing operands are rejected",
                () -> calculator.evaluatePostfixExpression("5 +"));
    }

    // This test checks an expression with extra operands.
    // Expression: 2 3 4 +
    // This leaves an extra number unused on the stack.
    // A correct postfix expression should leave only one final result.
    private static void testExtraOperands() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("extra operands are rejected",
                () -> calculator.evaluatePostfixExpression("2 3 4 +"));
    }
}
