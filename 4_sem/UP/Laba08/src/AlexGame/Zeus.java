package AlexGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Zeus {
    private BufferedImage zeus;
    private int x;
    private int y;

    Zeus(int width, int height) throws IOException {
        zeus = ImageIO.read(new File("data/zeus.bmp"));

        x = (width - zeus.getWidth()) / 2;
        y = (height - zeus.getHeight());
    }

    BufferedImage getImage() {
        return zeus;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int newX) {
        x = newX;
    }
}
