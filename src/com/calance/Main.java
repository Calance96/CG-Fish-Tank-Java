package com.calance;

import com.calance.OuterObjects.Showroom;
import com.calance.OuterObjects.Table3D;
import com.calance.Utility.ControlPanel;
import com.calance.Utility.MenuOptions;

import javax.swing.*;
import java.awt.*;

import static com.calance.Utility.ConsoleMessageColor.ANSI_RED;

public class Main {

    private static JFrame viewport = new JFrame("Fish Tank Project");
    private static Aquarium aquarium = new Aquarium();
    private static Table3D table3D = new Table3D(aquarium);
    private static Showroom showroom = new Showroom(aquarium, table3D);

    public static void main(String[] args) {
        setupUIStyle();

        viewport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewport.setLayout(new BorderLayout());
        viewport.add(new ControlPanel(aquarium), BorderLayout.PAGE_START);
        viewport.setJMenuBar(new MenuOptions());

        viewport.add(showroom, BorderLayout.CENTER);
        viewport.pack();
        viewport.setResizable(false);
        viewport.setVisible(true);
    }

    public static void setupUIStyle() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(ANSI_RED + "UI switching failed");
        }
    }

}
