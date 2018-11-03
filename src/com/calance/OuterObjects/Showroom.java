package com.calance.OuterObjects;

import com.calance.Aquarium;
import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Showroom extends JPanel {

    private Aquarium aquarium;
    private Table3D table3D;
    private BufferedImage wallpaper;
    private BufferedImage floorTexture;
    private JPanel waterLayer;

    public Showroom(Aquarium aquarium, Table3D table3D) {
        this.aquarium = aquarium;
        this.table3D = table3D;
        wallpaper = DataLoader.getInstance().getWallpaper();
        floorTexture = DataLoader.getInstance().getFloorImage();
        setLayout(null);
        setupScene();
        add(aquarium);
        add(table3D);
    }

    public void setupScene() {
        setPreferredSize(new Dimension(aquarium.getPreferredSize().width + 300, aquarium.getPreferredSize().height + 200));
        aquarium.setLocation(150, 50);
        table3D.setLocation(100, 20+aquarium.getHeight());

        /* Adding a layer of water over the aquarium JPanel to simulate the real world sensation*/
        waterLayer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Color waterColor = new Color(90,188, 216, 60);
                g.setColor(waterColor);
                g.fillRect(0, 0, Showroom.this.getWidth(), Showroom.this.getHeight());
            }
        };
        waterLayer.setSize(aquarium.getWidth() - 10, aquarium.getHeight()-35);
        waterLayer.setOpaque(false);
        add(waterLayer);
        waterLayer.setLocation(155, 80);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int separatorYLoc = table3D.getLocation().y +  85;

        /*
         * Draw floor:
         * Coordinates order: Top left, bottom left up, bottom left, bottom right, bottom right up, top right
         */
        int[] floorX = {80, 0, 0, getWidth(), getWidth(), getWidth()-80};
        int[] floorY = {separatorYLoc, getHeight()-35, getHeight(), getHeight(), getHeight()-35, separatorYLoc};

        /* Floor color */
        fillImage(floorX, floorY, floorTexture, g);
        /* Floor outline */
        g.drawPolygon(floorX, floorY, 6);

        /*
         * Draw wall:
         * Coordinates order: Top left, bottom left, bottom right, top right
         */
        int[] wallCenterX = {80, 80, getWidth()-80, getWidth()-80};
        int[] wallCenterY = {0, separatorYLoc, separatorYLoc, 0};
        int[] wallLeftX = {0, 0, 80, 80};
        int[] wallLeftY = {0, getHeight()-35, separatorYLoc, 0};
        int[] wallRightX = {getWidth()-80, getWidth()-80, getWidth(), getWidth()};
        int[] wallRightY = {0, separatorYLoc, getHeight()-35, 0};

        fillImage(wallCenterX, wallCenterY, wallpaper, g);
        fillImage(wallLeftX, wallLeftY, wallpaper, g);
        fillImage(wallRightX, wallRightY, wallpaper, g);
        
        g.setColor(Color.BLACK);
        g.drawPolygon(wallCenterX, wallCenterY,4);
        g.drawPolygon(wallLeftX, wallLeftY, 4);
        g.drawPolygon(wallRightX, wallRightY, 4);
    }

    private void fillImage(int[] coordinateX, int[] coordinateY, BufferedImage image, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new TexturePaint(image, new Polygon(coordinateX, coordinateY, coordinateX.length).getBounds2D()));
        g2d.fillPolygon(coordinateX, coordinateY, coordinateX.length);
    }
}
