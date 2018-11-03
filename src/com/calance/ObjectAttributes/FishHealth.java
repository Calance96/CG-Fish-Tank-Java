package com.calance.ObjectAttributes;

import static com.calance.Utility.AquariumConstants.MAX_HEALTH;

public class FishHealth {

    private double currentHealth;
    private boolean hasHealth = true;

    public FishHealth() {
        currentHealth = MAX_HEALTH;
    }

    public void decrement() {
        if (hasHealth) {
            currentHealth -= 0.1;
            checkHasHealth();
        }
    }

    public void increment(int amount) {
        double newHealth = currentHealth + amount;
        currentHealth = newHealth < MAX_HEALTH? newHealth:MAX_HEALTH;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    private void checkHasHealth() {
        if (currentHealth <= 0) {
            hasHealth = false;
        }
    }


}
