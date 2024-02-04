package com.shpp.p2p.cs.yuspytsyna.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Search for words that contain the three letters entered
 */
public class Assignment5Part3 extends TextProgram {

    /**
     * Number of letters to play
     */
    public static final int LETTERS_FOR_GAME = 3;

    /**
     * A file with a set of words
     */
    public static final String FILE_NAME = "assets/en-dictionary.txt";

    /**
     * Reading a file, entering three letters and
     * changing them to lower case, outputting suitable words
     */
    public void run() {
        ArrayList<String> words = readDictionary();

        while (true) {
            String letters = inputLetters();
            findWords(letters, words);
        }
    }

    /**
     * Responsible for obtaining input from the user
     *
     * @return string containing the user's input, converted to lowercase
     */
    private String inputLetters() {
        String letters = "";
        /*
         * Provide a sequence of 3 letters and continues
         * to do so until the desired length is achieved.
         */
        do {
            letters = readLine("\nEnter 3 letters: ").toLowerCase();
        } while (letters.length() != LETTERS_FOR_GAME);
        return letters;
    }

    /**
     * Finds and displays valid words formed from given letters.
     *
     * @param letters the available letters to form words
     * @param words an ArrayList of words to check
     */
    private void findWords(String letters, ArrayList<String> words) {
        /* Flag to track if any valid words are found */
        boolean validWords = false;
      int check = 0;
        for (String word : words) {

            /* Check if the word can be formed from the given letters */
            if (wordCheck(letters, word)) {
                System.out.println(word);
                validWords = true;
                check++;
            }
        }
        System.out.println(check);
        if (!validWords) {
            System.out.println("There are no suitable words");
        }
    }

    /**
     * Checks if a given word can be formed using a set of letters
     *
     * @param letters the available letters to form the word
     * @param word the word to be checked
     * @return true if the word can be formed, false otherwise
     */
    private boolean wordCheck(String letters, String word) {
        int checkIndex = -1;

        for (char letter : letters.toCharArray()) {
            /* Find the next occurrence of the character in the word */
            int index = word.indexOf(letter, checkIndex + 1);

            /* If the character is not found, the word cannot be formed */
            if (index == -1) {
                return false;
            }

            checkIndex = index;
        }

        return true;
    }

    /**
     * Reads and loads a dictionary of words from a file
     *
     * @return An ArrayList containing the words read from the file
     */
    private ArrayList<String> readDictionary() {
        ArrayList<String> dictionary = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

           /* Read words from the file until the end is reached */
            while (true) {

                /* Read a line (word) from the file */
                String word = br.readLine();

                /* Break the loop if the end of the file is reached */
                if (word == null) {
                    break;
                }

                dictionary.add(word.toLowerCase());
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Unable to open or read file");
        }

        return dictionary;
    }
}
