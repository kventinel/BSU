package AlexGame;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Rak Alexey on 5/12/17.
 */

class DuckObservable {
    private ArrayList<Duck> list = new ArrayList<Duck>();
    private int level = 1;
    private int next_level = 2;
    private double count_shoots = 0;
    private double loose_shoots = 0;
    private double count_hits = 0;
    private int lost_ducks = 0;
    private int width;
    private int height;

    DuckObservable(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
    }

    private void addDuck(Duck duck) {
        list.add(duck);
    }

    void move() {
        for (int i = list.size() - 1; i >= 0; --i) {
            list.get(i).move();
            if (list.get(i).getX() > width - list.get(i).getImage().getWidth() || list.get(i).getX() < 0) {
                ++lost_ducks;
                list.remove(i);
            } else if (list.get(i).getY() > height - 300) {
                list.remove(i);
            }
        }

        while (list.size() < 5) {
            addDuck(new Duck(level, width, height));
        }
    }

    void kill(int x, int y) {
        ++count_shoots;
        int count_kill = 0;
        for (int i = list.size() - 1; i >= 0; --i) {
            if (x > list.get(i).getX() && x < list.get(i).getX() + list.get(i).getImage().getWidth() && !list.get(i).is_die() &&
                    y > list.get(i).getY() && y < list.get(i).getY() + list.get(i).getImage().getHeight()) {
                list.get(i).kill();
                ++count_hits;
                ++count_kill;
                if (count_hits >= next_level) {
                    next_level *= 2;
                    ++level;
                }
            }
        }
        if (count_kill == 0) {
            ++loose_shoots;
        }
        music(count_kill);
    }

    void getImages(Graphics g, JPanel panel) {
        for (Duck duck : list) {
            g.drawImage(duck.getImage(), duck.getX(), duck.getY(), panel);
        }
    }

    double getHits() {
        return count_hits;
    }

    double getShoots() {
        return count_shoots;
    }

    double getAccuracy() {
        return count_shoots < 0.9 ? 0 : (count_shoots - loose_shoots) / count_shoots;
    }

    int getLevel() {
        return level;
    }

    int getLostDucks() {
        return lost_ducks;
    }

    private void music(int count_kill) {
        String killFile;
        if (count_hits - count_kill == 0) {
            killFile = "data/first_blood.wav";
        } else {
            switch (count_kill) {
                case 2:
                    killFile = "data/kill2.wav";
                    break;
                case 3:
                    killFile = "data/kill3.wav";
                    break;
                default:
                    killFile = "data/kill.wav";
            }
        }
        try {
            InputStream in = new FileInputStream(killFile);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            System.out.println("bad");
        }
    }
}
