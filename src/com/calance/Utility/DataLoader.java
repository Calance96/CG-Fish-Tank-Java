package com.calance.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.calance.Utility.AquariumConstants.MONSTER_TYPE;
import static com.calance.Utility.AquariumConstants.NUM_LEVEL;
import static com.calance.Utility.ConsoleMessageColor.ANSI_RED;

/*
* Application of Singleton Pattern
* DataLoader class has only one instance to serve as an image database.
*/
public class DataLoader {

    private static DataLoader dataLoader = new DataLoader();

    private BufferedImage backgroundImage;
    private BufferedImage sandImage;
    private BufferedImage wallpaper;
    private BufferedImage tableTextureImg;
    private BufferedImage floorImage;
    private BufferedImage foodImage;
    private Image plantImage;
    private Image bubbleImage;

    private List<Image> fishLeftImages;
    private List<Image> fishRightImages;
    private List<Image> monsterLeftImages;
    private List<Image> monsterRightImages;
    private final int[] drawWidths = {50, 60, 80, 105, 135, 175};
    private final int[] drawHeights = {38, 66, 51, 131, 135, 140};

    private DataLoader() {
        fishLeftImages = new ArrayList<>();
        fishRightImages = new ArrayList<>();
        monsterLeftImages = new ArrayList<>();
        monsterRightImages = new ArrayList<>();

        loadBackgroundImage();
        loadSceneImages();
        loadTableImage();
        loadLeftImages();
        loadRightImages();
        loadFoodImages();
        loadBubbleImages();
        loadPlantImages();
    }

    public static DataLoader getInstance() {
        return dataLoader;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public BufferedImage getSandImage() {
        return sandImage;
    }

    public BufferedImage getWallpaper() { return wallpaper; }

    public BufferedImage getFloorImage() {
        return floorImage;
    }

    public BufferedImage getTableTextureImg() {
        return tableTextureImg;
    }

    public Image getSwimLeftImage(int growthStage) {
        return fishLeftImages.get(growthStage);
    }

    public Image getSwimRightImage(int growthStage) {
        return fishRightImages.get(growthStage);
    }

    public Image getMonsterLeftImage(int typeNum) {
        return monsterLeftImages.get(typeNum);
    }

    public Image getMonsterRightImage(int typeNum) {
        return monsterRightImages.get(typeNum);
    }

    public int getImageWidth(int growthStage) {
        return drawWidths[growthStage];
    }

    public int getImageHeight(int growthStage) {
        return drawHeights[growthStage];
    }

    public BufferedImage getFoodImage() {
        return foodImage;
    }

    public Image getPlantImage() {
        return plantImage;
    }

    public Image getBubbleImage() { return bubbleImage; }

    /* Load fish tank background image */
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/resources/background/waterbg.png"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading background image");
        }

        try {
            sandImage = ImageIO.read(getClass().getResource("/resources/deco/sand.png"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading sand image");
            e.printStackTrace();
        }
    }

    /* Load scene wallpaper and floor texture */
    private void loadSceneImages() {
        try {
            wallpaper = ImageIO.read(getClass().getResource("/resources/texture/wall.jpg"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading wall texture");
            e.printStackTrace();
        }

        try {
            floorImage = ImageIO.read(getClass().getResource("/resources/texture/floorTexture.jpg"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading floor texture");
            e.printStackTrace();
        }

    }

    /* Load fish pellet image */
    private void loadFoodImages() {
        try {
            foodImage = ImageIO.read(getClass().getResource("/resources/food/fish_pellet.png"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading food image");
            e.printStackTrace();
        }

    }

    /* Load table image */
    private void loadTableImage() {
        try {
            tableTextureImg = ImageIO.read(getClass().getResource("/resources/texture/woodTexture.jpg"));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error loading table image");
            e.printStackTrace();
        }
    }

    /* Load bubble image*/
    private void loadBubbleImages() {
        bubbleImage = new ImageIcon(getClass().getResource("/resources/deco/bubble.png")).getImage();
    }

    private void loadPlantImages() {
        plantImage = new ImageIcon(getClass().getResource("/resources/plants/plant.gif")).getImage();
    }

    /* Loads swim left images */
    private void loadLeftImages() {
        for (int i = 0; i < NUM_LEVEL; ++i) {
            Image leftImage = new ImageIcon(getClass().getResource("/resources/pokemon/left_level" + i + ".gif")).getImage();
            fishLeftImages.add(leftImage);
        }

        for (int i = 0; i < MONSTER_TYPE; ++i) {
            Image leftImage = new ImageIcon(getClass().getResource("/resources/monster/monster_left" + i + ".gif")).getImage();
            monsterLeftImages.add(leftImage);
        }
    }

    /* Loads swim right images */
    private void loadRightImages() {
        for (int i = 0; i < NUM_LEVEL; ++i) {
            Image rightImage = new ImageIcon(getClass().getResource("/resources/pokemon/right_level" + i + ".gif")).getImage();
            fishRightImages.add(rightImage);
        }

        for (int i = 0; i < MONSTER_TYPE; ++i) {
            Image rightImage = new ImageIcon(getClass().getResource("/resources/monster/monster_right" + i + ".gif")).getImage();
            monsterRightImages.add(rightImage);
        }
    }

}
