package ca.ubc.cpsc210.paddleball.model;

import java.awt.event.KeyEvent;
import java.util.Random;

/*
 * Represents a paddle ball game.
 */
public class PaddleBallGame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final Random RANDOM = new Random();

    private Ball ballObject;
    private Paddle paddleObject;
    private boolean stop;

    // Constructs a Paddle Ball Game
    // EFFECTS: creates ball at random location at top of screen
    public PaddleBallGame() {
        setUp();
    }

    public Paddle getPaddle() {
        return paddleObject;
    }

    public Ball getBall() {
        return ballObject;
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    public boolean isOver() {
        return stop;
    }

    // Updates the game on clock tick
    // MODIFIES: this
    // EFFECTS: updates ball, paddle and game over status
    public void update() {
        ballObject.move();
        paddleObject.move();

        checkHitSomething();
        isGameOver();
    }

    // Responds to key press codes
    // MODIFIES: this
    // EFFECTS: turns paddle and resets game (if game over) in response to
    // given key pressed code
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_R && stop) {
            setUp();
        } else if (keyCode == KeyEvent.VK_X) {
            System.exit(0);
        } else {
            paddleDirection(keyCode);
        }
    }

    // Sets / resets the game
    // MODIFIES: this
    // EFFECTS: initializes game with paddle in centre of screen and ball
    // at random horizontal coordinate at top of screen
    private void setUp() {
        ballObject = new Ball(RANDOM.nextInt(PaddleBallGame.WIDTH), Ball.SIZE / 2);
        paddleObject = new Paddle(WIDTH / 2);
        stop = false;
    }

    // Controls the paddle
    // MODIFIES: this
    // EFFECTS: changes direction of paddle in response to key code
    private void paddleDirection(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            paddleObject.left();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            paddleObject.right();
        }
    }

    // Checks for collision between ball and paddle
    // MODIFIES: this
    // EFFECTS: bounces ball if it collides with paddle
    private void checkHitSomething() {
        if (ballObject.collided(paddleObject)) {
            ballObject.bounceOffPaddle();
        }
    }

    // Is game over? (Has ball hit ground?)
    // MODIFIES: this
    // EFFECTS: if ball has hit ground, game is marked as over
    private void isGameOver() {
        if (ballObject.getPosY() > HEIGHT) {
            stop = true;
        }
    }
}
