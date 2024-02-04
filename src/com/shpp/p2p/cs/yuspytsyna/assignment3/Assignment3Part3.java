package com.shpp.p2p.cs.yuspytsyna.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Assignment3Part3.java - Exponentiation without using Math
 */
public class Assignment3Part3 extends TextProgram {
    /*
     * The constants are responsible for number
     * that raised to power and exponent
     * Variable input size 8 bytes
     * */
    public static final double BASE = 0.5;
    public static final int EXPONENT = -2;

    /**
     * Exponentiation without using Math
     * Display result of expression
     */
    public void run() {
        double result = raiseToPower(BASE, EXPONENT);
        System.out.println("Expression: " + BASE + "^" + EXPONENT + " = " + result);
    }

    /**
     * Method will take two parameters and calculate the value
     * of the first parameter raised to the power of parameter 2
     * If exponent is equal 0 - the result will be 1
     * If exponent is negative - it changed to opposite and count,
     * then divide one by result
     */
    private double raiseToPower(double base, int exponent) {
        /*If exponent is equal 0 - the result will be 1*/
        if (exponent == 0) {
            return 1;
        }

        /*If exponent is negative - it changed to opposite*/
        boolean isNegativeExponent = false;
        if (exponent < 0) {
            isNegativeExponent = true;
            exponent = -exponent;
        }

        double result = multiplyBase(base, exponent);

        /*
         * If exponent is negative -
         * change result by dividing 1 by result
         * */
        if (isNegativeExponent) {
            result = 1 / result;
        }
        return result;
    }


    /**
     * Multiply number
     */
    private double multiplyBase(double base, int exponent) {
        double result = 1;
        for (int i = 1; i <= exponent; i++) {
            result *= base;
        }
        return result;
    }


}
