package ADTStack;

public class PostfixCalculator
{

	private BinarySearchTree binarySearchTree;
	private StackInterface<Integer> stack;

	public PostfixCalculator() 
	{

		binarySearchTree = new BinarySearchTree();
		stack = new ArrayStack<>();
	}

	// Assign the value for keys in BinarySearchTree
	public void setVariable(String key, int value)
	{

		binarySearchTree.insert(key, value);
	}

	// Clear the binary search tree nodes.
	public void deleteAllVariables() 
	{

		binarySearchTree.deleteAll();
	}

	// Display the binary tree
	public void displayVariables() 
	{

		binarySearchTree.displayTree();
	}

	// Evaluates a postfix expression.
	public int evaluatePostfixExpression(String expression)
	{

		stack.clear();

		String[] tokens = expression.split(" ");

		for (int i = 0; i < tokens.length; i++) 
		{

			if (isNumber(tokens[i]))
			{

				int value = Integer.parseInt(tokens[i]);

				stack.push(value);
			}

			else if (isOperator(tokens[i])) 
			{

				int operand2 = stack.pop();
				int operand1 = stack.pop();

				int result = 0;

				switch (tokens[i]) {

				case "+":
					result = operand1 + operand2;
					break;

				case "-":
					result = operand1 - operand2;
					break;

				case "*":
					result = operand1 * operand2;
					break;

				case "/":

					if (operand2 == 0)
						throw new ArithmeticException("Division by zero");

					result = operand1 / operand2;
					break;
				}

				stack.push(result);
			}

			else
			{

				Integer value = binarySearchTree.search(tokens[i]);

				if (value == null)
					throw new RuntimeException("Variable " + tokens[i] + " not found.");

				stack.push(value);
			}
		}

		return stack.pop();
	}

	// Method checks the passed argument is number or not
	private boolean isNumber(String token)
	{

		try
      {

			Integer.parseInt(token);

			return true;

		} catch (Exception e) 
		{

			return false;
		}
	}

	// Method checks the passed argument is operator or not
	private boolean isOperator(String token)
	{

		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
	}
}
