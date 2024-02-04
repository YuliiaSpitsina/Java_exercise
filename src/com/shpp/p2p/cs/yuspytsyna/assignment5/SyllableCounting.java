package com.shpp.p2p.cs.yuspytsyna.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Counting the number of syllables in a word
 */
public class SyllableCounting extends TextProgram {

    /**
     * Vowel sounds to check
     */
    public static final String VOWELS = "AEIOUY";

    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ").toUpperCase();
            println("  Syllable count: " + syllablesInWord(word));
        }

    }


    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        int countSyllables = 0;

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);

            /* Check if the current character is a vowel */
            if (VOWELS.contains(String.valueOf(currentChar))) {

                /*
                 * Special cases where 'E' at the
                 * end or consecutive vowels are not counted as syllables
                 */
                if ((i == word.length() - 1 && currentChar == 'E') ||
                    (i > 0 && VOWELS.contains(String.valueOf(word.charAt(i - 1))))) {
                    /* Do nothing, don't increment syllable count */
                } else {
                    countSyllables++;
                }
            }
        }

            /*
             *Return the higher value between
             * the calculated syllables and 1
             */
            return Math.max(countSyllables, 1);
    }
}
