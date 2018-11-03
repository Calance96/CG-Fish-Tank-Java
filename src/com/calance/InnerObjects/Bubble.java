package com.calance.InnerObjects;

import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;

public class Bubble extends JComponent {

    private Point location;
    private Image bubbleImage;
    private int bubbleSize;
    private int floatSpeed;
    private int floatDistance = 0;

    public Bubble() {
        int x = 150 - (int) (Math.random() * 50);
        bubbleSize = (int) (Math.random() * 40 + 10);
        setFloatSpeed();
        location = new Point(x, 0);
        bubbleImage = DataLoader.getInstance().getBubbleImage();
    }

    public Point getLocation() {
        return location;
    }

    /* Bubble floating speed is varied according to its size, small bubbles tend to float to the surface faster*/
    private void setFloatSpeed() {
        if (bubbleSize <= 20) {
            floatSpeed = 10;
        } else if (bubbleSize <= 30) {
            floatSpeed = 7;
        } else if (bubbleSize <= 40) {
            floatSpeed = 5;
        } else {
            floatSpeed = 3;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        location.y = getHeight() - 60 - floatDistance;
        g.drawImage(bubbleImage, location.x, location.y, bubbleSize, bubbleSize, this);
    }

    public void animateFloat() {
        floatDistance += floatSpeed;
    }
}
