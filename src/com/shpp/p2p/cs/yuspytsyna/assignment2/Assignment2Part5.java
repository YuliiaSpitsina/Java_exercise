package com.shpp.p2p.cs.yuspytsyna.assignment2;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*
Assignment2Part5.java - Drawing a matrix of black boxes separated by "streets"
 */
public class Assignment2Part5 extends WindowProgram {
    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    public void run() {
        /* The width and height of the grid */
        double gridWidth = NUM_COLS * (BOX_SIZE + BOX_SPACING) - BOX_SPACING;
        double gridHeight = NUM_ROWS * (BOX_SIZE + BOX_SPACING) - BOX_SPACING;

         /*
        Centering the grid in the program
        Finding starting position x and y
         */
        double startX = (getWidth() - gridWidth) / 2;
        double startY = (getHeight() - gridHeight) / 2;

        /* Creating grid */
        createGrid(startX, startY);
    }

    /*
    Creating squares with indents between them
     */
    private void createGrid(double startX, double startY) {
        for (int j = 0; j < NUM_ROWS; j++) { // Creating squares in a row
            for (int i = 0; i < NUM_COLS; i++) { // Creating squares in a column
                /*
                Counting the location of each square
                 */
                double x = startX + i * (BOX_SIZE + BOX_SPACING);
                double y = startY + j * (BOX_SIZE + BOX_SPACING);

                /*
                Create and fill squares with black color
                 */
                createBox(x, y);
            }
        }
    }

    /*
    Creating square boxes for the grid.
    Boxes have a black background
     */
    private void createBox(double x, double y) {
        GRect box = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        box.setFilled(true);
        box.setFillColor(Color.BLACK);
        add(box);
    }
}
