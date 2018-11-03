package com.calance.InnerObjects;

import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;

import static com.calance.Utility.AquariumConstants.PLANT_HEIGHT;

public class Plant extends JComponent {

    private Point location;
    private Image plantImage;
    private int offset = -1;

    public Plant(int x) {
        location = new Point(x, 0);
        plantImage = DataLoader.getInstance().getPlantImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (offset == -1) {
            offset = (int) (Math.random() * 10);
        }
        /* Continuously update to adapt to the viewport dimension */
        location.y = getHeight() - PLANT_HEIGHT - offset - 5;
        g.drawImage(plantImage, location.x, location.y, 200, PLANT_HEIGHT, this);
    }

}
