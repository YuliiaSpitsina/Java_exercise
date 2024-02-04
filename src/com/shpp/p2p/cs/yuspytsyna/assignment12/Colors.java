package com.shpp.p2p.cs.yuspytsyna.assignment12;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utility class for working with colors.
 */
public class Colors {

    /**
     * Finds the most common color in the provided image.
     *
     * @param image The image to analyze.
     * @return The most common color.
     */
    public static Color findMostCommonColor(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Arrays to store histogram for red, green, and blue color channels
        int[] redHistogram = new int[256];
        int[] greenHistogram = new int[256];
        int[] blueHistogram = new int[256];

        // Iterate through each pixel in the image and update histograms
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                redHistogram[color.getRed()]++;
                greenHistogram[color.getGreen()]++;
                blueHistogram[color.getBlue()]++;
            }
        }

        // Find the most common red, green, and blue values
        int mostCommonRed = findMostCommonValue(redHistogram);
        int mostCommonGreen = findMostCommonValue(greenHistogram);
        int mostCommonBlue = findMostCommonValue(blueHistogram);

        return new Color(mostCommonRed, mostCommonGreen, mostCommonBlue);
    }

    /**
     * Helper method to find the most common value in a histogram.
     *
     * @param histogram The histogram to analyze.
     * @return The most common value.
     */
    private static int findMostCommonValue(int[] histogram) {
        int maxCount = 0;
        int mostCommonValue = 0;

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > maxCount) {
                maxCount = histogram[i];
                mostCommonValue = i;
            }
        }

        return mostCommonValue;
    }

    /**
     * Calculates a tolerance value for color comparison based on the backgroundThreshold.
     *
     * @param backgroundThreshold The threshold color for comparison.
     * @return The calculated tolerance value.
     */
    public static int calculateTolerance(Color backgroundThreshold) {
        // Calculate a tolerance value based on the color difference
        // You can adjust this calculation based on your specific requirements

        // Calculate the maximum differences for red, green, and blue channels
        int maxRedDifference = Math.max(Math.abs(255 - backgroundThreshold.getRed()),
                Math.abs(backgroundThreshold.getRed()));
        int maxGreenDifference = Math.max(Math.abs(255 - backgroundThreshold.getGreen()),
                Math.abs(backgroundThreshold.getGreen()));
        int maxBlueDifference = Math.max(Math.abs(255 - backgroundThreshold.getBlue()),
                Math.abs(backgroundThreshold.getBlue()));

        // Return the maximum of the differences divided by 2 as tolerance
        return Math.max(maxRedDifference, Math.max(maxGreenDifference, maxBlueDifference)) / 2; // Adjust this factor as needed
    }

    /**
     * Checks if two colors are similar within a given tolerance.
     *
     * @param color          The first color for comparison.
     * @param colorCompareTo The second color for comparison.
     * @param tolerance      The tolerance value for comparison.
     * @return True if the colors are similar within the tolerance, false otherwise.
     */
    public static boolean isSimilar(Color color, Color colorCompareTo, int tolerance) {
        // Check if the differences in red, green, and blue channels are within the tolerance
        return Math.abs(color.getRed() - colorCompareTo.getRed()) <= tolerance
                && Math.abs(color.getGreen() - colorCompareTo.getGreen()) <= tolerance
                && Math.abs(color.getBlue() - colorCompareTo.getBlue()) <= tolerance;
    }
}
