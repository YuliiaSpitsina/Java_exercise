package com.shpp.p2p.cs.yuspytsyna.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
   Assignment1Part2.java - Placement of beepers on empty places in the column
*/
public class Assignment1Part2 extends KarelTheRobot {

    /*
      Being at the top of the column, Karel turns and looks at the base of the column.
      Moves forward and puts beepers, then returns to the top of the column,
      and moves to the next. Until it rests on the last column
    */
    public void run() throws Exception {
        while (frontIsClear()) {
            turnLeft();
            putBeepers();
            moveTopOfColumn();
            nextColumn();
        }
        /*
           Karel puts beepers on the last column
        */
        turnLeft();
        putBeepers();
    }

    /*
       Karel turns 180 degrees
       Result: looks in the opposite direction
    */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /*
      Precondition: Karel stands at the base of the column
      and looks north relative to the map.
      Karel makes a turn in the opposite direction
      and moves until he reaches the wall.
      Result: He will be at the top of the column and faces east
     */
    private void moveTopOfColumn() throws Exception {
        turnAround();
        moveUntilTop();
        turnLeft();
    }

    /*
       Karel will move forward until he meets an obstacle
    */
    private void moveUntilTop() throws Exception {
        while (frontIsClear()) {
            move();
        }
    }

    /*
      Karel moving towards the base of the column placing beepers on empty spaces
    */
    private void putBeepers() throws Exception {
            /*
            Karel moves forward until he reaches the base of the column.
            If there is an empty place, Karel puts on a beeper and takes a step,
            otherwise he moves on.
            */
            while(frontIsClear()) {
                if (!beepersPresent()) {
                    putBeeper();
                    move();
                }
                else
                    move();
            }
        /*
          Being at the base of the column,
          if the beeper is missing, then Karel puts it
        */
        if (!beepersPresent()) {
            putBeeper();
        }
    }

    /*
    Karel moves four steps forward,
    moving to the next column
     */
    private void nextColumn() throws Exception {
        for (int i = 0; i < 4; i++) {
            move();
        }
    }

}

