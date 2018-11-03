package com.calance.Utility;

import com.calance.Aquarium;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private Aquarium aquarium;
    private static JButton controlButton = new JButton("Pause");
    private static JButton addButton = new JButton("Add");
    private static JButton removeButton = new JButton("Remove");
    private static JButton foodButton = new JButton("FishFood");

    private static JLabel fishCountLabel = new JLabel("Fish count: ");
    private static JLabel fishNumber = new JLabel("0");

    public ControlPanel(Aquarium aquarium) {
        this.aquarium = aquarium;
        setupButtons();
    }

    /* Setup the top panel of the window */
    private void setupButtons() {

        add(controlButton);
        add(addButton);
        add(removeButton);
        add(foodButton);
        add(fishCountLabel);
        add(fishNumber);

        controlButton.addActionListener(e -> {
            if (aquarium.isRunning()) {
                aquarium.pause();
                controlButton.setText("Resume");
                controlButton.setToolTipText("Resume the fish tank activity");
            } else {
                aquarium.resume();
                controlButton.setText("Pause");
                controlButton.setToolTipText("Pause the fish tank activity");
            }
        });

        addButton.addActionListener(e -> {
            aquarium.addFish();
        });

        removeButton.addActionListener(e -> {
            aquarium.removeFish();
        });

        foodButton.addActionListener(e -> aquarium.addFoodRandom());

        addButton.setToolTipText("Add a new fish into the tank");
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        removeButton.setToolTipText("Remove the eldest fish from the tank");
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        foodButton.setToolTipText("Drop food at random position in the tank");
        foodButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void updateFishNumber(int fishCount) {
        fishNumber.setText(String.valueOf(fishCount));
    }
}
