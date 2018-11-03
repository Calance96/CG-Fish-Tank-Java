package com.calance;

import com.calance.AquariumController.AquariumTracker;
import com.calance.InnerObjects.*;
import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.calance.Utility.AquariumConstants.MONSTER_HEIGHT;
import static com.calance.Utility.AquariumConstants.MONSTER_TYPE;
import static com.calance.Utility.ControlPanel.updateFishNumber;

@SuppressWarnings("unused")
public class Aquarium extends JPanel implements MouseListener {

    private BufferedImage sandImage;
    private List<Fish> fishList;
    private List<FishFood> foodList;
    private List<Plant> plantList;
    private List<Bubble> bubbleList;
    private Monster monster = null;
    private AquariumTracker tracker;

    private static boolean enableMonster = true;

    public Aquarium() {
        sandImage = DataLoader.getInstance().getSandImage();
        addChest();

        fishList = new ArrayList<>();
        foodList = new ArrayList<>();
        plantList = new ArrayList<>();
        bubbleList = new ArrayList<>();
        tracker = new AquariumTracker(this);
        addMouseListener(this);
        addPlant();

        setLayout(new OverlayLayout(this));
        setPreferredSize(new Dimension(960, 540));
        setSize(getPreferredSize());
        setOpaque(false);
        setVisible(true);

        int randomInitialNumberFish = (int) (Math.random() * 5);
        for (int i = 0; i < (5 + randomInitialNumberFish); ++i) {
            addFish();
        }
    }

    public boolean isEmpty() {
        return getFishCount() == 0;
    }

    /*
    * Getters - For the purpose of manipulating the fish tank contents
    * 1. Fish count
    * 2. Fish at index i in the fish list
    * 3. Food count
    * 4. Food at index i in the food list
    * 5. Bubble count
    * 6. Bubble at index i in the bubble list
    * 7. Monster
    * 8. Monster status [enabled/disabled]
    */
    public int getFishCount() {
        return fishList.size();
    }

    public int getFoodCount() {
        return foodList.size();
    }

    public int getBubbleCount() {
        return bubbleList.size();
    }

    public Fish getFishAtIndex(int index) {
        return fishList.get(index);
    }

    public FishFood getFoodAtIndex(int index) {
        return foodList.get(index);
    }

    public Bubble getBubbleAtIndex(int index) {
        return bubbleList.get(index);
    }

    public Monster getMonster() {
        return monster;
    }

    public static boolean isEnableMonster() {
        return enableMonster;
    }

    /*
    * Bubble manipulation
    * 1. Add bubble at fixed position range (left of the fish tank)
    * 2. Remove bubble that is at specified index position
    */
    public void addBubble() {
        Bubble bubble = new Bubble();
        add(bubble);
        bubbleList.add(bubble);
        refresh();
    }

    public void removeBubble(int index) {
        remove(bubbleList.remove(index));
        refresh();
    }

    /*
    * Fish manipulation
    * 1. Add fish at random position in the fish tank
    * 2. Remove fish according to the order that the fish is added to the fish tank
    * 3. Remove fish at specified index position (Used to remove dead fish / fish being eaten)
    */
    public void addFish() {
        Fish fish = new Fish();
        add(fish);
        fishList.add(fish);
        updateFishNumber(getFishCount());
        refresh();
    }

    public void removeFish() {
        if (!fishList.isEmpty()) {
            removeFish(0);
        }
    }

    public void removeFish(int index) {
        remove(fishList.remove(index));
        refresh();
        updateFishNumber(getFishCount());
    }

    /*
    * Monster manipulation (Only one exists at a time)
    * Purpose - Move horizontally in fish tank to eat fish that has not yet grown to the last stage
    * 1. Check the existence of monster in the tank (since only one can exist at any one time)
    * 2. Add monster to the fish tank. It appears either from the left or from the right.
    * 3. Remove monster once it goes beyond the fish tank frame
    * 4. Enable / disable monster
    */
    public boolean isSafe() {
        return monster == null;
    }

    public void addMonster() {
        int monsterX = 0;
        int monsterY = (int) (Math.random() * getHeight()) + 30;

        if (monsterY + MONSTER_HEIGHT > getHeight()) {
            monsterY = getHeight() - MONSTER_HEIGHT;
        }

        int appearLeftOrRight = (int) ((Math.random() * 10) % MONSTER_TYPE);
        switch (appearLeftOrRight) {
            case 0: {
                monsterX = 0;
                break;
            }case 1: {
                monsterX = getWidth();
                break;
            }
        }
        monster = new Monster(monsterX, monsterY);
        add(monster);
        refresh();
    }

    public void removeMonster() {
        remove(monster);
        monster = null;
        refresh();
    }

    public static void toggleMonster() {
        enableMonster = !enableMonster;
    }

   /*
   * Food manipulation:
   * 1. Randomly drop a food from the top of the fish tank
   * 2. General add food function
   * 3. Remove food at specified index position (Eaten by fish / goes beyond the fish tank frame)
   * 4. Click any point in the fish tank to drop a food at cursor position
   */
    public void addFoodRandom() {
        int foodX = (int) (Math.random() * getWidth());
        FishFood food = new FishFood(foodX, 0);
        addFood(food);
    }

    private void addFood(FishFood food) {
        add(food);
        foodList.add(food);
        refresh();
    }

    public void removeFood(int index) {
        remove(foodList.remove(index));
        refresh();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int foodX = e.getX();
        int foodY = e.getY();
        FishFood food = new FishFood(foodX, foodY);
        addFood(food);
    }


    /*
    * @Initialization function
    * Add 4 plants at random bottom position of the fish tank
    */
    private void addPlant() {
        int startX = 150;

        for (int i = 1; i <= 3; ++i) {
            int interval = (int) (Math.random() * 100 + 100);
            Plant plant = new Plant(startX + interval);
            startX += interval;
            add(plant);
        }
    }

    /* Add treasure chest function */
    private void addChest() {
        TreasureChest treasureChest = new TreasureChest();
        add(treasureChest);
    }

    /* Refresh the fish tank on any changes in the fish tank */
    private void refresh() {
        revalidate();
        repaint();
    }

    /* Get the running status of the fish tank */
    public boolean isRunning() {
        return tracker.getRunningStatus();
    }

    /* Pause the fish tank activity, i.e. Freeze all object movement in the fish tank */
    public void pause() {
        tracker.stop();
    }

    /* Resume the fish tank activity */
    public void resume() {
        tracker.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Draw water level lines */
        Color waterColor = new Color(90,188, 216, 100);
        g.setColor(waterColor);
        g.drawLine(0, 30, 40, 50);
        g.drawLine(getWidth()-40, 50, getWidth(), 30);
        g.drawLine(40, 50, getWidth()-40, 50);

        /* Fish tank bottom texture - bottom left, bottom right, top right, top left */
        int[] bottomFaceX = {5, getWidth()-5, getWidth()-75, 75};
        int[] bottomFaceY = {getHeight()-5, getHeight()-5, getHeight()-40, getHeight()-40};

        /* Translucent color to draw frame */
        Color frameColor = new Color(0.0f, 0.2f, 0.3f, 0.5f);
        g.setColor(frameColor);

        /* Thin lines for the back surface of the fish tank in the order of: top -> left -> bottom -> right */
        g2d.setStroke(new BasicStroke(3));
        g.drawLine(40, 20, getWidth()-40, 20);
        g.drawLine(40, 20, 40, getHeight()-20);
        g.drawLine(40, getHeight()-20, getWidth()-40, getHeight()-20);
        g.drawLine(getWidth()-40, 20, getWidth()-40, getHeight()-20);

        /* Front frame line in the order of: top -> left -> bottom -> right */
        g.fillRect(0, 0, getWidth(), 5);
        g.fillRect(0,0, 5, getHeight());
        g.fillRect(0, getHeight()-5, getWidth(), 5);
        g.fillRect(getWidth()-5, 0, 5, getHeight());

        /* Draw wedges forming the top side edges */
        g.drawLine(0, 0, 40, 20);
        g.drawLine(getWidth()-40, 20, getWidth(), 0);

        /* Add sand */
        if (sandImage != null) {
            Polygon sandArea = new Polygon(bottomFaceX, bottomFaceY, 4);
            TexturePaint texturePaint = new TexturePaint(sandImage, sandArea.getBounds2D());
            g2d.setPaint(texturePaint);
            g2d.fillPolygon(sandArea);
        }

    }

    /* Unused function - must be implemented due to implementation of MouseListener interface */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
