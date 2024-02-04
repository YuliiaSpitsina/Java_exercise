package com.shpp.p2p.cs.yuspytsyna.assignment11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SilhouetteCounter {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private final int minSilhouetteSize;
    private final int backgroundThreshold;

    public SilhouetteCounter(int minSilhouetteSize, int backgroundThreshold) {
        this.minSilhouetteSize = minSilhouetteSize;
        this.backgroundThreshold = backgroundThreshold;
    }

    public int countSilhouettes(int[][] image) {
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }

        int count = 0;
        int rows = image.length;
        int cols = image[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (image[i][j] == 1 && !visited[i][j]) {
                    int silhouetteSize = dfs(image, visited, i, j, rows, cols);
                    if (silhouetteSize >= minSilhouetteSize) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private int dfs(int[][] image, boolean[][] visited, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || image[i][j] == 0 || visited[i][j])
            return 0;

        visited[i][j] = true;

        int size = 1;

        for (int[] direction : DIRECTIONS) {
            int newRow = i + direction[0];
            int newCol = j + direction[1];
            size += dfs(image, visited, newRow, newCol, rows, cols);
        }

        return size;
    }


    private int calculateOtThreshold(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            return calculateThreshold(image);
        } catch (IOException e) {
            System.err.println("Error loading the image.");
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }
    private int calculateThreshold(BufferedImage image) {
        int totalPixels = image.getWidth() * image.getHeight();

        int[] redHistogram = new int[256];
        int[] greenHistogram = new int[256];
        int[] blueHistogram = new int[256];

        // Calculate color histograms
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                redHistogram[color.getRed()]++;
                greenHistogram[color.getGreen()]++;
                blueHistogram[color.getBlue()]++;
            }
        }

        // Calculate threshold for each color channel
        int redThreshold = calculateThreshold(redHistogram, totalPixels);
        int greenThreshold = calculateThreshold(greenHistogram, totalPixels);
        int blueThreshold = calculateThreshold(blueHistogram, totalPixels);
        System.out.println(redThreshold+"     "+ greenThreshold+ "    " + blueThreshold);
        // Use the average of the color thresholds as the final threshold
        return (redThreshold + greenThreshold + blueThreshold) / 3;
    }

    private int calculateThreshold(int[] histogram, int totalPixels) {
        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        float sumB = 0;
        int wB = 0;
        int wF;

        float maxVar = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0)
                continue;

            wF = totalPixels - wB;
            if (wF == 0)
                break;

            sumB += i * histogram[i];
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            if (varBetween > maxVar) {
                maxVar = varBetween;
                threshold = i;
            }
        }

        return threshold;
    }


    public int countSilhouettes(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] imageArray = new int[height][width];

            int backgroundThreshold = calculateThreshold(image);
            System.out.println(backgroundThreshold);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(image.getRGB(x, y));
                    imageArray[y][x] = (getBrightness(color) < backgroundThreshold) ? 1 : 0;
                }
            }

            // System.out.println("Image Array:");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // System.out.print(imageArray[y][x] == 1 ? "X" : " ");
                }
                // System.out.println();
            }
            SilhouetteCounter counter = new SilhouetteCounter(100, backgroundThreshold);
            try {
                int silhouetteCount = counter.countSilhouettes(imageArray);
                return silhouetteCount;
            } catch (StackOverflowError e) {
                return -1;
            }
        } catch (IOException e) {
            return -2;
        }
    }

    private static int getBrightness(Color color) {
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
    }

    public static void main(String[] args) {
        SilhouetteCounter instance = new SilhouetteCounter(100, 0);


        //int backgroundThreshold = instance.calculateOtThreshold("test7.jpg");
        //instance = new Assignment12Part1(100, backgroundThreshold);

        check(instance, "test.jpg", 4);
        check(instance, "test1.jpg", 5);
        check(instance, "test2.jpg", 1);
        check(instance,"test3.jpg", 6);
        check(instance,"test4.jpg", 6);
        check(instance,"test5.jpg", 3);
        check(instance,"test6.jpg", 3);
        check(instance,"test7.jpg", 12);
        check(instance,"test8.jpg", 7);
        check(instance,"test9.jpg", 3);

        // other checks...
    }

    public static void check(SilhouetteCounter instance, String fileName, int expected) {
        int result = instance.countSilhouettes(fileName);
        System.out.print(fileName);
        System.out.println(result == expected ? "  true " : "  false " + result);
    }
}
