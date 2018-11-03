package com.calance.ObjectAttributes;

import com.calance.Utility.DataLoader;

public class FishGrowth {

    private static final int[] WIDTH_ARRAY = {60, 80, 105, 135, 175};
    private static final int[] HEIGHT_ARRAY = {66, 51, 131, 135, 150};

    private int currentWidth;
    private double currentHeight;
    private int widthLimit;
    private int heightLimit;
    private double heightScale;
    private int level;

    private boolean isMaxLevel = false;

    public FishGrowth() {
        level = 0;
        calculateLimitAndScale();
    }

    public boolean increment() {
        if (isMaxLevel) {
            return false;
        }

        currentWidth++;
        currentHeight += heightScale;

        if (level == WIDTH_ARRAY.length - 1) {
            if (currentWidth > widthLimit) {
                level++;
                currentWidth = widthLimit;
                currentHeight = DataLoader.getInstance().getImageHeight(WIDTH_ARRAY.length);
                isMaxLevel = true;
            }
        } else {
            if (currentWidth > widthLimit) {
                level++;
                calculateLimitAndScale();
            }
        }
        return true;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentWidth() {
        return currentWidth;
    }

    public int getCurrentHeight() { return (int) currentHeight; }

    private void calculateLimitAndScale() {
        currentWidth = DataLoader.getInstance().getImageWidth(level);
        currentHeight = DataLoader.getInstance().getImageHeight(level);
        widthLimit = WIDTH_ARRAY[level];
        heightLimit = HEIGHT_ARRAY[level];
        heightScale = (heightLimit - currentHeight) / (widthLimit - currentWidth);
    }

}
