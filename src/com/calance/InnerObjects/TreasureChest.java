package com.calance.InnerObjects;

import javax.swing.*;
import java.awt.*;

public class TreasureChest extends JComponent {

    private Point location;
    private Image treasureImage;

    public TreasureChest() {
        treasureImage = new ImageIcon(getClass().getResource("/resources/deco/chest.gif")).getImage();
        location = new Point(80, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        location.y = getHeight() - 103;
        g.drawImage(treasureImage, location.x, location.y, 100, 100, this);
    }
}
