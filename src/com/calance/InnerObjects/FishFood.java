package com.calance.InnerObjects;

import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static com.calance.Utility.AquariumConstants.FOOD_RADIUS;

public class FishFood extends JComponent {

    private Point location;
    private BufferedImage foodImg;
    private int feedValue;

    public FishFood(int x, int y) {
        location = new Point(x, y);
        foodImg = DataLoader.getInstance().getFoodImage();
        feedValue = (int) (Math.random() * 15 + 15);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (foodImg != null) {
            g.drawImage(foodImg, location.x, location.y, FOOD_RADIUS, FOOD_RADIUS, this);
        }
    }

    public void sink() {
        location.y += 2;
    }

    public Point getLocation() {
        return location;
    }

    public Rectangle getLocationArea() {
        return new Rectangle(location.x, location.y, FOOD_RADIUS, FOOD_RADIUS);
    }

    public int getFeedValue() {
        return feedValue;
    }

}
