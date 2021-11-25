
import java.util.Scanner;
import java.util.Stack;


public class RPNStackerCalculator {
	
	private static Stack<Integer> stack = new Stack<>();
	
	public static int parseOperation(String operation, Stack<Integer> stack) throws Exception {
        int result = (stack.empty()) ? 0 : stack.pop();

        if (!stack.empty()) {
            result = calculate(operation, stack.pop(), result);
        }

        return result;
    }

   
    public static int calculate(String operation, int leftOperator, int rightOperator) throws Exception {
        switch (operation) {
            case "+": return leftOperator + rightOperator;
            case "-": return leftOperator - rightOperator;
            case "*": return leftOperator * rightOperator;
            case "/": {
                if (rightOperator == 0) {
                    throw new Exception("Cannot divide by 0");
                }
                return leftOperator / rightOperator;
            }
            default: return leftOperator;
        }
    }

    // check if current input is an integer
    
    public static boolean isInteger(String input) {
        if (input == null) return false;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    // check if current input is an operator

    public static boolean isOperation(String input) {
        if (input == null) return false;

        return input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
    }
    
   
	private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Digite a operação a ser feita (para finalizar digite end): ");

        String input = "";
        while (input != "end") {
            input = scanner.next();

            if (isInteger(input)) {
                stack.push(Integer.parseInt(input));
                System.out.println(new Token (TokenType.NUM, input));
            } else if (isOperation(input)) {
                int current = parseOperation(input, stack);
                TokenType tokenType = TokenType.EOF;
                switch (input) {
	                case "+": 
                    tokenType =  TokenType.PLUS;
	                	break;
	                case "-": 
                    tokenType =  TokenType.MINUS;
	                	break;
	                case "*": 
                    tokenType =  TokenType.STAR;
	                	break;
	                case "/": 
                    tokenType =  TokenType.SLASH;
	                	break;
	                default: 
                    tokenType = TokenType.EOF;
	                	break;
                }
                System.out.print(new Token (tokenType, input));
                if (stack.size() == 0) {
                	System.out.println();
                    System.out.println("= " + current);
                } 
               
                stack.push(current);
            }
            else {
            	System.out.print("Error: Unexpected character: " + input);
            }
        }
    }


    
}