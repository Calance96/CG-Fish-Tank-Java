package com.calance.AquariumController;

import com.calance.Aquarium;
import com.calance.InnerObjects.Bubble;
import com.calance.InnerObjects.Fish;
import com.calance.InnerObjects.FishFood;
import com.calance.InnerObjects.Monster;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.calance.Utility.AquariumConstants.FOOD_RADIUS;
import static com.calance.Utility.AquariumConstants.MONSTER_WIDTH;
import static com.calance.Utility.ConsoleMessageColor.ANSI_RED;

public class AquariumTracker implements Runnable, ActionListener {

    private Aquarium aquarium;
    private BackgroundMusic bgm;
    private boolean isRunning;
    private Monster monster;
    private Timer monsterTimer = new Timer(5000, this);
    private Timer bubbleTimer = new Timer(750, this);
    private Thread trackerThread;

    public AquariumTracker(Aquarium aquarium) {
        this.aquarium = aquarium;
        aquarium.addBubble();
        bgm = new BackgroundMusic();
        start();
    }

    /* Control the aquarium tracker thread, background music, aquarium timer */
    public void stop() {
        isRunning = false;
        try {
            trackerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        trackerThread = null;
        bgm.pauseMusic();
        bubbleTimer.stop();
        monsterTimer.stop();
    }

    public void start() {
        isRunning = true;
        trackerThread = new Thread(this);
        trackerThread.start();
        bgm.startMusic();
        bubbleTimer.start();
        monsterTimer.start();
    }

    /* Return the running status for manipulation of aquarium activity */
    public boolean getRunningStatus() {
        return isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {
            animateBubble();
            animateFood();
            trackerMainActivity();

            /* Update monster position */
            if (monster != null) {
                monster.moveForward();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(ANSI_RED + "Error in AquariumTracker");
            }
            aquarium.repaint();
        }
    }

    /* Animate bubble and remove invalid bubble (i.e. move beyond the fish tank frame) */
    private void animateBubble() {
        for (int i = 0; i < aquarium.getBubbleCount(); i++) {
            Bubble bubble = aquarium.getBubbleAtIndex(i);
            bubble.animateFloat();

            /* 30 is the distance of water from the fish tank top */
            if (bubble.getLocation().y < 30) {
                aquarium.removeBubble(i);
            }
        }
    }

    /*
     * Check for food sank at the bottom of the fish tank. If found, remove it immediately
     *  This cannot be placed in the third loop as the food won't sink if there is no fish
     */
    private void animateFood() {
        FishFood food;
        for (int foodIndex = 0; foodIndex < aquarium.getFoodCount(); ++foodIndex) {
            food = aquarium.getFoodAtIndex(foodIndex);
            food.sink();
            if (food.getLocation().y + FOOD_RADIUS >= aquarium.getHeight()) {
                aquarium.removeFood(foodIndex);
                if (Fish.getFishSenseStatus()) {
                    allFishRemoveTarget();
                }
            }
        }
    }

    /* The whole logic of the objects' movement in the fish tank */
    private void trackerMainActivity() {
        Fish fish;
        FishFood food;

        /* Animate alive fish and remove dead fish */
        if (!aquarium.isEmpty()) {
            for (int fishIndex = 0; fishIndex < aquarium.getFishCount(); ++fishIndex) {
                fish = aquarium.getFishAtIndex(fishIndex);
                if (fish.isAlive()) {
                    if (fish.hasTarget()) {
                        fish.huntFood(aquarium.getFoodAtIndex(fish.getTargetFoodIndex()));
                    } else {
                        fish.swim();

                        if (Fish.getFishSenseStatus()) {
                            for (int foodIndex = 0; foodIndex < aquarium.getFoodCount(); ++foodIndex) {
                                food = aquarium.getFoodAtIndex(foodIndex);

                                if (fish.locationSet() && fish.getFoodSenseArea().contains(food.getLocationArea())) {
                                    fish.setFoodTarget(foodIndex);
                                }
                            }
                        }
                    }
                } else {
                    aquarium.removeFish(fishIndex);
                    continue;
                }

                /*
                 * Check for existence of monster (i.e. Spongebob and Patrick)
                 * 1. If it exists in the fish tank, see if it should be removed, i.e. it has moved beyond the fish tank frame
                 * 2. Whenever it encounters a fish, the fish shall be eaten (except the last stage of fish)
                 */
                if (monster != null) {
                    int monsterX = monster.getLocation().x;
                    int monsterY = monster.getLocation().y;

                    if (monsterX > aquarium.getWidth() || monsterX < 0) {
                        aquarium.removeMonster();
                        monster = null;
                        continue;
                    }

                    if (fish.locationSet()) {
                        int fishX = fish.getLocation().x + fish.getDrawWidth() / 2;
                        int fishY = fish.getLocation().y + fish.getDrawHeight() / 2;

                        int rightBound = monsterX + MONSTER_WIDTH;
                        int bottomBound = monsterY + 50; // Instead of adding the drawHeight of the monster, we add 50 to prevent the monster from eating by using leg

                        if (fishX > monsterX && fishX < rightBound && fishY > monsterY && fishY < bottomBound) {
                            if (fish.getLevel() < 5) { // Last stage of fish shall not be eaten
                                aquarium.removeFish(fishIndex);
                                continue;
                            }
                        }
                    }

                }

                /* Compute the food that can be eaten by the fish */
                for (int foodIndex = 0; foodIndex < aquarium.getFoodCount(); ++foodIndex) {
                    food = aquarium.getFoodAtIndex(foodIndex);

                    if (fish.locationSet() && fish.getLocationArea().contains(food.getLocationArea())) {
                        fish.feed(food);
                        aquarium.removeFood(foodIndex);
                        if (Fish.getFishSenseStatus()) {
                            allFishRemoveTarget();
                        }
                    }
                }
            }
        }
    }

    /* Coordinate all fish to give up food on pursuit for reset purpose on every change in food */
    private void allFishRemoveTarget() {
        for (int fishIndex = 0; fishIndex < aquarium.getFishCount(); ++fishIndex) {
            aquarium.getFishAtIndex(fishIndex).removeTarget();
        }
    }

    /*
    * 1. Spawn Spongebob / Patrick in fixed interval if it has not already existed.
    * 2. Spawn bubbles in fixed interval
    */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bubbleTimer) {
            aquarium.addBubble();
        } else if (e.getSource() == monsterTimer) {
            if (aquarium.isSafe() && aquarium.isEnableMonster()) {
                aquarium.addMonster();
                monster = aquarium.getMonster();
            }
        }

    }
}
