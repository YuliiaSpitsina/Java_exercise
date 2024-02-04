package com.shpp.p2p.cs.yuspytsyna.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
   Assignment1Part4.java - Placement of beepers in a checkerboard pattern
*/
public class Assignment1Part4 extends KarelTheRobot{
/*
   Being in the southwestern point,
   Karel puts beepers moving from west to east.
   Then it returns to the west and moves to the next line
   until it reaches the north
   If he has wall front him, he returns left and puts beepers
 */
    public void run() throws Exception {
       if (frontIsClear()) {
          while (leftIsClear()) {
           putBeepers();
           returnToOrigin();
           stepToNextRow();
          }
        /*
           Placement of beepers in the last northern line
         */
           putBeepers();
       } else {
         turnLeft();
         putBeepers();
       }
    }

    /*
      Karel makes three left turns
      Result: looks to the right of the starting position
   */
    private void turnRight() throws Exception {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }

    /*
       The beeper is placed in the first position,
       and then the method continues to move each time,
       moving forward two positions.
       A beeper is placed in every second free position
       as long as there are free positions ahead.
    */
    private void putBeepers() throws Exception {
        putBeeper();
        while (frontIsClear()) {
            move();
            if (frontIsClear()) {
                move();
                putBeeper();
            }
        }
    }

    /*
      Karel stands in the west, if there is a beeper under him,
      he moves to the next row and takes a step forward,
      otherwise he just goes to the next row
     */
    private void stepToNextRow() throws Exception {

      if(beepersPresent()) {
          turnRight();
          move();
          turnRight();
          move();
      }
      else {
          turnRight();
          move();
          turnRight();
      }

    }

    /*
        Precondition: Karel faces east, makes a turn to the west, and moves until it reaches the western wall
        Result: Karel stays and looks on the west
     */
    private void returnToOrigin() throws Exception {
        turnAround();
        while (frontIsClear()) {
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
