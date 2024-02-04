package com.shpp.p2p.cs.yuspytsyna.assignment2;

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*
Assignment2Part3.java - Drawing 3 black paw prints and a heel
*/
public class Assignment2Part3 extends WindowProgram {

    /*
    Constants controlling the relative positions of the
    three toes to the upper-left corner of the paw print
    */
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the paw print.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    /*Constants for control the width and height of the program */
    public static final int APPLICATION_WIDTH = 370;
    public static final int APPLICATION_HEIGHT = 320;

    /* Draw paws at the specified coordinates */
    public void run() {
        drawPawprint(20, 20);
        drawPawprint(180, 70);
    }


    /*Drawing 3 black paws and a heel*/
    private void drawPawprint(double x, double y) {
        drawFirstToe(x, y);
        drawSecondToe(x, y);
        drawThirdToe(x, y);
        drawHeel(x, y);
    }

    /*Drawing the first black paw, taking into account the deviation*/
    private void drawFirstToe(double x, double y) {
        drawToe(x, y, FIRST_TOE_OFFSET_X, FIRST_TOE_OFFSET_Y);
    }

    /*Drawing the second black paw, taking into account the deviation*/
    private void drawSecondToe(double x, double y) {
        drawToe(x, y, SECOND_TOE_OFFSET_X, SECOND_TOE_OFFSET_Y);
    }

    /*Drawing the third black paw, taking into account the deviation*/
    private void drawThirdToe(double x, double y) {
        drawToe(x, y, THIRD_TOE_OFFSET_X, THIRD_TOE_OFFSET_Y);
    }

    /*Drawing the black heel, taking into account the deviation*/
    private void drawHeel(double x, double y) {
        GOval heel = new GOval(x + HEEL_OFFSET_X, y + HEEL_OFFSET_Y, HEEL_WIDTH, HEEL_HEIGHT);
        heel.setFilled(true);
        heel.setFillColor(Color.BLACK);
        add(heel);
    }

    /*Draws ovals in offset coordinates with a black background */
    private void drawToe(double x, double y, double xOffset, double yOffset) {
        GOval toe = new GOval(x + xOffset, y + yOffset, TOE_WIDTH, TOE_HEIGHT);
        toe.setFilled(true);
        toe.setFillColor(Color.BLACK);
        add(toe);

    }
}


