package com.shpp.p2p.cs.yuspytsyna.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Random;

/**
 * Assignment3Part5.java - Two people play: the lucky one and the sweaty one.
 * The lucky person leaves the casino when he earns $20 or more.
 * The sweaty one puts $1 on the table, and the lucky one starts flipping a coin.
 * If it is an eagle, then the sweaty person adds exactly
 * the same amount to the amount on the table.
 * If it's a tail, everything on the table goes to the lucky person.
 * If the lucky person ends up with less than $20, then the game is repeated.
 */
public class Assignment3Part5 extends TextProgram {
    /*The constant is responsible for how much money lucky person need to win */
    private static final int MONEY_FOR_WIN = 20;

    /**
     * Start the game
     */
    public void run() {
        game();
    }

    /**
     * While lucky person haven`t enough money games are played
     * After each game display how much was put
     * and how much lucky person has money
     * Count how many games were played for win
     */
    private void game() {
        /*Variable to count how much lucky person has money
         * and how many games were played for win
         * */
        int totalGames = 0;
        int totalSum = 0;

        while (totalSum < MONEY_FOR_WIN) {

            int earned = playSingleGame(); // Flipping a coin while eagle

            totalSum += earned; //Add earned money to all lucky person`s money

            System.out.println("This game, you earned $" + earned);
            System.out.println("Your total is $" + totalSum);

            totalGames++; // Count amount of games
        }
        System.out.println("It took " + totalGames + " games to earn $" + MONEY_FOR_WIN);
    }

    /**
     * Flipping a coin while eagle
     * Using random 0 - eagle, 1 - tail
     * If 0 - the bet is doubled
     */
    private int playSingleGame() {
        Random random = new Random();
        int earned = 1; // Starting bet

        while (random.nextInt(2) == 0) {
            earned *= 2;
        }
        return earned;
    }
}


