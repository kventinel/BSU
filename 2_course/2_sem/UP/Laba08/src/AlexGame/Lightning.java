package AlexGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class Lightning {
    private BufferedImage lightning;
    private BufferedImage resizedLightning;
    private int x;
    private int count = 10;

    Lightning() throws IOException{
        lightning = ImageIO.read(new File("/home/alex/Documents/Git/BSU/2_course/2_sem/UP/Laba08/data/lightning.bmp"));
    }

    void resize(int newX, int newY) {
        x = newX;
        int width = lightning.getWidth() * newY / lightning.getHeight();
        resizedLightning = new BufferedImage(width, newY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedLightning.createGraphics();
        g.drawImage(lightning, 0, 0, width, newY, null);
        g.dispose();
        count = 0;
    }

    BufferedImage getImage() {
        return resizedLightning;
    }

    int getX() {
        return x;
    }

    boolean is() {
        ++count;
        return count < 10;
    }
}
