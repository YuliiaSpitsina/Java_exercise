package com.shpp.p2p.cs.yuspytsyna.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*
Assignment2Part4.java - Creating a three color flag
 */
public class Assignment2Part4 extends WindowProgram {

    /*
    The size of the flag in width and height
     */
    private static final int FLAG_WIDTH = 1000;
    private static final int FLAG_HEIGHT = 900;

    /*Constants for control the width and height of the program */
    public static final int APPLICATION_WIDTH = FLAG_WIDTH + 100;
    public static final int APPLICATION_HEIGHT = FLAG_HEIGHT + 150;

    /*
    Create your own colors: mint and dark green
     */
    private static final Color MINT = new Color(0, 255, 164);
    private static final Color DARK_GREEN = new Color(9, 98, 0);

    /*
    Creating a three-color flag with horizontal stripes
    */
    public void run() {
        /*
        Calculation of the width of each strip
        */
        double stripeWidth = FLAG_WIDTH / 3.0;

        /*
        Centering the flag in the program
         */
        double x = (getWidth() - FLAG_WIDTH) / 2.0;
        double y = (getHeight() - FLAG_HEIGHT) / 2.0;

        /*
        Drawing a tricolor flag
         */
        drawFlag(x, y, stripeWidth);

        /*
        Write the country name at the bottom right
         */
        writeCountryNameAtBottomRight();
    }

    /*
    Drawing 3 strips
     */
    private void drawFlag(double x, double y, double stripeWidth) {
        drawFirstStripe(x, y, stripeWidth);
        drawSecondStripe(x, y, stripeWidth);
        drawThirdStripe(x, y, stripeWidth);
    }

    /*
    Drawing the first strip
    Color: dark green
     */
    private void drawFirstStripe(double x, double y, double stripeWidth) {
        GRect firstStripe = new GRect(x, y, stripeWidth, FLAG_HEIGHT);
        firstStripe.setFilled(true);
        firstStripe.setColor(DARK_GREEN);
        add(firstStripe);
    }

    /*
    Drawing the second strip
    Color: green
     */
    private void drawSecondStripe(double x, double y, double stripeWidth) {
        GRect secondStripe = new GRect(x + stripeWidth, y, stripeWidth, FLAG_HEIGHT);
        secondStripe.setFilled(true);
        secondStripe.setColor(Color.GREEN);
        add(secondStripe);
    }

    /*
    Drawing the third strip
    Color: mint
     */
    private void drawThirdStripe(double x, double y, double stripeWidth) {
        GRect thirdStripe = new GRect(x + 2 * stripeWidth, y, stripeWidth, FLAG_HEIGHT);
        thirdStripe.setFilled(true);
        thirdStripe.setColor(MINT);
        add(thirdStripe);
    }

    /*
    Write the country name at the bottom right
    */
    private void writeCountryNameAtBottomRight() {
        GLabel label = new GLabel("Flag of Empire of the Green");
        /*
        Setting of the font Ariel Black
         */
        label.setFont("Arial Black");
        /*
        Search for the width and height of the label
        Setting the label in the lower right corner
         */
        add(label, getWidth() - label.getWidth(), getHeight() - label.getHeight());
    }
}
