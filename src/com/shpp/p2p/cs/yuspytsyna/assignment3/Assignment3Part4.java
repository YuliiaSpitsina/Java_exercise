package com.shpp.p2p.cs.yuspytsyna.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Assignment3Part4.java - Build a pyramid of bricks
 */
public class Assignment3Part4 extends WindowProgram {
    /*Constants responsible for the width and height of each brick*/
    public static final int BRICK_HEIGHT = 50;
    public static final int BRICK_WIDTH = 80;

    /*
     * Constants responsible for how many
     * bricks are in the base of the pyramid
     * */
    public static final int BRICKS_IN_BASE = 5;

    /*
     * Constants responsible for the
     * width of the pyramid at the base.
     * */
    public static double WIDTH_PYRAMID = BRICK_WIDTH * BRICKS_IN_BASE;

    /**
     * Finds the origin for the pyramid image centered
     * horizontally, finds the origin vertically
     * and builds the pyramid
     */
    public void run() {
        double startX = (getWidth() - WIDTH_PYRAMID) / 2;
        double startY = getHeight() - (BRICK_HEIGHT) - 2;
        createPyramid(startX, startY);
    }

    /**
     * Builds a pyramid. Each row has one less brick.
     * Offset each row by half a brick
     */
    private void createPyramid(double startX, double startY) {
        double x = startX;
        for (int i = BRICKS_IN_BASE; i > 0; i--) { // Draws next row of bricks
            for (int j = 0; j < i; j++) { // Draws bricks in a row
                createBrick(x, startY);
                x += BRICK_WIDTH; // Offset of each brick in a row in a brick
            }
            /*Drawing a new row of bricks higher to the height of the brick*/
            startY -= BRICK_HEIGHT;
            /*Offset of each brick by half a brick, taking into account the row*/
            x = startX + BRICK_WIDTH * (BRICKS_IN_BASE - i + 1) / 2.0;
        }
    }

    /**
     * Draw yellow rectangle with
     * black border in special coordinates
     */
    private void createBrick(double x, double y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        brick.setFilled(true);
        brick.setFillColor(Color.YELLOW);
        add(brick);
    }


}
