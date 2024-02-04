package com.shpp.p2p.cs.yuspytsyna.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Scanner;

/**
 * Assignment3Part1.java - Ask how many minutes in day  do you do exercise
 * Display whether the norm was fulfilled
 * For cardiovascular health 5 days at least 30 minutes
 * For blood pressure 3 days at least 40 minutes
 */
public class Assignment3Part1 extends TextProgram {
    /*The constant is responsible for how many days the check takes place */
    public static final int AMOUNT_OF_DAYS = 7;

    /*
     * The constants are responsible for what is the minimum number of days
     * that are successful for cardio and blood
     * */
    public static final int MIN_SUCCESSFUL_CARDIOVASCULAR_DAYS = 5;
    public static final int MIN_SUCCESSFUL_BLOOD_DAYS = 3;

    /*
     * The constants are responsible for what is the minimum number of minutes
     * that are successful for cardio and blood
     * */
    public static final int MIN_SUCCESSFUL_CARDIOVASCULAR_MINUTES = 30;
    public static final int MIN_SUCCESSFUL_BLOOD_MINUTES = 40;

    /* Variables for counting successful days */
    int checkCardiovascular = 0;
    int checkBlood = 0;

    /**
     * Asks the user to enter the number of minutes spent exercising each day.
     * And displays the result of whether there were enough days
     * for cardio health (5 days for 30 minutes)
     * and blood pressure (3 days for 40 minutes)
     */
    public void run() {
        inputMinutesInDayAndCount();
        cardiovascularResult(checkCardiovascular);
        bloodResult(checkBlood);
    }

    /**
     * Input minutes and count successful cardiovascular and blood days
     */
    private void inputMinutesInDayAndCount() {
        for (int i = 0; i < AMOUNT_OF_DAYS; i++) { // Input for each day
            int timeExercise = inputMinuteForExercise(i + 1);

            countSuccessfulCardiovascularDays(timeExercise);
            countSuccessfulBloodDays(timeExercise);

        }
    }

    /**
     * Count amount days when minutes for exercise at least
     * 40 minutes for successful blood pleasure
     */
    private void countSuccessfulBloodDays(int timeExercise) {
        if (timeExercise >= MIN_SUCCESSFUL_BLOOD_MINUTES) {
            checkBlood++;
        }
    }

    /**
     * Count amount days when minutes for exercise at least
     * 30 minutes for successful cardiovascular health
     */
    private void countSuccessfulCardiovascularDays(int timeExercise) {
        if (timeExercise >= MIN_SUCCESSFUL_CARDIOVASCULAR_MINUTES) {
            checkCardiovascular++;
        }
    }

    /**
     * Input and check for correct input.
     * Entering positive integers up to 1440
     */
    private int inputMinuteForExercise(int i) {
        Scanner scanner = new Scanner(System.in);
        int minute;
        while (true) {
            /*Please enter workout minutes on a specific day*/
            System.out.print("How many minutes did you do on day " + i + "? : ");

            if (scanner.hasNextInt()) { // Check for correct input
                minute = scanner.nextInt();
                if (minute >= 0 && minute <= 1440) { // In day can be max 1440 minutes
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a non-negative integer not exceeding 1440.");
                }
            } else {
                /*If the input is incorrect, then the input can be entered again*/
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
        return minute;
    }

    /**
     * Output of results. If the norm for blood pressure is met,
     * then congratulations are displayed,
     * if not, how many days are not enough before success
     */
    private void bloodResult(int checkBlood) {
        System.out.println("Blood pressure:");
        if (checkBlood >= MIN_SUCCESSFUL_BLOOD_DAYS) {
            System.out.println("  Great job! You've done enough exercise to keep a low blood pressure.");
        } else {
            System.out.println("  You needed to train hard for at least "
                    + (MIN_SUCCESSFUL_BLOOD_DAYS - checkBlood)
                    + " more day(s) a week!");
        }
    }

    /**
     * Output of results. If the norm for cardiovascular health is met,
     * then congratulations are displayed,
     * if not, how many days are not enough before success
     */
    private void cardiovascularResult(int checkCardiovascular) {
        System.out.println("\nCardiovascular health:");
        if (checkCardiovascular >= MIN_SUCCESSFUL_CARDIOVASCULAR_DAYS) {
            System.out.println("  Great job! You've done enough exercise for cardiovascular.");
        } else {
            System.out.println("  You needed to train hard for at least "
                    + (MIN_SUCCESSFUL_CARDIOVASCULAR_DAYS - checkCardiovascular)
                    + " more day(s) a week!");
        }
    }
}

