package com.shpp.p2p.cs.yuspytsyna.assignment3;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.util.ArrayList;

/**
 * Assignment3Part6.java - Create a 5-second animation
 * where a plane flies, clouds move, and a blue
 * and yellow flag is created behind the plane
 * */
public class Assignment3Part6 extends WindowProgram {
    /* The amount of time to pause between frames. */
    private static final double PAUSE_TIME = 1000.0 / 24;

    /* Create your own color light blue */
    private static final Color LIGHT_BLUE = new Color(85, 255, 238);

    private static final int SPEED = 5;
    /*
    * Constants controlling the relative positions of the
    * three cloud part to the upper-left corner of the cloud
    * */
    private static final double[] CLOUD_PART_OFFSET_X = {0, 50, 100};
    private static final double[] CLOUD_PART_OFFSET_Y = {0, -30, 0};

    /*Diameter of each cloud part*/
    private static final double CLOUD_DIAMETER = 70;

    /*
    * Constants controlling the relative
    * positions between clouds
    * */
    private static final int CLOUD_OFFSET_X = 500;
    private static final int CLOUD_OFFSET_Y = 100;

    /*Constants controlling the amount of clouds in the animation */
    private static final int CLOUD_AMOUNT = 4;

    /*Location of the first cloud*/
    int startCloudPositionX = 100;
    int startCloudPositionY = 300;

    /*For holding airplane and cloud elements*/
    private final ArrayList<GOval> clouds = new ArrayList<>();
    private final ArrayList<GOval> plane = new ArrayList<>();

    /**
     * Setting light blue background
     * Creating an image of an airplane and clouds
     * and their movement
     * */
    public void run(){
        setBackground(LIGHT_BLUE);
        createPlane();
        createClouds(startCloudPositionX, startCloudPositionY);
        createAnimation();
    }

    /**
     * All objects are moving, a flag is created behind the plane,
     * the program's running time is calculated (5 seconds)
     * */
    private void createAnimation() {
        /*Initial time value*/
        long startTime = System.currentTimeMillis();

        /*Loop to start and move elements on the screen*/
        while (true){
            movePlane(); // Move plane parts
            moveClouds(); // Move clouds parts

            /*
            * Position of the main part of the plane
            * by x during continuous flight
            * */
            double x = plane.get(0).getX();

            drawFlag(x); // Creating a flag behind the main part of the plane

            pause(PAUSE_TIME);

            /*Current time value*/
            long currentTime = System.currentTimeMillis();
            /*Execution time of one iteration*/
            long elapsedTime = currentTime - startTime;

            if (elapsedTime >= 5500) {
                break; // Exit the loop after 5 seconds
            }
        }
    }

    /**
     * Creating a flag behind the plane
     * Flag has two colors: blue and yellow
     * */
    private void drawFlag(double x) {
        createRectangle(x - 40, 80, Color.BLUE);
        createRectangle(x - 40, 110, Color.YELLOW);
    }

    /**
     * Iterate through the list with the parts of the clouds,
     * and move all the parts backward
     * */
    private void moveClouds() {
        for (GOval cloud : clouds) {
            cloud.move(-SPEED, 0);
        }
    }

    /**
     * Iterate through the list with the parts of the plane,
     * and move all the parts forward
     * */
    private void movePlane() {
        for (GOval cloud : plane) {
            cloud.move(SPEED, 0);
        }
    }

    /**
     * Draw oval with offset to make plane
     * Adding each part to the array list
     * */
    private void createPlane() {
        GOval main = addObject(10, 100,80,35);
        add(main);
        plane.add(main);
        GOval wing1 = addObject(50, 70,20,50);
        add(wing1);
        plane.add(wing1);
        GOval wing2 = addObject(50, 115,20,50);
        add(wing2);
        plane.add(wing2);
        GOval tail1 = addObject(10, 90,10,25);
        add(tail1);
        plane.add(tail1);
        GOval tail2 = addObject(10, 120,10,25);
        add(tail2);
        plane.add(tail2);
    }


    /**
     * Creating many clouds at first in start position
     * then draw next clouds with offset
     * */
    private void createClouds(int startPositionX, int startPositionY) {
        /* Changing position by x */
        for ( int positionX = startPositionX; positionX <= 100 + CLOUD_OFFSET_X * CLOUD_AMOUNT;
              positionX+=CLOUD_OFFSET_X) {

            drawCloud(positionX, startPositionY);

            /* Changing position by y */
            if (startPositionY == startCloudPositionY) {
                startPositionY -= CLOUD_OFFSET_Y;
            } else startPositionY += CLOUD_OFFSET_Y;
        }
    }

    /**
     * Draw circle with offset to make clouds
     * Adding each part to the array list
     * */
    private void drawCloud(double positionX, double positionY) {
        for (int i = 0; i < 3; i++) {
            GOval cloudPart = drawCloudPart(positionX + CLOUD_PART_OFFSET_X[i],
                                            positionY + CLOUD_PART_OFFSET_Y[i]);
            add(cloudPart);
            clouds.add(cloudPart); // Add to array list
        }

    }

    /**
     * Creating circle for the clouds
     * SSetting white border and background
     * */
    private GOval drawCloudPart(double x, double y) {
        GOval o = new GOval(x,y, CLOUD_DIAMETER, CLOUD_DIAMETER);
        o.setColor(Color.WHITE);
        o.setFilled(true);
        o.setFillColor(Color.WHITE);
        return o;
    }

    /**
     * Creating oval for the plane
     * Setting black background
     * */
    private GOval addObject (int x, int y, int width, int height){
        GOval o = new GOval(x,y, width, height);
        o.setFilled(true);
        o.setFillColor(Color.BLACK);
        return o;
    }

    /**
     * Creating rectangles for the flag
     * Setting border and background
     * */
    private void createRectangle(double x, double y, Color color) {
        GRect rect = new GRect(x, y, 30, 30);
        rect.setFilled(true);
        rect.setColor(color);
        rect.setFillColor(color);
        add(rect);
    }
}
