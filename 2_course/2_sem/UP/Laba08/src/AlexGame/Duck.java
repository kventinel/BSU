package AlexGame;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Duck {
    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private boolean die = false;
    private boolean to_left;
    static BufferedImage leftDuck;
    static BufferedImage rightDuck;
    static BufferedImage dieLeftDuck;
    static BufferedImage dieRightDuck;

    Duck(int maxSpeed) {
        x = 0;
        Random duckRandom = new Random();
        y = duckRandom.nextInt(400);
        speedX = duckRandom.nextInt(maxSpeed) + 1;
        to_left = duckRandom.nextBoolean();
        if (!to_left) {
            x = 1650;
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void move() {
        if (to_left) {
            x += speedX;
        } else {
            x -= speedX;
        }
        y += speedY;
    }

    boolean left() {
        return to_left;
    }

    boolean is_die() {
        return die;
    }

    BufferedImage getImage() {
        if (to_left) {
            if (die) {
                return dieLeftDuck;
            } else {
                return leftDuck;
            }
        } else {
            if (die) {
                return dieRightDuck;
            } else {
                return rightDuck;
            }
        }
    }

    void kill() {
        die = true;
        speedX = 0;
        speedY = 5;
    }
}
