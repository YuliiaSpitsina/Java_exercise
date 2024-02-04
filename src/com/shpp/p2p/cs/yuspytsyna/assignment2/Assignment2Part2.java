package com.shpp.p2p.cs.yuspytsyna.assignment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*
Assignment2Part2.java - Draws 4 black circles at the corners of the program
and a white rectangle that overlaps a quarter of each circle
 */
public class Assignment2Part2 extends WindowProgram {

    /*Constants for control the width and height of the program */
    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 600;

    public void run() {
        /*
        Search for the diameter of a circle,
        due to the size of the program.
        One third of the smallest of the width or height
        is equal to the diameter of the circle
        */
        double circleDiameter;
        if (getWidth() < getHeight()) {
            circleDiameter = getWidth() / 3.0;
        } else circleDiameter = getHeight() / 3.0;

        /* Draws black circles at the corners of the program */
        drawCircleInCorners(circleDiameter);

        /*Draws a white rectangle over the circles*/
        drawRectangle(circleDiameter);
    }


    /* Draws black circles at the corners of the program */
    private void drawCircleInCorners(double circleDiameter) {
        for (int i = 0; i <= getWidth() - circleDiameter; i += getWidth() - circleDiameter) { //Executed for location x
            for (int j = 0; j <= getHeight() - circleDiameter; j += getHeight() - circleDiameter) { //Executed for location y
                drawCircle(i, j, circleDiameter);
            }
        }
    }

    /* Draws black circles at coordinates x and y */
    private void drawCircle(int x, int y, double circleDiameter) {
        GOval circle = new GOval(x, y, circleDiameter, circleDiameter);
        circle.setFilled(true);
        circle.setFillColor(Color.BLACK); //  Black background
        add(circle);
    }

    /*Draws a white rectangle over the circles
    Start and end at half the diameter of the circle
    */
    private void drawRectangle(double circleDiameter) {
        GRect rectangle = new GRect(circleDiameter / 2, circleDiameter / 2, getWidth() - circleDiameter, getHeight() - circleDiameter);
        rectangle.setColor(Color.WHITE); // Setting white board
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.WHITE); // Setting white background
        add(rectangle);
    }

}
