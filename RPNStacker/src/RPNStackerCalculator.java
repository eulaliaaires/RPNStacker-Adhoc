
import java.util.Scanner;
import java.util.Stack;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RPNStacker {

    private static Stack<String> stack = new Stack<>();
    private static Boolean debugging = true;

    public static String parseOperation(String operation, Stack<String> stack, Map<String,String> hashTable) throws Exception {
        String result = (stack.empty()) ? "0" : stack.pop();

        if (!stack.empty()) {
            result = Integer.toString(calculate(operation, stack.pop(), result, hashTable)) ;
        }

        return result;
    }

    public static int calculate(String operation, String leftOperator, String rightOperator, Map<String,String> hashTable) throws Exception {
    	 
    	
    	TokenType leftOperatorTokenType = TokenType.EOF;
    	TokenType rightOperatorTokenType = TokenType.EOF;
    	
    	int leftOperatorValue = 0;
    	int rightOperatorValue = 0;
    	
    	if(isId(leftOperator)) {
    		leftOperatorTokenType = TokenType.ID;
    		leftOperatorValue = mapHash(hashTable, leftOperator);
    	}
    	else {
    		leftOperatorTokenType = TokenType.NUM;
    		leftOperatorValue = Integer.parseInt(leftOperator);
    	}
    	
    	if(isId(rightOperator)) {
    		rightOperatorTokenType = TokenType.ID;
    		rightOperatorValue = mapHash(hashTable, rightOperator);
    	}
    	else {
    		rightOperatorTokenType = TokenType.NUM;
    		rightOperatorValue = Integer.parseInt(rightOperator);
    	}
    	
    	if(debugging) {
    		System.out.println(new Token(leftOperatorTokenType, leftOperator));
    		System.out.println(new Token(rightOperatorTokenType, rightOperator));
    	}
    	
    	TokenType tokenType = TokenType.EOF;
    	int value = 0;
    	
    	switch (operation) {
        case "+":
        	tokenType = TokenType.PLUS;
            value = leftOperatorValue + rightOperatorValue;
            break;
        case "-":
        	tokenType = TokenType.MINUS;
        	value = leftOperatorValue - rightOperatorValue;
            break;
        case "*":
        	tokenType = TokenType.STAR;
        	value = leftOperatorValue * rightOperatorValue;
            break;
        case "/": {
        	tokenType = TokenType.SLASH;
        	value = leftOperatorValue / rightOperatorValue;
        	if (rightOperatorValue == 0) {
        		throw new Exception("Cannot divide by 0");
        	}
            break;
        }
        default:
            value = leftOperatorValue;
        }
    	
    	if(debugging) {
    		System.out.println(new Token(tokenType, operation));
    	}
    	
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
    
    public static boolean isId(String input) {
        if (input == null)
            return false;

        return Character.isLetter(input.charAt(0));
    }
    
    public static int mapHash (Map<String,String> hashTable, String key) throws Exception {
    	 if (hashTable.containsKey(key)) {
    		 return Integer.parseInt(hashTable.get(key)) ;
    	 }
    	 else {
    		 throw new Exception(key + " cannot be resolved");
    	 }
    }

    public static void main(String[] args) throws Exception {
    	
    	Map<String,String> hashTable = new HashMap<String,String>();
    	hashTable.put("y", new String("10"));

        String filePath = "C:/Users/eulal/Documents/Calc1.stk";
        
        try {
	        File file = new File(filePath);
	        Scanner reader = new Scanner(file);
	
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	
	            if (isInteger(line)) {
	                stack.push(line);
	            } else if (isOperation(line)) {
	                String current = parseOperation(line, stack, hashTable);          
	                if (stack.size() == 0) {
	                    System.out.println();
	                }
	
	                stack.push(current);
	            } 
	            else if (isId(line)) {
	            	stack.push(line);
	            }
	            
	            else {
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