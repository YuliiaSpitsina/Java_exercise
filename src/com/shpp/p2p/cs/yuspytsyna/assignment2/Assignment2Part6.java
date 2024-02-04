package com.shpp.p2p.cs.yuspytsyna.assignment2;

import acm.graphics.GLine;
import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*
Assignment2Part6.java - Drawing a caterpillar
 */
public class Assignment2Part6 extends WindowProgram {
    /* The number of caterpillar segments */
    private static final int COUNT_CATERPILLAR_SEGMENTS = 5;

    /* Caterpillar segment size */
    private static final double CIRCLE_DIAMETER = 100;

    /* Offset for caterpillar cements horizontally and vertically */
    private static final double offsetX =  CIRCLE_DIAMETER * 0.55;
    private static final double offsetY =  CIRCLE_DIAMETER * 0.25;

    /*
    Custom color for caterpillar colour: darkish blue
    */
    private static final Color DARK_GREEN = new Color(10, 54, 166);

    /* Drawing a caterpillar */
    public void run() {
        drawCaterpillar();
    }

    /*Draws a caterpillar whose segments are either lower or higher.*/
    private void drawCaterpillar() {
        /*Starting position of first segment*/
        double x = 0;
        double y = 0;

        /*Drawing each segment in turn*/
        for (int i = 1; i <= COUNT_CATERPILLAR_SEGMENTS; i++) {
            /* Creating segment*/
            drawCaterpillarSegment(x, y);

            x += offsetX; // Move the next segment forward
            /*Draw even segments below and odd segments above*/
            y = i % 2 != 0 ? offsetY : 0;
        }
        drawCaterpillarFace(x, y);
    }

    /*
    Draws a yellow circle with two black ovals in the middle
    and a black line (caterpillar`s face)
    */
    private void drawCaterpillarFace(double x, double y) {
        /* Draws a yellow circle */
        GOval segment = new GOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        segment.setFilled(true);
        segment.setFillColor(Color.YELLOW); //Setting body color of caterpillar
        add(segment);

        drawCaterpillarEye(x, y); //Draws the caterpillar's eyes (two black ovals)
        createSmile(x, y); //Draws the caterpillar's smile (black line)
    }

    /* Draws the caterpillar's smile (black line) */
    private void createSmile(double x, double y) {
        /*
        Depending on the size of the caterpillar segment,
        the coordinates of the line location are determined
        */
        double leftSmileX = x + CIRCLE_DIAMETER / 4;
        double rightSmileX = x + CIRCLE_DIAMETER * 3 / 4;
        double smileY = y + CIRCLE_DIAMETER * 3 / 4;

        /* Line creation */
        GLine smile = new GLine(leftSmileX, smileY, rightSmileX, smileY);
        add(smile);
    }

    /*
    Depending on the size of the caterpillar segment,
    the coordinates of the location of the right and left eyes,
    as well as their sizes, are determined.
    */
    private void drawCaterpillarEye(double x, double y) {
        double eyeWidth = CIRCLE_DIAMETER / 4.0;
        double eyeHeight =  CIRCLE_DIAMETER / 3.0;

        double leftEyeX = x + CIRCLE_DIAMETER / 4 - eyeWidth / 2;
        double rightEyeX = x + CIRCLE_DIAMETER * 3 / 4 - eyeWidth / 2;
        double eyeY = y + CIRCLE_DIAMETER / 3 - eyeHeight / 2;

        /* Creation of the left and right eyes */
        createEye(leftEyeX, eyeY, eyeWidth, eyeHeight);
        createEye(rightEyeX, eyeY, eyeWidth, eyeHeight);
    }

    /* Creating a black oval (eye) at the given coordinates */
    private void createEye(double x, double y, double eyeWidth, double eyeHeight) {
        GOval eye = new GOval(x, y, eyeWidth, eyeHeight);
        eye.setColor(Color.WHITE);
        eye.setFilled(true);
        eye.setFillColor(Color.BLACK);
        add(eye);
    }


    /*
    Creating round segments for the caterpillar
    The segments have a red border and a blue background.
     */
    private void drawCaterpillarSegment(double x, double y) {
        GOval segment = new GOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        segment.setColor(Color.RED); // Setting red board
        segment.setFilled(true);
        segment.setFillColor(DARK_GREEN); //Setting body color of caterpillar
        add(segment);
    }

}
