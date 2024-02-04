package com.shpp.p2p.cs.yuspytsyna.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
    Assignment1Part3.java - finding center of the row
 */
public class Assignment1Part3 extends KarelTheRobot {
    /*
        Karel puts beepers along the first row.
        Then Karel picks the beepers one at a time,
        first from the west side, then from the east,
        and so on, until the beepers run out.
        Karel puts the last beeper in the middle of the first row.
     */
    public void run() throws Exception {
        putBeepersInRow();
        turnAround();
           if (frontIsClear()) {
              turnAround();
              clearEndpointsOfRow();
              pickBeepers();
              putCentreBeeper();
        }
    }

    /*
      Karel puts beepers along the first row.
     */
    private void putBeepersInRow() throws Exception {
        while (frontIsClear()){
            putBeeper();
            move();
        }
        /*
            Putting the last beeper in the row
         */
        putBeeper();
    }

    /*
        Karel picks beepers from the east and west points of the row.
        Stops at next west beeper point, looking east
     */
    private void clearEndpointsOfRow() throws Exception {
        turnAround();
        pickBeeper();
        moveUntilWall();
        pickBeeper();
        turnAround();
        move();
    }

    /*
        Karel picks up beepers while moving forward,
        and when there are no more beepers in the current position,
        it turns around, moves back, picks up the beeper,
        and moves forward again.
     */
    private void pickBeepers() throws Exception {
        while(beepersPresent()) {
            move();
            if (!beepersPresent()) {
                turnAround();
                move();
                pickBeeper();
                move();
            }
        }
    }

    /*
       Karel puts the central beeper
    */
    private void putCentreBeeper() throws Exception {
        turnAround();
        move();
        putBeeper();
    }

    /*
        Karel will move forward until he meets a wall
   */
    private void moveUntilWall() throws Exception {
        while(frontIsClear()) {
            move();
        }
    }

    /*
     Karel turns 180 degrees
     Result: looks in the opposite direction
   */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
}
