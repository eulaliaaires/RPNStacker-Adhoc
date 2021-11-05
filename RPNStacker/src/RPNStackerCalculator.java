
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;

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

    // check if current input is a integer
    
    public static boolean isInteger(String input) {
        if (input == null) return false;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    // check if current input is a operator

    public static boolean isOperation(String input) {
        if (input == null) return false;

        return input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
    }
    
   
	private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Digite a operação a ser feita: ");

        String input = "";
        while (scanner.hasNext()) {
            input = scanner.next();

            if (isInteger(input)) {
                stack.push(Integer.parseInt(input));
            } else if (isOperation(input)) {
                int current = parseOperation(input, stack);
                if (stack.size() == 0) {
                    System.out.println("= " + current);
                } 
               
                stack.push(current);
            }
        }
    }


    
}