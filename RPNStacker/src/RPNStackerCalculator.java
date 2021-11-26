
import java.util.Scanner;
import java.util.Stack;

import java.io.File;
import java.io.FileNotFoundException;

public class RPNStacker {

    private static Stack<Integer> stack = new Stack<>();

    public static int parseOperation(String operation, Stack<Integer> stack) throws Exception {
        int result = (stack.empty()) ? 0 : stack.pop();

        if (!stack.empty()) {
            result = calculate(operation, stack.pop(), result);
        }

        return result;
    }

    public static int calculate(String operation, int leftOperator, int rightOperator) throws Exception {
    	 
    	System.out.println(new Token(TokenType.NUM, Integer.toString(leftOperator)));
    	System.out.println(new Token(TokenType.NUM, Integer.toString(rightOperator)));
    	
    	TokenType tokenType = TokenType.EOF;
    	int value = 0;
    	
    	switch (operation) {
        case "+":
        	tokenType = TokenType.PLUS;
            value = leftOperator + rightOperator;
            break;
        case "-":
        	tokenType = TokenType.MINUS;
        	value = leftOperator - rightOperator;
            break;
        case "*":
        	tokenType = TokenType.STAR;
        	value = leftOperator * rightOperator;
            break;
        case "/": {
        	tokenType = TokenType.SLASH;
        	value = leftOperator / rightOperator;
        	if (rightOperator == 0) {
        		throw new Exception("Cannot divide by 0");
        	}
            break;
        }
        default:
            value = leftOperator;
        }
    	
    	 System.out.print(new Token(tokenType, operation));
    	
    	return value;
    }

    // check if current input is an integer

    public static boolean isInteger(String input) {
        if (input == null)
            return false;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    // check if current input is an operator

    public static boolean isOperation(String input) {
        if (input == null)
            return false;

        return input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
    }

    public static void main(String[] args) throws Exception {

        String filePath = "C:/Users/eulal/Documents/Calc1.stk";
        
        try {
	        File file = new File(filePath);
	        Scanner reader = new Scanner(file);
	
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	
	            if (isInteger(line)) {
	                stack.push(Integer.parseInt(line));
	            } else if (isOperation(line)) {
	                int current = parseOperation(line, stack);          
	                if (stack.size() == 0) {
	                    System.out.println();
	                }
	
	                stack.push(current);
	            } else {
	                System.out.println("Error: Unexpected character: " + line);
	                throw new Exception("Character not allowed");
	            }
	
	        }
	        System.out.println("Saída = " + stack.pop());
	
	        reader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("Error when read the file...");
	        e.printStackTrace();
	    }
    }

}