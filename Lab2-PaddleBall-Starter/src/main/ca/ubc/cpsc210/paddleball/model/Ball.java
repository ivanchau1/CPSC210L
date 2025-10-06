package ca.ubc.cpsc210.paddleball.model;

import java.awt.Color;
import java.awt.Rectangle;

/*
 * Represents a ball.
 */
public class Ball {
    public static final int SIZE = 20; // must be even integer
    public static final Color COLOR = new Color(10, 50, 188);

    private double posX;
    private double posY;
    private double deltaX;
    private double deltaY;

    // Constructs an ball
    // EFFECTS: ball is positioned at coordinates (x, y) with velocity (2.0, 2.0)
    public Ball(int x, int y) {
        this.posX = x;
        this.posY = y;
        deltaX = 2.0;
        deltaY = 2.0;
    }

    public int getPosX() {
        return (int) posX;
    }

    public int getPosY() {
        return (int) posY;
    }

    public double getDx() {
        return deltaX;
    }

    public double getDy() {
        return deltaY;
    }

    // Bounce ball off paddle
    // MODIFIES: this
    // EFFECTS: vertical component of ball's velocity is reversed
    public void bounceOffPaddle() {
        deltaY *= -1;
    }

    // Updates ball on clock tick
    // MODIFIES: this
    // EFFECTS: ball is moved (dx, dy) units
    public void move() {
        posX = posX + deltaX;
        posY = posY + deltaY;

        ballBoundaryCollision();
    }

    // Determines if this ball has collided with the paddle
    // EFFECTS: returns true if this ball has collided with paddle p,
    // false otherwise
    public boolean collided(Paddle p) {
        Rectangle ballBoundingRectangle = new Rectangle(getPosX() - SIZE / 2, getPosY() - SIZE / 2, SIZE, SIZE);
        Rectangle paddleBoundingRectangle = new Rectangle(p.getPosX() - Paddle.WIDTH / 2,
                Paddle.Y_POS - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
        return ballBoundingRectangle.intersects(paddleBoundingRectangle);
    }

    // Constrains ball so that it bounces off top and vertical walls
    // MODIFIES: this
    // EFFECTS: ball is constrained to bounce off top and vertical walls
    private void ballBoundaryCollision() {
        if (getPosX() - SIZE / 2 < 0) {
            posX = SIZE / 2;
            deltaX *= -1;
        } else if (getPosX() + SIZE / 2 > PaddleBallGame.WIDTH) {
            posX = PaddleBallGame.WIDTH - SIZE / 2;
            deltaX *= -1;
        } else if (getPosY() - SIZE / 2 < 0) {
            posY = SIZE / 2;
            deltaY *= -1;
        }
    }
}
