package com.shpp.p2p.cs.yuspytsyna.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
    Assignment1Part1.java - Karel is in a square house.
    He moves to the newspaper (beeper) lying in front of the house,
    picks it up and returns to the starting position.
*/
public class Assignment1Part1 extends KarelTheRobot {
    /*
    Karel moves to the newspaper (beeper),
    picks it up and returns to the starting position.
    */
    public void run() throws Exception {
        moveToPaper();
        pickBeeper();
        comeBack();
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
      Karel turns 180
      Result: looks in the opposite direction
    */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /*
       Karel will move forward until he meets an obstacle
    */
    private void moveUntilWall() throws Exception {
        while (frontIsClear()) {
            move();
        }
    }

    /*
    Karel moves forward until he stands in front of the wall,
    turns right, steps and turns left to move to another row,
    he will take two steps forward to be near the newspaper
    */
    private void moveToPaper() throws Exception {
        moveUntilWall();
        turnRight();
        move();
        turnLeft();
        move();
        move();
    }

    /*
    Karel turns 180 degrees. He enters the house,
    moves until he meets the wall turns to the right,
    steps to take the starting position
    and turns to the right to face east again
     */
    private void comeBack() throws Exception {
        turnAround();
        moveUntilWall();
        turnRight();
        move();
        turnRight();
    }
}