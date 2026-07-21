package ADTStack;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        // Intro dialog
        JOptionPane.showMessageDialog(
                null,
                "This program evaluates postfix arithmetic expressions using a\n"
                        + "stack, and stores variables in a binary search tree.\n\n"
                        + "Press OK to begin.",
                "Postfix Calculator with BST",
                JOptionPane.INFORMATION_MESSAGE);

        PostfixCalculator calculator = new PostfixCalculator();

        runCase(calculator, 1, new String[]{"x", "y", "z"}, new int[]{5, 3, 4}, "x y + z *");
        runCase(calculator, 2, new String[]{"a", "b", "c"}, new int[]{2, 3, 4}, "a b + c *");
        runCase(calculator, 3, new String[]{"m", "n", "p"}, new int[]{8, 2, 3}, "m n / p +");
        runCase(calculator, 4, new String[]{"q", "r", "s"}, new int[]{7, 3, 2}, "q r - s *");
        runCase(calculator, 5, new String[]{"d", "e", "f"}, new int[]{1, 2, 3}, "d e + f -");
        runCase(calculator, 6, new String[]{"g", "h", "i", "j"}, new int[]{2, 3, 4, 5}, "g h i + * j /");
        runCase(calculator, 7, new String[]{"k", "l", "m", "n"}, new int[]{2, 3, 4, 5}, "k l + m n - *");
        runCase(calculator, 8, new String[]{"o", "p", "q", "r", "s"}, new int[]{9, 3, 5, 2, 7}, "o p - q r + * s -");

        JOptionPane.showMessageDialog(null, "All test cases complete.", "Done", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void runCase(PostfixCalculator calculator, int caseNumber,
                                 String[] keys, int[] values, String expression) {

        for (int i = 0; i < keys.length; i++) {
            calculator.setVariable(keys[i], values[i]);
        }

        StringBuilder message = new StringBuilder();
        message.append("Postfix expression ").append(caseNumber).append(": ").append(expression).append("\n\n");
        message.append("Binary Search Tree:\n").append(calculator.displayVariables()).append("\n");

        int result = calculator.evaluatePostfixExpression(expression);
        message.append("Result: ").append(result).append("\n");

        calculator.deleteAllVariables();
        message.append("\nAll variables deleted from the tree.");

        System.out.println(message);
        System.out.println("----------------------------------------");

        JOptionPane.showMessageDialog(null, message.toString(),
                "Test Case " + caseNumber, JOptionPane.PLAIN_MESSAGE);
    }
}
