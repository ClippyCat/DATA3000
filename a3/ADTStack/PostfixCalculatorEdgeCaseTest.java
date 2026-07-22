package ADTStack;

public class PostfixCalculatorEdgeCaseTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testValidNumberExpression();
        testValidVariableExpression();
        testEmptyExpression();
        testUnknownVariable();
        testDivisionByZero();
        testMalformedToken();
        testMissingOperands();
        testExtraOperands();

        System.out.println();
        System.out.println("SUMMARY: " + passed + " passed, " + failed + " failed.");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(String description, boolean condition) {

        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }

    private static void expectException(String description, Runnable action) {

        try {
            action.run();
            failed++;
            System.out.println("FAIL: " + description + " (no exception)");
        } catch (Exception e) {
            passed++;
            System.out.println("PASS: " + description + " -> " 
                    + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void testValidNumberExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        int result = calculator.evaluatePostfixExpression("2 3 +");

        check("normal expression 2 3 + returns 5", result == 5);
    }

    private static void testValidVariableExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        calculator.setVariable("x", 5);
        calculator.setVariable("y", 3);
        calculator.setVariable("z", 4);

        int result = calculator.evaluatePostfixExpression("x y + z *");

        check("variable expression x y + z * returns 32", result == 32);
    }

    private static void testEmptyExpression() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("empty expression is rejected",
                () -> calculator.evaluatePostfixExpression(""));
    }

    private static void testUnknownVariable() {

        PostfixCalculator calculator = new PostfixCalculator();

        calculator.setVariable("x", 5);

        expectException("unknown variable is rejected",
                () -> calculator.evaluatePostfixExpression("x y +"));
    }

    private static void testDivisionByZero() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("division by zero is rejected",
                () -> calculator.evaluatePostfixExpression("5 0 /"));
    }

    private static void testMalformedToken() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("malformed token is rejected",
                () -> calculator.evaluatePostfixExpression("5 2 @"));
    }

    private static void testMissingOperands() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("missing operands are rejected",
                () -> calculator.evaluatePostfixExpression("5 +"));
    }

    private static void testExtraOperands() {

        PostfixCalculator calculator = new PostfixCalculator();

        expectException("extra operands are rejected",
                () -> calculator.evaluatePostfixExpression("2 3 4 +"));
    }
}
