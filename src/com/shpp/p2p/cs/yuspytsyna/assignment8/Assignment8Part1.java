package com.shpp.p2p.cs.yuspytsyna.assignment8;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This program creates a grid of squares where the bottom row is filled with black squares.
 * When a black square is clicked, it moves up until it hits the top of the window, and then moves down
 * to its original position. Multiple squares can move simultaneously.
 */
public class Assignment8Part1 extends WindowProgram {

    // Number of squares in each row
    private static final int SQUARES_IN_ROW = 10;

    // Number of squares in each column
    private static final int SQUARES_IN_COL = 10;

    // Size of each square (width and height)
    private static final int SQUARES_SIZE = 50;

    // Spacing between squares
    private static final int SQUARES_SPACING = 10;

    // Animation interval (in milliseconds)
    private static final int ANIMATION_INTERVAL = 300;

    // Distance to move squares
    private static final int MOVE_DISTANCE = SQUARES_SIZE + SQUARES_SPACING;

    // Add extra height to program
    private static final int ADD_HEIGHT = 30;

    // ArrayList to store black squares in the bottom row
    private final ArrayList<GRect> blackSquares = new ArrayList<>();

    // Set to keep track of squares currently in flight (moving)
    private final Set<GRect> squaresInFlight = new HashSet<>();

    @Override
    public void run() {
        initializeWindow();
        createGrid();
        addMouseListeners();
    }

    /**
     * Initializes the size of the application window based on the grid dimensions.
     */
    private void initializeWindow() {
        // Calculate the total width of the grid, including spacing
        int gridWidth = (SQUARES_IN_COL * (SQUARES_SIZE + SQUARES_SPACING) + SQUARES_SPACING);

        // Calculate the total height of the grid, including spacing
        int gridHeight = (SQUARES_IN_ROW * (SQUARES_SIZE + SQUARES_SPACING));

        // Set the size of the application window to match the grid dimensions
        setSize(gridWidth, gridHeight + ADD_HEIGHT);
    }


    /**
     * Creates the grid of squares and populates
     * the black squares in the bottom row.
     */
    private void createGrid() {
        for (int j = 0; j < SQUARES_IN_ROW; j++) {
            for (int i = 0; i < SQUARES_IN_COL; i++) {
                double x = i * (SQUARES_SIZE + SQUARES_SPACING);
                double y = j * (SQUARES_SIZE + SQUARES_SPACING);

                GRect box = createBox(x, y);
                add(box);

                // Fill the bottom row with black squares
                if (j == SQUARES_IN_ROW - 1) {
                    box.setFillColor(Color.BLACK);

                    // Add the black square to the list
                    blackSquares.add(box);
                }
            }
        }
    }

    /**
     * Creates a white square at the specified coordinates.
     *
     * @param x The x-coordinate of the square.
     * @param y The y-coordinate of the square.
     * @return The created GRect representing the square.
     */
    private GRect createBox(double x, double y) {
        GRect box = new GRect(x, y, SQUARES_SIZE, SQUARES_SIZE);
        box.setColor(Color.WHITE); // Setting white board
        box.setFilled(true);
        box.setFillColor(Color.WHITE);
        return box;
    }

    /**
     * Responds to a mouse click event.
     *
     * @param e The MouseEvent representing the mouse click.
     */
    public void mousePressed(MouseEvent e) {
        for (GRect blackSquare : blackSquares) {
            // If the black square is not in flight
            // and the mouse click is inside the square:

            if (!squaresInFlight.contains(blackSquare))
                if (blackSquare.contains(e.getX(), e.getY())) {

                    // Move the square up and down
                    moveSquareUpDown(blackSquare);
                    break;
                }
        }
    }

    /**
     * Moves a black square up and then down to its original position with animation.
     *
     * @param square The GRect representing the black square to move.
     */
    private void moveSquareUpDown(GRect square) {
        // Create a timer to handle the animation
        Timer timer = new Timer(ANIMATION_INTERVAL, new ActionListener() {

            // Flag to track whether the square is moving up or down
            boolean movingUp = true;

            // Store the original Y-coordinate of the square
            final double originalY = square.getY();

            public void actionPerformed(ActionEvent e) {
                if (movingUp) {

                    // Calculate the new Y-coordinate for moving up
                    double newY = square.getY() - MOVE_DISTANCE;
                    if (newY >= 0) {
                        square.move(0, -MOVE_DISTANCE);
                    } else {
                        // Change the direction to moving down
                        movingUp = false;
                    }
                } else {
                    // Calculate the new Y-coordinate for moving down
                    double newY = square.getY() + MOVE_DISTANCE;
                    if (newY <= originalY) {
                        square.move(0, MOVE_DISTANCE);
                    } else {
                        squaresInFlight.remove(square); // Remove square from the set when it returns to the original position
                        ((Timer) e.getSource()).stop(); // Stop the timer when the square reaches the original position
                    }
                }
            }
        });

        squaresInFlight.add(square);

        // Start the timer to initiate the animation
        timer.start();
    }
}
