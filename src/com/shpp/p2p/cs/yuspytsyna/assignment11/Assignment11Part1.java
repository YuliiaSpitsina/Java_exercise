package com.shpp.p2p.cs.yuspytsyna.assignment11;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Calculated expression.
 * Stand instead of letters and numbers
 * Support for the following operators: -, +, /, *, ^ (exponentiation).
 */
public class Assignment11Part1 {

    // Define constants for arithmetic operations
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char RAISE = '^';

    // Current position in the expression being processed
    private static int position;

    // Map to store variable names and their values
    static Map<String, Double> variableValues = new HashMap<>();

    //  Map to store functions
    private static final Map<String, Function<Double, Double>> FUNCTION_MAP = new HashMap<>();

    static {
        FUNCTION_MAP.put("sin", Math::sin);
        FUNCTION_MAP.put("cos", Math::cos);
        FUNCTION_MAP.put("tan", Math::tan);
        FUNCTION_MAP.put("atan", Math::atan);
        FUNCTION_MAP.put("log10", Math::log10);
        FUNCTION_MAP.put("log2", x -> Math.log(x) / Math.log(2.0));
        FUNCTION_MAP.put("sqrt", Math::sqrt);
    }

    /**
     * Method to calculate the expression
     *
     * @param args The input expression to be calculated
     * @return The processed expression
     */
    public static String calculate(String[] args) {
        // Check if args has at least one element and it's not empty
        if (args.length < 1 || args[0].isEmpty()) {
            return null; // or any appropriate action for error handling
        }

        String expression = args[0];
        // Initialize the position and remove spaces
        com.shpp.p2p.cs.yuspytsyna.assignment11.Assignment11Part1.position = 0;
        expression = expression.replaceAll("\\s", "");
        return expression;
    }

    /**
     * Method to evaluate the entire expression.
     *
     * @param expression The expression to be evaluated.
     * @return The result of the evaluation.
     */
    private static double evaluateExpression(String expression) {
        String spacedExpression = expression.replaceAll("([-+*/^()])", " $1 ");
        String[] words = spacedExpression.split("\\s+"); // Разбить строку на слова по пробелу

        StringBuilder resultString = new StringBuilder();
        for (String word : words) {
            // Check if the word is a function name
            if (FUNCTION_MAP.containsKey(word)) {
                resultString.append(word); // Add the function name with a space
            } else {
                // Replace variables with their values
                for (Map.Entry<String, Double> entry : variableValues.entrySet()) {
                    word = word.replace(entry.getKey(), entry.getValue().toString());
                }
                resultString.append(word); // Add the word with a space
            }
        }

        System.out.println(resultString);
        // Evaluate the expression using the correct math functions
        double result = evaluateFunction(String.valueOf(resultString));

        return result;
    }


    private static double evaluateFunction(String expression) {
        double result = evaluateFactor(expression);

        // Iterate while there are more addition or subtraction operators
        while (position < expression.length()) {
            char operator = expression.charAt(position);

            // Break if the operator is neither addition (+) nor subtraction (-)
            if (operator != ADDITION && operator != SUBTRACTION) {
                break;
            }
            position++;  // Move to the next character (skip the operator)

            double nextFactor = evaluateFactor(expression);

            // Perform addition or subtraction based on the operator
            if (operator == ADDITION) {
                result += nextFactor;
            } else if (operator == SUBTRACTION) {
                result -= nextFactor;
            }
        }
        return result;

    }

    /**
     * Method to evaluate a factor (multiplication and division).
     *
     * @param expression The expression to be evaluated.
     * @return The result of the evaluation.
     */
    private static double evaluateFactor(String expression) {
        double result = evaluateExponentiation(expression);

        // Iterate while there are more multiplication or division operators
        while (position < expression.length()) {
            char operator = expression.charAt(position);

            // Break if the operator is neither multiplication (*) nor division (/)
            if (operator != MULTIPLICATION && operator != DIVISION) {
                break;
            }
            position++; // Move to the next character (skip the operator)

            // Evaluate the next exponentiation (rightmost factor)
            double nextExponentiation = evaluateExponentiation(expression);

            // Perform multiplication or division based on the operator
            try {
                // Perform multiplication or division based on the operator
                if (operator == MULTIPLICATION) {
                    result *= nextExponentiation;
                } else if (operator == DIVISION) {
                    if (nextExponentiation == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result /= nextExponentiation;
                }
            } catch (ArithmeticException e) {
                System.err.println("Error: " + e.getMessage());
                return Double.NaN;  // Return NaN (Not a Number) to indicate an error
            }
        }

        return result;
    }

    /**
     * Method to evaluate exponentiation.
     *
     * @param expression The expression to be evaluated.
     * @return The result of the evaluation for the exponentiation.
     */
    private static double evaluateExponentiation(String expression) {
        // Evaluate the term (base)
        double result = evaluateTerm(expression);
        // Flag to track if the result is negative
        boolean isNegative = false;

        // Iterate while there are more exponentiation operators (^)
        while (position < expression.length() && expression.charAt(position) == RAISE) {
            position++;

            // Check if the result of the base is negative
            if (result < 0) {
                isNegative = true;
            }
            result = Math.pow(result, evaluateTerm(expression));
        }

        // Return the result with the appropriate sign (negative if the base was negative)
        return isNegative ? -result : result;
    }

    /**
     * Method to evaluate a term (number or variable).
     *
     * @param expression The expression to be evaluated.
     * @return The result of the evaluation.
     */
    private static double evaluateTerm(String expression) {
        try {
            // Check if the position is beyond the expression length
            if (position >= expression.length()) {
                throw new IllegalArgumentException("Unexpected end of expression");
            }

            // Get the current character at the current position
            char currentChar = expression.charAt(position);

            // Handle negative numbers
            //int sign = handleSign(expression, currentChar);
            int sign = 1;
            if (currentChar == '-') {
                sign = -1;
                position++;
                if (position < expression.length()) {
                    currentChar = expression.charAt(position);
                } else {
                    throw new IllegalArgumentException("Unexpected end of expression");
                }
            }

            double result;

            if (currentChar == '(') {
                position++;
                result = evaluateFunction(expression);
                if (position >= expression.length() || expression.charAt(position) != ')') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                position++;
            } else if (Character.isDigit(currentChar) || currentChar == '.') {
                result = handleNumber(expression);
            } else if (Character.isLetter(currentChar)) {
                result = handleFunction(expression);
            } else {
                throw new IllegalArgumentException("Unexpected character: " + currentChar);
            }

            // Apply sign
            return sign * result;
        } catch (NumberFormatException e) {
            System.err.println("Error: Unable to parse a number");
            return Double.NaN;
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }

    /**
     * Handles a function without arguments by extracting the function name
     * and calling handleFunctionCall to evaluate it.
     *
     * @param expression The expression containing the function call.
     * @return The result of the evaluated function.
     * @throws IllegalArgumentException If the function is unknown.
     */
    private static double handleFunction(String expression) {
        StringBuilder function = new StringBuilder();

        while (position < expression.length() && expression.charAt(position) != '(') {
            function.append(expression.charAt(position));
            position++;
        }

        String functionName = function.toString();
        if (FUNCTION_MAP.containsKey(functionName)) {
            return handleFunctionCall(expression, functionName);
        } else {
            throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

    /**
     * Handles a function call with arguments
     * by evaluating the function with its argument.
     *
     * @param expression   The expression containing the function call and its argument.
     * @param functionName The name of the function to be evaluated.
     * @return The result of the evaluated function.
     * @throws IllegalArgumentException If there is a mismatched closing parenthesis or the function is unknown.
     */
    private static double handleFunctionCall(String expression, String functionName) {
        position++;  // Move past the opening parenthesis
        double argument = evaluateFunction(expression);
        checkClosingParenthesis(expression);

        // Evaluate the function with the argument
        return FUNCTION_MAP.get(functionName).apply(argument);
    }

    /**
     * Checks for a matching closing parenthesis.
     *
     * @param expression The expression to check for a closing parenthesis.
     * @throws IllegalArgumentException If there is a mismatched closing parenthesis.
     */
    private static void checkClosingParenthesis(String expression) {
        if (position >= expression.length() || expression.charAt(position) != ')') {
            throw new IllegalArgumentException("Mismatched parentheses");
        }
        position++;  // Move past the closing parenthesis
    }

    /**
     * Handles parsing numeric values in the expression.
     *
     * @param expression The expression containing the numeric value.
     * @return The parsed numeric value.
     */
    private static double handleNumber(String expression) {
        StringBuilder number = new StringBuilder();

        // Continue building the number while it's a digit or a decimal point
        while (position < expression.length() &&
                (Character.isDigit(expression.charAt(position)) || expression.charAt(position) == '.')) {
            number.append(expression.charAt(position));
            position++;
        }

        // Parse the number and store it in the result
        return Double.parseDouble(number.toString());
    }


    /**
     * Method to create variables and their values from the input arguments.
     *
     * @param args The input arguments containing variable assignments.
     * @return A map of variables and their assigned values.
     */
    private static Map<String, Double> createVariables(String[] args) {
        Map<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {

            // Split the argument based on the '=' sign to separate variable and value
            String[] assignment = args[i].split("=");

            // Check if the assignment contains both variable and value
            if (assignment.length == 2) {
                String variable = assignment[0].trim();
                double value = Double.parseDouble(assignment[1].trim());
                // Put the variable and its value in the map
                variables.put(variable, value);
            }
        }
        return variables;
    }

    public static void main(String[] args) {
        args = new String[]{"5-a", "a =   -2", "b=-4"};
        String evaluateString = null;
        try {
            evaluateString = calculate(args);
        } catch (Exception e) {
            System.err.println("Error during expression calculation: " + e.getMessage());
            return;  // Exit early, since we cannot proceed without a valid expression
        }

        // Check if evaluateString is null or empty
        if (evaluateString == null || evaluateString.isEmpty()) {
            System.err.println("No expression provided.");
            return;  // Exit early, since we cannot proceed without a valid expression
        }

        // Create a map of variables and their values from the input arguments
        variableValues = createVariables(args);

        // Evaluate the entire expression and get the result
        double result = evaluateExpression(evaluateString);

        // Display the result or a message indicating no result
        if (!Double.isNaN(result)) {
            System.out.println("Result: " + result);
        } else {
            System.out.println("No result");
        }

    }
}