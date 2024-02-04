package com.shpp.p2p.cs.yuspytsyna.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Scanner;

/*
 Different from Assignment3Part2.java - when user input 1,
 in this version this finished number.
 Program doesn't use algorithm
 */

/**
 * Assignment3Part2Ext.java - Take some positive integer and call it n
 * If n is even, then divide it by 2
 * If n is odd, multiply by 3 and add 1
 * Continue this process until n is equal to 1
 */
public class Assignment3Part2Ext extends TextProgram {

    /**
     * Input number, check for correct enter
     * If number is even, then divide it by 2
     * If number is odd, multiply by 3 and add 1
     * Stop when number will equal 1
     */
    public void run() {
        int n = inputNumber();
        numberCount(n);
        System.out.println("Number is equal 1");
    }

    /**
     * As long as the number is greater than 1
     * If number is even, then divide it by 2
     * If number is odd, multiply by 3 and add 1
     */
    private void numberCount(int number) {
        while (number > 1) {
            if (number % 2 == 0) { // Check for even number
                System.out.print(number + " is even so I take half: ");
                number = number / 2;
                System.out.println(number);
            } else { //Check for odd number
                System.out.print(number + " is odd so I make 3n + 1: ");
                number = number * 3 + 1;
                System.out.println(number);
            }
        }
    }

    /**
     * Ask for input a number
     * Input and check for correct input.
     * Entering positive integers from 1
     */
    private int inputNumber() {
        Scanner scanner = new Scanner(System.in);
        int inputNumber;
        while (true) {

            System.out.print("Enter a number: ");

            if (scanner.hasNextInt()) { // Check for valid input
                inputNumber = scanner.nextInt();
                if (inputNumber >= 1) { // Number must be positive more than 0
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a non-negative integer more than 0.");
                }
            } else {
                /*If the input is incorrect, then the input can be entered again*/
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
        return inputNumber;
    }
}
