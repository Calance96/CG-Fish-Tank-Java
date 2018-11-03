package com.calance.Utility;

import com.calance.Aquarium;
import com.calance.InnerObjects.Fish;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuOptions extends JMenuBar implements ActionListener {

    private JMenuItem exit = new JMenuItem("Close Application");
    private JMenuItem about = new JMenuItem("About");

    private JMenuItem healthControl = new JMenuItem("Enable fish health");
    private JMenuItem fishSenseControl = new JMenuItem("Disable fish sense");
    private JMenuItem monsterControl = new JMenuItem("Disable monster");
    private JMenuItem removeWindowBorder = new JMenuItem("Remove viewport border");


    public MenuOptions() {
        JMenu file = new JMenu("File");
        JMenu advancedControl = new JMenu("Advanced");

        exit.addActionListener(this);
        about.addActionListener(this);
        healthControl.addActionListener(this);
        fishSenseControl.addActionListener(this);
        monsterControl.addActionListener(this);
        removeWindowBorder.addActionListener(this);

        file.add(new JSeparator());
        file.add(exit);
        file.add(about);

        advancedControl.add(healthControl);
        advancedControl.add(fishSenseControl);
        advancedControl.add(monsterControl);
        advancedControl.add(removeWindowBorder);

        add(file);
        add(advancedControl);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == about) {
            showAboutDialog();
        } else if (e.getSource() == healthControl) {
            toggleHealth();
        } else if (e.getSource() == fishSenseControl) {
            toggleFishSense();
        } else if (e.getSource() == monsterControl) {
            toggleMonster();
        } else if (e.getSource() == removeWindowBorder) {
            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
            window.dispose();
            if (window.isUndecorated()) {
                window.setUndecorated(false);
                removeWindowBorder.setText("Remove window border");
            } else {
                window.setUndecorated(true);
                removeWindowBorder.setText("Display window border");
            }
            window.setVisible(true);
        }
    }

    private void toggleHealth() {
        if (Fish.getHealthControlStatus()) {
            healthControl.setText("Enable fish health");
        } else {
            healthControl.setText("Disable fish health");
        }
        Fish.toggleHealth();
    }

    private void toggleFishSense() {
        if (Fish.getFishSenseStatus()) {
            fishSenseControl.setText("Enable fish sense");
        } else {
            fishSenseControl.setText("Disable fish sense");
        }
        Fish.toggleFishSense();
    }

    private void toggleMonster() {
        if (Aquarium.isEnableMonster()) {
            monsterControl.setText("Enable monster");
        } else {
            monsterControl.setText("Disable monster");
        }
        Aquarium.toggleMonster();
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(null, "Computer Graphics Fish Tank Project\n" +
                "--------------------------------------------------\n" +
                "Lecturer: Dr. Burra Venkata\n" +
                "--------------------------------------------------\n" +
                "Desinged and created by:\n\n" +
                "1. SWE1609562 Hong Yi Zhi \n" +
                "2. SWE1609507 Chee Sue Sien \n" +
                "3. SWE1609661Teh Li Han \n" +
                "--------------------------------------------------\n" +
                "What is fish sense?\n" +
                "A algorithm design for fish to detect \nfood within a certain coverage :)\n" +
                "--------------------------------------------------\n" +
                "Subject to copyright ©2018", "About", JOptionPane.PLAIN_MESSAGE);
    }
}
