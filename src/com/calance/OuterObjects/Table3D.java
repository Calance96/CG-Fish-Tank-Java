package com.calance.OuterObjects;

import com.calance.Aquarium;
import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Table3D extends JPanel {

    private Aquarium aquarium; // Need to know the fish tank size to adapt the size accordingly
    private int width;
    private int height;
    private BufferedImage tableTexture;

    public Table3D(Aquarium aquarium) {
        this.aquarium = aquarium;
        width = aquarium.getPreferredSize().width + 100;
        height = width / 3;
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);
        setSize(width, height);
        tableTexture = DataLoader.getInstance().getTableTextureImg();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = aquarium.getPreferredSize().width + 100;
        height = width / 3;
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);

        g.setColor(Color.gray);

        int[] tableTopX = {0, getWidth(), getWidth()-80, 80};
        int[] tableTopY = {40, 40, 0, 0};

        /* Front side of the table */
        fillRect(0, 40, getWidth(), 40, g);

        /* Front left leg */
        fillRect(0, 80, 40, 60, g);
        /* Front left leg side piece */
        int[] legLeftSideX = {40, 40, 50, 50};
        int[] legLeftSideY = {80, 140, 133, 80};
        fillPolygon(legLeftSideX, legLeftSideY, g);

        /* Front right leg */
        fillRect(getWidth()-40,80,40,60, g);
        /* Front right leg side piece*/
        int[] legRightSideX = {getWidth()-40,getWidth()-40,getWidth()-50,getWidth()-50};
        int[] legRightSideY = {80,140,133,80};
        fillPolygon(legRightSideX,legRightSideY,g);

        /* Back left table leg */
        fillRect(80,80,35,20, g);
        /* Back left leg side piece */
        int[] backLegLeftSideX = {115, 115, 122, 122};
        int[] backLegLeftSideY = {80, 100, 93 ,80};
        fillPolygon(backLegLeftSideX, backLegLeftSideY, g);

        /* Back right table leg */
        fillRect(getWidth()-120,80,35,20, g);
        /* Back right leg side piece */
        int[] backLegRightSideX = {getWidth()-120, getWidth()-120, getWidth()-127, getWidth()-127};
        int[] backLegRightSideY = {80, 100, 93, 80};
        fillPolygon(backLegRightSideX, backLegRightSideY, g);

        /* Top surface */
        fillPolygon(tableTopX,tableTopY,g);

        g.setColor(Color.black);

        /* Lines for table side */
        g.drawRect(0,40, getWidth(),40);

        /* Lines for table legs */
        g.drawRect(0,80,40,60); // Front left
        g.drawRect(getWidth()-40,80,40,60); // Front right
        g.drawPolygon(legLeftSideX, legLeftSideY,4); // Front left side piece
        g.drawPolygon(legRightSideX, legRightSideY,4); // Front right side piece
        g.drawRect(80, 80, 35, 20); // Back left
        g.drawRect(getWidth()-120, 80, 35 ,20); // Back right
        g.drawPolygon(backLegLeftSideX, backLegLeftSideY, 4); // Back left side piece
        g.drawPolygon(backLegRightSideX, backLegRightSideY, 4); //Back right side piece

    }

    private void fillRect(int x, int y, int width, int height, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Polygon fillArea = generatePolygon(x, y, width, height);
        g2d.setPaint(new TexturePaint(tableTexture, fillArea.getBounds2D()));
        g2d.fillPolygon(fillArea);
    }

    private Polygon generatePolygon(int x, int y, int width, int height) {
        int[] pointX = {x, x, x+width, x+width};
        int[] pointY = {y, y+height, y+height, y};
        return new Polygon(pointX, pointY, 4);
    }

    private void fillPolygon(int[] coordinateX, int[] coordinateY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new TexturePaint(tableTexture, new Polygon(coordinateX, coordinateY, coordinateX.length).getBounds2D()));
        g2d.fillPolygon(coordinateX, coordinateY, coordinateX.length);
    }
}
