package com.shpp.p2p.cs.yuspytsyna.assignment13;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Assignment13Part1 {

    // Ratio for recognized garbage
    private static final double SMALLER_RATIO = 0.04;

    // Constants for structuring element size, and coefficients
    // Change for different pictures
    private static final int MIN_STRUCTURING_ELEMENT_SIZE = 1;
    private static final double SIZE_COEFFICIENT = 0.00001;
    private static final double STD_DEV_COEFFICIENT = 0.001;

    // List to store silhouette sizes
    static List<Integer> listOfShapes = new ArrayList<>();

    // List to store filtered silhouette sizes
    static List<Integer> filteredListOfShapes = new ArrayList<>();


    // Image matrix and related attributes
    private final int[][] image;
    private final int rows;
    private final int cols;
    private final boolean[][] visited;

    /**
     * Constructor for SilhouetteCounter.
     *
     * @param image The image matrix.
     */
    public Assignment13Part1(int[][] image) {
        this.image = image;
        this.rows = image.length;
        this.cols = image[0].length;
        this.visited = new boolean[rows][cols];
    }


    /**
     * Counts the number of silhouettes in the provided image.
     *
     * @return The number of silhouettes.
     */
    public int countSilhouettes() {
        listOfShapes.clear();
        filteredListOfShapes.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (image[i][j] == 1 && !visited[i][j]) {
                    int silhouetteSize = bfs(i, j);
                    listOfShapes.add(silhouetteSize);
                }
            }
        }

        return filterGarbage(listOfShapes).size();
    }

    /**
     * Filters silhouette sizes based on a given ratio.
     *
     * @param listOfShapes The list of silhouette sizes.
     * @return A filtered list of silhouette sizes.
     */
    private static List<Integer> filterGarbage(List<Integer> listOfShapes) {
        if (!listOfShapes.isEmpty()) {
            int maxShape = Collections.max(listOfShapes);
            for (Integer shape : listOfShapes) {
                if ((double) shape / maxShape > SMALLER_RATIO) {
                    filteredListOfShapes.add(shape);
                }
            }
        }
        return filteredListOfShapes;
    }

    /**
     * Breadth-First Search (BFS) algorithm to calculate the silhouette size starting from a given pixel.
     *
     * @param i The row index of the starting pixel.
     * @param j The column index of the starting pixel.
     * @return The size of the silhouette.
     */
    private int bfs(int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int silhouetteSize = 0;

        while (!queue.isEmpty()) {
            int[] currPixel = queue.poll();
            silhouetteSize++;

            for (int[] direction : directions) {
                int newRow = currPixel[0] + direction[0];
                int newCol = currPixel[1] + direction[1];

                if (isValid(newRow, newCol) && image[newRow][newCol] == 1 && !visited[newRow][newCol]) {
                    queue.add(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                }
            }
        }

        return silhouetteSize;
    }

    /**
     * Checks if the given indices represent a valid position in the image matrix.
     *
     * @param i The row index.
     * @param j The column index.
     * @return True if the indices are valid, false otherwise.
     */
    private boolean isValid(int i, int j) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

    /**
     * Processes the image to determine the silhouettes by converting it to a binary array based on color thresholding.
     *
     * @param fileName The file name of the image to be processed.
     * @return A binary array representing the silhouette in the image.
     * @throws IOException If the file cannot be found or loaded.
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
     * Converts the given image to a binary array based on color similarity with the background threshold.
     *
     * @param image               The input image to be converted to a binary array.
     * @param backgroundThreshold The color representing the background threshold.
     * @param tolerance           The tolerance for color similarity.
     * @return A binary array representing the silhouette in the image.
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
     * Erodes the binary image using a structuring element based on the number of silhouettes.
     *
     * @param binaryImage The binary image to be eroded.
     * @param silhouettes The number of silhouettes in the image.
     * @return The eroded binary image.
     */
    public static int[][] erode(int[][] binaryImage, int silhouettes) {
        int structuringElementSize = determineStructuringElementSize(silhouettes);

        int[][] structuringElement = createStructuringElement(structuringElementSize);

        int rows = binaryImage.length;
        int cols = binaryImage[0].length;
        int[][] result = new int[rows][cols];

        int structuringElementRows = structuringElement.length;
        int structuringElementCols = structuringElement[0].length;

        // Iterate over each pixel in the binary image
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Initialize the result for this pixel to 1 (assume it fits the structuring element)
                result[i][j] = 1;

                // Check if the structuring element fits the binary image at this position
                for (int m = 0; m < structuringElementRows; m++) {
                    for (int n = 0; n < structuringElementCols; n++) {
                        int si = i - structuringElementRows / 2 + m;
                        int sj = j - structuringElementCols / 2 + n;

                        // Check if the pixel is within bounds of the binary image
                        if (si >= 0 && si < rows && sj >= 0 && sj < cols) {
                            // Check if the structuring element fits
                            if (structuringElement[m][n] == 1 && binaryImage[si][sj] != 1) {
                                // If any part of the structuring element doesn't fit, set result to 0
                                result[i][j] = 0;
                                break;
                            }
                        } else {
                            // If the structuring element is out of bounds, set result to 0
                            result[i][j] = 0;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Calculates the standard deviation of the silhouette sizes.
     *
     * @param silhouetteSizes       List of silhouette sizes.
     * @param averageSilhouetteSize The average silhouette size.
     * @return The standard deviation of silhouette sizes.
     */
    public static double calculateStdDev(List<Integer> silhouetteSizes, int averageSilhouetteSize) {
        int n = silhouetteSizes.size();
        double sumOfSquares = 0;

        // Calculate the sum of squares of deviations from the mean
        for (int size : silhouetteSizes) {
            double deviation = size - averageSilhouetteSize;
            sumOfSquares += deviation * deviation;
        }

        // Calculate variance (mean square deviation)
        double variance = sumOfSquares / n;

        // Calculate standard deviation
        return Math.sqrt(variance);
    }

    /**
     * Determines the size of the structuring element based on the average silhouette size and standard deviation.
     *
     * @param silhouettes The number of silhouettes in the image.
     * @return The size of the structuring element.
     */
    private static int determineStructuringElementSize(int silhouettes) {
        int averageSilhouetteSize = calculateAverageSilhouetteSize(silhouettes);

        double stdDev = calculateStdDev(listOfShapes, averageSilhouetteSize);
        int structuringElementSize = (int) (averageSilhouetteSize * SIZE_COEFFICIENT + stdDev * STD_DEV_COEFFICIENT);

        structuringElementSize = Math.max(structuringElementSize, MIN_STRUCTURING_ELEMENT_SIZE);
        return structuringElementSize;
    }

    /**
     * Calculates the average silhouette size.
     *
     * @param silhouettes The total number of silhouettes.
     * @return The average silhouette size.
     */
    private static int calculateAverageSilhouetteSize(int silhouettes) {

        int sum = 0;

        for (Integer number : filteredListOfShapes) {
            sum += number;
        }
        return sum / silhouettes;
    }

    /**
     * Creates a structuring element with all elements set to 1.
     *
     * @param structuringElementSize The size of the structuring element (both rows and columns).
     * @return The created structuring element.
     */
    private static int[][] createStructuringElement(int structuringElementSize) {
        int[][] structuringElement = new int[structuringElementSize][structuringElementSize];

        for (int i = 0; i < structuringElementSize; i++) {
            for (int j = 0; j < structuringElementSize; j++) {
                structuringElement[i][j] = 1;
            }
        }

        return structuringElement;
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

    public static void main(String[] args) {
        String fileName = (args.length > 0) ? args[0] : "assets/test.jpg";
        int[][] binaryImage;
        int result;
        int newResult;
        try {
            binaryImage = imageProcessing(fileName);
            Assignment13Part1 counter = new Assignment13Part1(binaryImage);
            result = counter.countSilhouettes();
            binaryImage = erode(binaryImage, result);
            Assignment13Part1 counters = new Assignment13Part1(binaryImage);
            newResult = counters.countSilhouettes();
            System.out.println(newResult);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
