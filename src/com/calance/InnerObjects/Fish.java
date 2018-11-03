package com.calance.InnerObjects;

import com.calance.ObjectAttributes.FishGrowth;
import com.calance.ObjectAttributes.FishHealth;
import com.calance.Utility.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Fish extends JComponent {

    private Image swimRightImage;
    private Image swimLeftImage;
    private int drawWidth;
    private int drawHeight;

    private Rectangle foodSenseArea = null;
    private boolean hasTargetFood = false;
    private int targetFoodIndex = 0;

    private JLabel dragLabel = new JLabel();
    private FishHealth fishHealth;
    private FishGrowth fishGrowth;

    private int velX = 3, velY = 3;
    private static boolean healthControl = false;
    private static boolean fishSense = true;
    private Point location = null;

    public Fish() {
        fishHealth = new FishHealth();
        fishGrowth = new FishGrowth();

        int direction = (int) (Math.random() * 10);
        if (direction > 5) {
            velX = -velX;
        }

        setupImage();
        setupMouseListener();
    }

    public boolean locationSet() {
        return location != null;
    }

    public Point getLocation() {
        return location;

    }

    public int getDrawWidth() {
        return drawWidth;
    }

    public int getDrawHeight() {
        return drawHeight;
    }

    public int getLevel() {
        return fishGrowth.getLevel();
    }

    public boolean isAlive() {
        return fishHealth.getCurrentHealth() > 0;
    }

    /* Toggle health tracker, i.e. fishes should utilize HP or vice versa */
    public static void toggleHealth() {
        healthControl = !healthControl;
    }

    /* For checking of health tracker status */
    public static boolean getHealthControlStatus() {
        return healthControl;
    }

    /* Toggle fish sense, i.e. fish should hunt for food or not */
    public static void toggleFishSense() {
        fishSense = !fishSense;
    }

    public static boolean getFishSenseStatus() {
        return fishSense;
    }

    public void swim() {
        if (locationSet()) {
            changeVelocity();
            location.x += velX;
            location.y += velY;
            checkBoundary();
            if (healthControl) {
                fishHealth.decrement();
            }
        }
    }

    private void setupImage() {
        int growthStage = fishGrowth.getLevel();
        swimLeftImage = DataLoader.getInstance().getSwimLeftImage(growthStage);
        swimRightImage = DataLoader.getInstance().getSwimRightImage(growthStage);
        drawWidth = DataLoader.getInstance().getImageWidth(growthStage);
        drawHeight = DataLoader.getInstance().getImageHeight(growthStage);
    }

    /*
     * For two cases, the fish needs to turn around:
     * a. At the most left edge of the fish tank
     * b. At the most right edge of the fish tank
     *
     * The addition of drawSize is to take care of the case where fish can go beyond the right edge
     *
     * For two cases, the fish needs to adjust it vertical swimming direction:
     * a. At the top of the fish tank
     * b. At the bottom of the fish tank
     *
     * The addition of drawSize is to take care of the case where fish can go beyond the bottom.
     *
     * Note: The subtraction of 5 is due to the frame of the fish tank
     */
    private void checkBoundary() {
        if ((location.x < 0 && swimLeft()) || (location.x + drawWidth > getWidth() - 5 && !swimLeft()) ) {
            velX = -velX;
        }

        /* 30 is the distance of water surface from the fish tank top */
        if ((location.y < 30 && swimUp()) || (location.y + drawHeight > getHeight() - 5 && !swimUp())) {
            velY = -velY;
        }
    }

    /* Randomize the movement of the fish so that it can swim more naturally */
    private void changeVelocity() {
        if (Math.random() < 0.01 || Math.abs(velY) == 4) {
            switch((int) (Math.random() * 1000 % 5)) {
                case 0: {
                    velX = 2;
                    velY = 2;
                    break;
                }case 1: {
                    velX = 3;
                    velY = 0;
                    break;
                }case 2: {
                    velX = 3;
                    velY = 3;
                    break;
                }case 3: {
                    velX = -3;
                    velY = 0;
                    break;
                }case 4: {
                    velX = -3;
                    velY = -3;
                    break;
                }
            }
        }
    }

    /* Fish eat the food passed in as parameter*/
    public void feed(FishFood food) {
        fishHealth.increment(food.getFeedValue());
        if (fishGrowth.increment()) {
            setupImage();
        }
        drawWidth = fishGrowth.getCurrentWidth();
        drawHeight = fishGrowth.getCurrentHeight();
    }

    /* Return the rectangle wrapping the fish image */
    public Rectangle getLocationArea() {
        return new Rectangle(location.x, location.y, drawWidth, drawHeight);
    }

    /* Return the coverage that the fish can detect food */
    public Rectangle getFoodSenseArea() {
        return foodSenseArea;
    }

    /* Check if the current fish has a food to pursuit */
    public boolean hasTarget() {
        return hasTargetFood;
    }

    /* Set food target */
    public void setFoodTarget(int foodIndex) {
        targetFoodIndex = foodIndex;
        hasTargetFood = true;
    }

    /* Return target food index */
    public int getTargetFoodIndex() {
        return targetFoodIndex;
    }

    /* Give up pursuit food */
    public void removeTarget() {
        hasTargetFood = false;
    }

    /* Pursuit the food */
    public void huntFood(FishFood food) {
        if (!fishSense) {
            if (hasTargetFood)
                removeTarget();
            return;
        }

        checkBoundary();

        /*
        * Food is:
        * 1. Not at the left or right of the fish
        * 2. To the left of the fish
        * 3. To the right of the fish
        */
        if (food.getLocation().x >= location.x && food.getLocation().x <= location.x + drawWidth) {
            location.x += velX;
        } else if (food.getLocation().x < location.x) {
            if (!swimLeft())
                velX = -velX;
        } else {
            if (swimLeft())
                velX = -velX;
        }

        /*
        * Food is:
        * 1. Above the fish
        * 2. Below the fish
        * 3. Same line as the fish
        */
        if (food.getLocation().y < location.y) {
            if (!swimUp())
                velY = -3;
            location.y += velY;
        } else if (food.getLocation().y > location.y){
            if (swimUp())
                velY = 3;
            location.y += velY;
        } else {
            // Do nothing
        }

        location.x += velX;
    }

    /* True if fish is swimming towards left | False if fish is swimming towards right */
    private boolean swimLeft() {
        return velX < 0;
    }

    /* True if fish is swimming upwards | False if fish is swimming downwards */
    private boolean swimUp() {
        return velY < 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Initializing the location of fish - Cannot be done in constructor as the function getWidth() and getHeight() would return 0 */
        if (!locationSet()) {
            int fishX = (int) (Math.random() * getWidth());
            int fishY = (int) (Math.random() * getHeight());
            location = new Point(fishX, fishY);
        }

        if (swimLeft()) {
            g.drawImage(swimLeftImage, location.x, location.y, drawWidth, drawHeight, this);
        } else {
            g.drawImage(swimRightImage, location.x, location.y, drawWidth, drawHeight, this);
        }


        /* If fish sense food is enabled, then only construct the food sense area */
        if (fishSense) {
            foodSenseArea = new Rectangle(location.x - (drawWidth * 2), location.y - (drawHeight * 2), drawWidth * 5, drawHeight * 5);
        }

        /* If fish life is enabled, then display it */
        if (healthControl) {
            if (fishHealth.getCurrentHealth() > 30) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            g.fillRect(location.x, location.y + drawHeight + 5, (int) (fishHealth.getCurrentHealth() / 100 * drawWidth), 5);
        }

        dragLabel.setBounds(location.x, location.y, drawWidth, drawHeight);
    }

    private void setupMouseListener() {
        dragLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                /* Adjust the fish location while dragging so that cursor falls on it */
                location.x = e.getXOnScreen() - getParent().getLocationOnScreen().x - (int) (drawWidth/2.0);
                location.y = e.getYOnScreen() - getParent().getLocationOnScreen().y - (int) (drawHeight/2.0);
            }
        });
        add(dragLabel);
    }
}
