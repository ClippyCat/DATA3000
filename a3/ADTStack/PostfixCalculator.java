package ADTStack;

import java.util.EmptyStackException;

public class PostfixCalculator {

    private BinarySearchTree binarySearchTree;
    private StackInterface<Integer> stack;

    public PostfixCalculator() {
        binarySearchTree = new BinarySearchTree();
        stack = new ArrayStack<>();
    }

    // Assign a value to a variable in the Binary Search Tree.
    public void setVariable(String key, int value) {
        if (!isVariable(key)) {
            throw new IllegalArgumentException("Invalid variable name: " + key);
        }

        binarySearchTree.insert(key, value);
    }

    // Clear all variables from the Binary Search Tree.
    public void deleteAllVariables() {
        binarySearchTree.deleteAll();
    }

    // Return the Binary Search Tree as a String so Main can display it.
    public String displayVariables() {
        return binarySearchTree.displayTree();
    }

    // Evaluates a postfix expression and returns the final integer result.
    public int evaluatePostfixExpression(String expression) {

        stack.clear();

        // Edge case: empty or null expression.
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty.");
        }

        // Split by one or more spaces so extra spaces do not create empty tokens.
        String[] tokens = expression.trim().split("\\s+");

        for (int i = 0; i < tokens.length; i++) {

            String token = tokens[i];

            if (isNumber(token)) {

                int value = Integer.parseInt(token);
                stack.push(value);

            } else if (isOperator(token)) {

                int operand2;
                int operand1;

                // Edge case: operator appears without enough operands.
                try {
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                } catch (EmptyStackException e) {
                    throw new IllegalArgumentException(
                            "Malformed expression: not enough operands for operator " + token);
                }

                int result = calculate(operand1, operand2, token);
                stack.push(result);

            } else if (isVariable(token)) {

                Integer value = binarySearchTree.search(token);

                // Edge case: variable was not stored in the BST.
                if (value == null) {
                    throw new IllegalArgumentException("Variable " + token + " not found.");
                }

                stack.push(value);

            } else {

                // Edge case: token is not a number, operator, or valid variable name.
                throw new IllegalArgumentException("Malformed token found: " + token);
            }
        }

        int finalResult;

        try {
            finalResult = stack.pop();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Malformed expression: no result found.");
        }

        // Edge case: extra numbers or variables were left unused.
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Malformed expression: too many operands.");
        }

        return finalResult;
    }

    // Performs one arithmetic operation.
    private int calculate(int operand1, int operand2, String operator) {

        switch (operator) {

            case "+":
                return operand1 + operand2;

            case "-":
                return operand1 - operand2;

            case "*":
                return operand1 * operand2;

            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return operand1 / operand2;

            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    // Checks if the token is an integer number.
    private boolean isNumber(String token) {

        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Checks if the token is one of the allowed operators.
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    // Checks if the token is a valid variable name.
    private boolean isVariable(String token) {
        return token != null && token.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }
}
