package com.shpp.p2p.cs.yuspytsyna.assignment2;

import com.shpp.cs.a.console.TextProgram;

/*
Assignment2Part1.java - Finding the roots of a quadratic equation
 */
public class Assignment2Part1 extends TextProgram {
    /*
    Accepts 3 numbers (a,b,c) as input,
    and outputs the roots of a quadratic equation: a*(x^2) + b*x + c = 0
     */
    public void run() {
        /*
        3 coefficient (coefficientA,coefficientB,coefficientC)
        Variable input size 8 bytes
        Variable a(coefficientA) cannot be 0
         */
        double coefficientA = 0, coefficientB, coefficientC;

        /* Enter variable a while user enters zero */
        while (coefficientA == 0) {
            coefficientA = readDouble("Please enter a: "); // Input coefficient a
            if (coefficientA == 0) {
                System.out.println("Invalid input. 'a' cannot be zero. Please try again.");
            }
        }
        /* Input coefficients b and c */
        coefficientB = readDouble("Please enter b: ");
        coefficientC = readDouble("Please enter c: ");

        /*
        Finding the discriminant to find out how many roots are in the program
         */
        double discriminant = findDiscriminant(coefficientA, coefficientB, coefficientC);

        if (discriminant > 0) {
            println("There are two roots: " + firstRoot(coefficientB, discriminant, coefficientA) + " and " + secondRoot(coefficientB, discriminant, coefficientA)); // The two roots in the equation
        } else if (discriminant == 0) {
            println("There is one root: " + firstRoot(coefficientB, discriminant, coefficientA)); // The one root in the equation
        } else {
            println("There are no real roots"); // No roots in the equation
        }

    }

    /*
    Finding the discriminant to find out how many roots are in the program
     */
    private double findDiscriminant(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }

    /*
    Finding the first or single root in the program
     */
    private double firstRoot(double b, double d, double a) {
        return (-b + Math.sqrt(d)) / (2 * a);
    }

    /*
    Finding the second root if it is present in the equation
     */
    private double secondRoot(double b, double d, double a) {
        return (-b - Math.sqrt(d)) / (2 * a);
    }
}

