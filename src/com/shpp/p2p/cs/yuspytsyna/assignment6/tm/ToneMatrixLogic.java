package com.shpp.p2p.cs.yuspytsyna.assignment6.tm;

import java.util.Arrays;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        boolean hasNotes = false;

        // Accumulate samples for active notes
        for (int row = 0; row < toneMatrix.length; row++) {
            if (toneMatrix[row][column]) {
                hasNotes = true;

                // Accumulate the sound sample values for the active notes
                for (int i = 0; i < result.length; i++) {
                    result[i] += samples[row][i];
                }
            }
        }

        // If no active notes, fill with zeros
        if (!hasNotes) {
            Arrays.fill(result, 0);
        }

        // Normalize to the range [-1.0, 1.0]
        normalizeSamples(result);

        return result;
    }

    /**
     * Normalizes an array of samples to the range [-1.0, 1.0]
     *
     * @param result The array of samples to be normalized
     */
    private static void normalizeSamples(double[] result) {

        // Finds the maximum intensity in an array
        double maxIntensity = findMaxIntensity(result);

        // If the maximum intensity is not zero
        if (maxIntensity != 0.0) {
            for (int i = 0; i < result.length; i++) {
                result[i] /= maxIntensity;
            }
        }
    }

    /**
     * Finds the maximum intensity in an array of samples
     *
     * @param result The array of samples
     * @return The maximum intensity.
     */
    private static double findMaxIntensity(double[] result) {
        double maxIntensity = 0.0;
        for (double element : result) {
            if (Math.abs(element) > Math.abs(maxIntensity)) {
                maxIntensity = element;
            }
        }

        return maxIntensity;
    }
}
