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

    Zeus() throws IOException {
        x = 800;
        y = 500;

        zeus = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/zeus.bmp"));
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
