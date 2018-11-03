package com.calance.InnerObjects;

import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;

import static com.calance.Utility.AquariumConstants.MONSTER_HEIGHT;
import static com.calance.Utility.AquariumConstants.MONSTER_TYPE;
import static com.calance.Utility.AquariumConstants.MONSTER_WIDTH;

public class Monster extends JComponent {

    private Image monsterImage;
    private Point location;
    private int velX = 3;

    public Monster(int x, int y) {
        location = new Point(x, y);
        int type = (int) (Math.random() * MONSTER_TYPE);
        if (location.x != 0) {
            velX = -velX;
            monsterImage = DataLoader.getInstance().getMonsterLeftImage(type);
        } else {
            monsterImage = DataLoader.getInstance().getMonsterRightImage(type);
        }
    }

    public void moveForward() {
        location.x += velX;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(monsterImage, location.x, location.y, MONSTER_WIDTH, MONSTER_HEIGHT, null);
    }
}
