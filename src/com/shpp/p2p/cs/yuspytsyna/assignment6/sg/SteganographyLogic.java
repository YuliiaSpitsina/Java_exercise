package com.shpp.p2p.cs.yuspytsyna.assignment6.sg;

import acm.graphics.*;

public class SteganographyLogic {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int[][] pixels = source.getPixelArray();

        // Initialize a 2D boolean array to store the hidden message
        int numRows = pixels.length;
        int numCols = pixels[0].length;
        boolean[][] hiddenImage = new boolean[numRows][numCols];

        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {

                // Extract the red component of the pixel
                int red = GImage.getRed(pixels[row][col]);

                // Determine if the message bit is 1
                // based on the red component's parity
                hiddenImage[row][col] = red % 2 != 0;
            }
        }
        return hiddenImage;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int[][] pixels = source.getPixelArray();

        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {

                // Extract the green, blue, and red components of the pixel
                int green = GImage.getGreen(pixels[row][col]);
                int blue = GImage.getBlue(pixels[row][col]);
                int red = GImage.getRed(pixels[row][col]);

                /* if the image pixel is black, then make the red
                 * color value of the color image pixel odd
                 * if the pixel is white, then make it even
                 */
                if (message[row][col] && red % 2 == 0){
                    red = red + 1;
                } else if (!message[row][col] && red % 2 != 0) {
                    red = (red == 255) ? red - 1 : red + 1;
                }

                //                                        R    G     B
                pixels[row][col] = GImage.createRGBPixel(red, green, blue);
            }
        }

        return new GImage(pixels);
    }
}
