package com.shpp.p2p.cs.yuspytsyna.assignment12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class provides methods to count silhouettes in an image.
 */
public class Assignment12Part1 {

    // Possible directions for traversing the image
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    // Minimum silhouette size to be counted
    private static final int MIN_SILHOUETTE_SIZE = 50;


    /**
     * Counts the number of silhouettes in the given binary image.
     *
     * @param image Binary image represented as a 2D array
     * @return The count of silhouettes in the image
     */
    public static int countSilhouettes(int[][] image) {
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }

        int count = 0;
        int rows = image.length;
        int cols = image[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // Iterate through the image pixels and determine silhouettes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if the pixel is part of a silhouette
                if (image[i][j] == 1 && !visited[i][j]) {
                    int silhouetteSize = dfs(image, visited, i, j, rows, cols);
                    if (silhouetteSize >= MIN_SILHOUETTE_SIZE) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Depth-first search to traverse the binary image and find a silhouette's size.
     *
     * @param image   Binary image represented as a 2D array
     * @param visited Array to track visited pixels
     * @param i       Current row index
     * @param j       Current column index
     * @param rows    Total number of rows in the image
     * @param cols    Total number of columns in the image
     * @return Size of the silhouette starting from the given position
     */
    private static int dfs(int[][] image, boolean[][] visited, int i, int j, int rows, int cols) {
        // Check if the current pixel is outside
        // the image or already visited or not part of a silhouette
        if (i < 0 || i >= rows || j < 0 || j >= cols || image[i][j] == 0 || visited[i][j])
            return 0;

        // Mark the current pixel as visited
        visited[i][j] = true;

        int size = 1;

        // Traverse in all directions to find the silhouette's size
        for (int[] direction : DIRECTIONS) {
            int newRow = i + direction[0];
            int newCol = j + direction[1];
            size += dfs(image, visited, newRow, newCol, rows, cols);
        }

        return size;
    }

    /**
     * Process the image to convert it to a binary representation.
     *
     * @param fileName The file name of the image
     * @return Binary representation of the image
     * @throws IOException If there is an error reading the image
     */
    public static int[][] imageProcessing(String fileName) throws IOException {
        // Load the image from the specified file
        BufferedImage image = loadImage(fileName);

        // Find the most common color to determine the background threshold
        Color backgroundThreshold = Colors.findMostCommonColor(image);
        int tolerance = Colors.calculateTolerance(backgroundThreshold);

        // Convert the image to a binary array based
        // on the calculated background threshold and tolerance
        return convertImageToBinaryArray(image, backgroundThreshold, tolerance);
    }

    /**
     * Converts the loaded image into a binary array based on the background color.
     *
     * @param image               BufferedImage object representing the image
     * @param backgroundThreshold The color threshold for considering a pixel as background
     * @param tolerance           Tolerance level for color matching
     * @return Binary array representing the image
     */
    private static int[][] convertImageToBinaryArray(BufferedImage image, Color backgroundThreshold, int tolerance) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] imageArray = new int[height][width];

        // Iterate through the image pixels to determine the binary representation
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the color of the current pixel
                Color color = new Color(image.getRGB(x, y));

                // Determine if the current pixel is part of the background
                // or silhouette based on color similarity
                imageArray[y][x] = (Colors.isSimilar(backgroundThreshold, color, tolerance)) ? 0 : 1;
            }
        }
        return imageArray;
    }



    /**
     * Load an image from the specified file.
     *
     * @param fileName The file name of the image to be loaded
     * @return BufferedImage object representing the loaded image
     * @throws IOException If the file cannot be found or loaded
     */
    private static BufferedImage loadImage(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("Cannot find the specified file");
        }

        return ImageIO.read(file);
    }

    public static int mains(String[] args) {
        String fileName = (args.length > 0) ? args[0] : "test.jpg";
        int[][] binaryImage;
        int result = 0;
        try {
            binaryImage = imageProcessing(fileName);
            result = countSilhouettes(binaryImage);
            //System.out.println("Number of silhouettes:  " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return result;
    }
    public static void main(String[] args) {
        check("assets/test.jpg", 4);
    }

    public static void check(String fileName, int expected) {
        int result = mains(new String[]{fileName});
        System.out.print(fileName);
        System.out.println(result == expected ? "  true " : "  false " + result);
    }
}
