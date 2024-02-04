package com.shpp.p2p.cs.yuspytsyna.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;


public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 6;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Diameter of the ball in pixels
     */
    private static final int BALL_DIAMETER = 2 * BALL_RADIUS;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /**
     * The amount of time to pause between frames
     */
    private static final int PAUSE_TIME = 9;

    /**
     * Speed of ball motion
     */
    private static final double BALL_SPEED = 3;

    /**
     * Colors of the bricks
     */
    private static final Color[] brickColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

    /* Keep track of the number of games played */
    private int countGames;

    /* GRect object representing the paddle */
    private GRect paddle;

    /* GOval object representing the ball*/
    private GOval ball;

    /* A double variable representing the horizontal velocity*/
    private double vx;

    /* A double variable representing the vertical velocity*/
    private double vy;

    /* An integer keeping track of the number of remaining brick*/
    private int brickCounter = NBRICK_ROWS * NBRICKS_PER_ROW;


    /* Create bricks and start the game*/
    public void run() {
        if (validateData()) {
            createBrickGrid();
            playGame();
        }
    }

    /**
     * It sets up the game environment,
     * starts the ball movement,
     * and handles the game logic.
     */
    private void playGame() {

        /* Create paddle in the center and  track the movement of the paddle */
        paddle = createPaddle((getWidth() - PADDLE_WIDTH) / 2,
                              getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET,
                                 PADDLE_WIDTH, PADDLE_HEIGHT);
        add(paddle);
        addMouseListeners();

        /*
         * Display the "Click to start" label and wait for the player
         *  to click before starting the ball movement
         */
        GLabel startLabel = createLabel("Click to start");
        add(startLabel);
        /* Wait for the player to click before starting the ball movement */
        waitForClick();
        remove(startLabel);

        ball = createBall((getWidth() - BALL_DIAMETER) / 2,
                          (getHeight() - BALL_DIAMETER) / 2,
                             BALL_DIAMETER, BALL_DIAMETER);
        add(ball);

        /*Set a random initial direction for the ball*/
        randomDirection();


        while (true) {
            /* Move the ball and check for collisions with walls and bricks*/
            moveBall();
            pause(PAUSE_TIME);

            // Check if all bricks are destroyed
            if (brickCounter == 0) {
                removeAll();
                add(createLabel("YOU WIN!"));
                break;
            }
        }
    }

    /**
     * This method sets a random initial direction for the ball.
     */
    private void randomDirection() {
        /*
         * Set the vertical velocity
         * to make the ball move downwards
         */
        vy = BALL_SPEED;

        /* Generate random values*/
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5))
            vx = -vx;
    }


    /**
     * This method creates a grid of bricks and adds them
     * The colors of the bricks are determined based on the row
     * they belong to. Every two rows a new color
     */
    private void createBrickGrid() {
        /* Calculate the width of each brick*/
        int BRICK_WIDTH = (getWidth() - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

        /* Calculate the x-coordinate for the first column of bricks to center the grid*/
        int firstColumnX = (getWidth() - (NBRICKS_PER_ROW * BRICK_WIDTH
                + (NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;

        /*Loop through each row and column to create and position the bricks in the grid */
        for (int row = 0; row < NBRICK_ROWS; row++) {
            for (int column = 0; column < NBRICKS_PER_ROW; column++) {

                /*Calculate the x and y coordinates for the current brick in the grid*/
                int x = firstColumnX + (BRICK_WIDTH + BRICK_SEP) * column;
                int y = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * row;

                /*Create a new brick*/
                GRect brick = createBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT);

                /* Set the color of the brick based on the row it belongs to*/
                brick.setColor(brickColors[row / 2 % brickColors.length]);
                add(brick);
            }
        }
    }

    /**
     * This method creates and configures
     * a new GRect representing a brick.
     *
     * @param x      int variable to specify the brick's x position
     * @param y      int variable to specify the brick's y position
     * @param width  The width of the brick
     * @param height The height of the brick
     * @return A new GRect object representing a brick
     * with the specified position and size
     */
    private GRect createBrick(int x, int y, int width, int height) {
        GRect brick = new GRect(x, y, width, height);
        brick.setFilled(true);
        return brick;
    }


    /**
     * Updating the position of the ball at each
     * animation step in the breakout game.
     * It also handles collisions with the walls, the paddle,
     * and the bricks, as well as checking for game-ending
     * conditions when the ball goes below the paddle.
     */
    private void moveBall() {
        /* Update the position of the ball on each animation */
        ball.move(vx, vy);

        /* Check for collisions with the walls */
        collisionWithWalls();

        /*Check for collisions with objects (paddle or bricks)*/
        GObject collider = getCollidingObject();
        if (collider != null) {
            if (collider == paddle) {
                collisionWithPaddle();
            } else {
                /* Remove the collided brick from the game*/
                collisionWithBrick(collider);
                remove(collider);
                brickCounter--;
            }
        }

        /* Check if the ball has gone below the paddle */
        if (ball.getY() + BALL_DIAMETER > getHeight()) {
            countGames++;

            /*Check if there are still turns left*/
            if (countGames < NTURNS) {

                /* If there are turns left restart the game */
                remove(paddle);
                remove(ball);
                playGame();
            } else {
                /*If there are no turns left, display "Game Over" message*/
                removeAll();
                add(createLabel("Game Over"));
            }
        }
    }

    private void collisionWithPaddle() {
        if (ball.getY() + BALL_DIAMETER >= paddle.getY() && vy > 0) {
            vy = -vy; // Bounce off the paddle (vertical hit)
        }
    }

    /**
     * This method handles the collision
     * between the ball and a brick.
     * It takes the colliding GObject (brick) as a parameter
     * and determines the collision direction.
     *
     * @param collider brick that the ball is colliding with.
     */
    private void collisionWithBrick(GObject collider) {
        /* Get the current position of the ball */

        double ballRight = ball.getX() + ball.getWidth();
        double ballBottom = ball.getY() + ball.getHeight();

        /* Get the position of the brick */
        double brickRight = collider.getX() + collider.getWidth();
        double brickBottom = collider.getY() + collider.getHeight();

        /*
         * Checking the coordinate relationship to determine the collision location
         * and changing the direction of the ball according to the collision location
         * */
        double overlapX = Math.min(ballRight, brickRight) - Math.max(ball.getX(), collider.getX());
        double overlapY = Math.min(ballBottom, brickBottom) - Math.max(ball.getY(), collider.getY());

       if (overlapX > overlapY) {
            /*Horizontal collision, reverse the vertical  movement*/
            vy = -vy;
        } else {
            /*Vertical collision, reverse the horizontal  movement */
            vx = -vx;
        }
    }

    /**
     * Checking for a ball hitting the wall and
     * changing the direction of the ball after that
     */
    private void collisionWithWalls() {
        /* Checking whether the ball collides with the upper border */
        if (ball.getY() < 0) {
            vy = -vy; // Changing the direction of movement down
        }

        /* Checking whether the ball collides with the left or right border */
        if (ball.getX() < 0 || ball.getX() > (getWidth() - BALL_DIAMETER)) {
            vx = -vx; // Changing the direction of movement to the side
        }

    }

    /**
     * This method creates and configures a new label with the given text.
     *
     * @param text The text to be displayed in the GLabel.
     * @return A new GLabel object with the specified text and font settings.
     */
    private GLabel createLabel(String text) {
        GLabel gameLabel = new GLabel(text);
        gameLabel.setFont("Arial-Bold-36"); // Set font

        /* Coordinates for  centering the label */
        double labelX = (getWidth() - gameLabel.getWidth()) / 2;
        double labelY = getHeight() / 2.0;

        /* Set the location of the existing label */
        gameLabel.setLocation(labelX, labelY);
        return gameLabel;
    }


    /**
     * This method retrieves the colliding GObject (if any) with the current ball position.
     * It checks the elements in the game space at four different locations around the ball:
     *
     * @return The GObject that the ball is colliding with, or null if no collision occurs.
     */
    private GObject getCollidingObject() {
        int ballX = (int) ball.getX();
        int ballY = (int) ball.getY();
        int ballDiameter = BALL_DIAMETER;

        // Проверяем каждую точку мяча на пересечение с объектом
        for (int x = ballX; x < ballX + ballDiameter; x++) {
            for (int y = ballY; y < ballY + ballDiameter; y++) {
                GObject collider = getElementAt(x, y);
                if (collider != null && collider != ball) {
                    return collider;
                }
            }
        }

        // Если столкновение не обнаружено, возвращаем null
        return null;
    }



    /**
     * Moves the paddle behind the mouse cursor.
     * The cursor is in the middle of the paddle
     *
     * @param mouseEvent the event to be processed
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        /* Cursor location */
        double mouseX = mouseEvent.getX();

        /* Checking that the paddle doesn't go beyond the screen to the left */
        if (mouseX < PADDLE_WIDTH / 2.0) {
            mouseX = PADDLE_WIDTH / 2.0;
        }

        /* Checking that the paddle doesn't go beyond the screen to the right */
        if (mouseX > getWidth() - PADDLE_WIDTH / 2.0) {
            mouseX = getWidth() - PADDLE_WIDTH / 2.0;
        }

        /* Setting a new position so that the paddle does not go beyond the screen */
        paddle.setLocation(mouseX - PADDLE_WIDTH / 2.0,
                           getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
    }

    /**
     * Create black rectangle paddle in special position
     *
     * @param x      int variable to specify the paddle's x position
     * @param y      int variable to specify the paddle's y position
     * @param width  int variable to specify the paddle's width
     * @param height int variable to specify the paddle's height
     * @return rectangle object that represent paddle
     */
    private GRect createPaddle(int x, int y, int width, int height) {
        GRect rect = new GRect(x, y, width, height);
        rect.setFilled(true);
        rect.setFillColor(Color.BLACK);
        return rect;
    }


    /**
     * Create black ball in special position
     *
     * @param x      int variable to specify the ball's x position
     * @param y      int variable to specify the ball's y position
     * @param width  int variable to specify the ball's width
     * @param height int variable to specify the ball's height
     * @return circle object
     */
    private GOval createBall(int x, int y, int width, int height) {
        GOval ball = new GOval(x, y, width, height);
        ball.setFilled(true);
        ball.setFillColor(Color.BLACK);
        return ball;
    }

    /**
     * Validating program constants compared with application window's size
     *
     * @return a message if constants are invalid, otherwise return true
     */
    private boolean validateData() {
        int EndRowY = BRICK_Y_OFFSET + (NBRICK_ROWS * (BRICK_HEIGHT + BRICK_SEP));
        if (EndRowY > (getHeight() - BALL_DIAMETER) / 2) {
            add(createLabel("Too many rows or height"));
            return false;
        }

        return true;
    }


}