package AlexGame;

import java.util.Random;

/**
 * Created by Rak Alexey on 5/12/17.
 */

public class Duck {
    private int x;
    private int y;
    private int speed;

    Duck() {
        x = 0;
        Random duckRandom = new Random();
        y = duckRandom.nextInt(400);
        speed = duckRandom.nextInt(5) + 1;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void move() {
        x += speed;
    }
}
