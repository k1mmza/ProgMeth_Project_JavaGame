package application;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import gameLogic.GameLogic;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClassSelectionGUI().setVisible(true);
        });
    }
}