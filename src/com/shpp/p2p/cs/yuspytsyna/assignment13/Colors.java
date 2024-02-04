
package com.shpp.p2p.cs.yuspytsyna.assignment13;

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
        int[][] histogram = calculateHistogram(image);
        int mostCommonRed = findMostCommonValue(histogram[0]);
        int mostCommonGreen = findMostCommonValue(histogram[1]);
        int mostCommonBlue = findMostCommonValue(histogram[2]);
        Color background = new Color(mostCommonRed, mostCommonGreen, mostCommonBlue);

        return (isSimilar(background, findContourColor(image), calculateTolerance(background))) ? background : getLighterColor(background, findContourColor(image));
    }

    /**
     * Calculates the color histogram of the provided image.
     *
     * @param image The image for which to calculate the histogram.
     * @return A 2D array representing the histogram for red, green, and blue channels.
     */
    private static int[][] calculateHistogram(BufferedImage image) {
        int[][] histogram = new int[3][256];
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                histogram[0][color.getRed()]++;
                histogram[1][color.getGreen()]++;
                histogram[2][color.getBlue()]++;
            }
        }

        return histogram;
    }

    /**
     * Finds the most common value in a histogram.
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
     * Extracts the contour of an image (dummy implementation for demonstration purposes).
     *
     * @param image The original image.
     * @return A new BufferedImage representing the contour.
     */
    private static BufferedImage extractContour(BufferedImage image) {
        // Dummy implementation for demonstration purposes
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Finds the most common color along the image's contour.
     *
     * @param image The image to analyze.
     * @return The most common color along the contour.
     */
    private static Color findContourColor(BufferedImage image) {
        BufferedImage contourImage = extractContour(image);
        int[][] histogram = calculateHistogram(contourImage);
        int mostCommonRed = findMostCommonValue(histogram[0]);
        int mostCommonGreen = findMostCommonValue(histogram[1]);
        int mostCommonBlue = findMostCommonValue(histogram[2]);

        return new Color(mostCommonRed, mostCommonGreen, mostCommonBlue);
    }

    /**
     * Returns the lighter of the two colors.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     * @return The lighter color.
     */
    private static Color getLighterColor(Color color1, Color color2) {
        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), hsv1);
        Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), hsv2);
        return (hsv1[2] > hsv2[2]) ? color1 : color2;
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
        return Math.abs(color.getRed() - colorCompareTo.getRed()) <= tolerance
                && Math.abs(color.getGreen() - colorCompareTo.getGreen()) <= tolerance
                && Math.abs(color.getBlue() - colorCompareTo.getBlue()) <= tolerance;
    }
}
