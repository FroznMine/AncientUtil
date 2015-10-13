package com.ancient.util.spell.operations;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

import com.ancient.util.spell.item.SpellItem;

public class Mathematic {
	public static final String OPERATORS = "+-*/^";
	public static final String DECIMAL_SEPERATOR = ".";
	public static final String BRACES = "()";
		
	public static SpellItem operation(String expression) {
		return new Operation(expression.replace(";", "").trim());
	}
	
	/** Converts the given mathematic operation in infix format<br>
	 * Result is a queue ordered like RPN.
	 * 
	 * @param input the operation to work with
	 * @return A LinkedList, type String, containing the RPN representation of the input
	 */
	public static Queue<String> convertToRPN(String input) {
		Queue<String> resultQueue = new LinkedList<String>();
		Stack<String> operationsStack = new Stack<String>();
		
		input = input.replaceAll("\\s", "").replaceAll("\\(-", "(0-").replace(".-", ".0-").replace("(+", "(0+").replace(".+", ".0+");
		
		if (input.charAt(0) == '-' || input.charAt(0) == '+') input = "0" + input;

		System.out.println(input);
		
		StringTokenizer tokenizer = new StringTokenizer(input, Mathematic.OPERATORS + Mathematic.BRACES, true);

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			
			if (Mathematic.isNumber(token)) resultQueue.add(token);
			else if (Mathematic.isVariableName(token)) resultQueue.add(token);
			else if (Mathematic.isOperator(token)) {
				while (!operationsStack.isEmpty()) {
					if (!Mathematic.isOperator(operationsStack.peek())) break;
					
					if (Mathematic.isLeftAssociative(token))
						if (Mathematic.getPrecedence(token) <= Mathematic.getPrecedence(operationsStack.peek())) resultQueue.add(operationsStack.pop());
						else break;
					else
						if (Mathematic.getPrecedence(token) < Mathematic.getPrecedence(operationsStack.peek())) resultQueue.add(operationsStack.pop());
						else break;
				}
				operationsStack.push(token);
			}
			else if (Mathematic.isLeftParenthesis(token)) operationsStack.push(token);
			else if (Mathematic.isRightParenthesis(token)) {
				while (!operationsStack.isEmpty()) {
					if (Mathematic.isLeftParenthesis(operationsStack.peek())) {
						operationsStack.pop();
						break;
					}
					resultQueue.add(operationsStack.pop());
				}
			}
		}
		while (!operationsStack.isEmpty()) {
			if (Mathematic.isLeftParenthesis(operationsStack.peek())) {
				operationsStack.pop();
				break; // exception
			}
			resultQueue.add(operationsStack.pop());
		}
		return resultQueue;
	}
	
	//UNFINISHED
	/** Calculate the final result of the RPN operation.<br>
	 * Replaces variable names passed with their current values.<br>
	 * <br>
	 * If there occurs a String as value and another operation than + to the String it will fail and throw an exception.<br>
	 * Operator '+' used on Strings combines them to a new String.
	 * 
	 * @param queue the like RPN formatted queue
	 * @return A String containing the final result. If an error occured it wil be null.
	 */
	public static String calculateFromRPN(Queue<String> queue) {
		Stack<String> buffer = new Stack<String>();
		String[] complex = new String[3];
		for (int i = 0; i < complex.length; i++)
			complex[i] = "";
		
		while (!queue.isEmpty()) {
			String next = queue.poll();
			System.out.println(next);
			if (isOperator(next)) {
				double x, y, result;
				
				try {
					if (complex[1].equals("")) {
						complex[1] = complex[0];
						complex[0] = buffer.pop();
					}
					x = Double.parseDouble(complex[0]);
					y = Double.parseDouble(complex[1]);
					result = 0;
				} catch (Exception ex) {
					// variable dabei
					throw new IllegalArgumentException();
				}
				switch (next.charAt(0)) {
				case '+':
					result = x + y;
					break;
				case '-':
					result = x - y;
					break;
				case '*':
					result = x * y;
					break;
				case '/':
					result = x / y;
					break;
				case '^':
					result = Math.pow(x, y);
					break;
				}
				
				for (int i = 0; i < complex.length; i++)
					complex[i] = "";
				
				complex[0] = result + "";
			}
			else {
				if (complex[0].equals("")) complex[0] = next;
				else if (complex[1].equals("")) complex[1] = next;
				else {
					buffer.push(complex[0]);
					complex[0] = complex[1];
					complex[1] = next;
				}
			}
		}
		return complex[0];
	}
	
	/** Checks if the given expression is a double number.<br>
	 * The method tries to parse the String to Double and catches a possible exception.
	 * 
	 * @param expression The String to check
	 * @return true if the expression is a number, false otherwise
	 */
	public static boolean isNumber(String expression) {
		try {
			Double.parseDouble(expression);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/** Checks if the given expression equals ")"
	 * 
	 * @param expressison the String to check
	 * @return true if expression is ")", false otherwise
	 */
	public static boolean isRightParenthesis(String expresison) {
		if (expresison.equals(")")) return true;
		return false;
	}

	/** Checks if the given expression equals "("
	 * 
	 * @param expressison the String to check
	 * @return true if expression is "(", false otherwise
	 */
	public static boolean isLeftParenthesis(String expression) {
		if (expression.equals("(")) return true;
		return false;
	}
	
	/** Returns the precedence of the given operator.<br>
	 * Precedence from high to low:
	 * <ul>
	 *   <li>"^" -> 2</li>
	 *   <li>"*" or "/" -> 1</li>
	 *   <li>"+" or "-" -> 0</li>
	 * </ul>
	 * The value behind "->" is what is returned by this method.<br>
	 * If it is not in the list it returns -1.
	 * 
	 * @param expressison the String to check
	 * @return 2 for "^", 1 for "*" or "/", 0 for "+" or "-" and -1 for everything else
	 */
	public static int getPrecedence(String expression) {
		if (expression.equals("^")) return 2;
		if (expression.equals("*") || expression.equals("/")) return 1;
		if (expression.equals("+") || expression.equals("-")) return 0;
		return -1;
	}
	
	/** Checks if the given operator is left or right associative.<br>
	 * Since "^" is the only right associative operator it checks if the given String equals it.
	 * 
	 * @param expressison the String to check
	 * @return true if expression is left associative, false when right associative
	 */
	public static boolean isLeftAssociative(String expression) {
		if (expression.equals("^")) return false;
		return true;
	}

	/** Checks if the given String is an operator.<br>
	 * Operators are:
	 * <ul>
	 *   <li>^</li>
	 *   <li>*</li>
	 *   <li>/</li>
	 *   <li>+</li>
	 *   <li>-</li>
	 * </ul>
	 * 
	 * @param expressison the String to check
	 * @return true if the expression is an operator, false otherwise
	 */
	public static boolean isOperator(String token) {
		return OPERATORS.contains(token);
	}

	public static boolean isVariableName(String token) {
		for (char c : token.toCharArray()) {
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') continue;
			return false;
		}
		return true;
	}
}
