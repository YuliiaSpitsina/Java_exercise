package com.shpp.p2p.cs.yuspytsyna.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Given two string representations of non-negative integers, adds the
 * numbers represented by those strings
 */
public class AlgorismAlgorithms extends TextProgram {

    /**
     * Input two string representation of non-negative integers
     * and displays result of the adding them
     */
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of non-negative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        /* Check which string is longer */
        int maxLength = Math.max(n1.length(), n2.length());
        int remainder = 0;
        String result = "";

        for (int i = 0; i < maxLength; i++) {

            /* Extract the digit from strings, or use 0 if string is shorter */
            int number1 = i < n1.length() ? n1.charAt(n1.length() - 1 - i) - '0' : 0;
            int number2 = i < n2.length() ? n2.charAt(n2.length() - 1 - i) - '0' : 0;

            int sum = number1 + number2 + remainder;

            remainder = sum / 10;

            /* Remember only the last digit */
            int lastNumberInSum = sum % 10;

            result = lastNumberInSum + result;
        }

        /* To transfer the remainder to a new digit */
        if (remainder > 0) {
            result = remainder + result;
        }

        return result;
    }
}
