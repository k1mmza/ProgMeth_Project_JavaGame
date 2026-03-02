package application;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import gameLogic.GameLogic;
import inventory.potion.AtkPotion;
import javafx.application.Platform;
import application.MainMap;

import javax.swing.*;
import java.awt.*;

public class ClassSelectionGUI extends JFrame {

    private JTextField nameField;

    // ===== สีธีมเกม =====
    private final Color BACKGROUND_COLOR = new Color(25, 25, 25);
    private final Color BUTTON_COLOR = new Color(60, 60, 60);
    private final Color BUTTON_HOVER = new Color(120, 30, 30);
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    public ClassSelectionGUI() {

        setTitle("SLAY THE GPA");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // ===== Title =====
        JLabel titleLabel = new JLabel("CHOOSE YOUR CLASS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setLayout(new GridLayout(4, 1, 15, 15));

        nameField = new JTextField();
        nameField.setBackground(new Color(40, 40, 40));
        nameField.setForeground(TEXT_COLOR);
        nameField.setCaretColor(Color.WHITE);
        nameField.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TEXT_COLOR),
                "Enter Your Name",
                0, 0,
                new Font("Serif", Font.BOLD, 14),
                TEXT_COLOR
        ));

        centerPanel.add(nameField);

        JButton knightBtn = createStyledButton("Knight (Tank)");
        JButton mageBtn = createStyledButton("Mage (High Damage)");
        JButton rogueBtn = createStyledButton("Rogue (Critical Assassin)");

        centerPanel.add(knightBtn);
        centerPanel.add(mageBtn);
        centerPanel.add(rogueBtn);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ===== Button Actions =====
        knightBtn.addActionListener(e -> startGame("knight"));
        mageBtn.addActionListener(e -> startGame("mage"));
        rogueBtn.addActionListener(e -> startGame("rogue"));
    }

    // ===== ปุ่มสไตล์เกม =====
    private JButton createStyledButton(String text) {

        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private void startGame(String classType) {

        String playerName = nameField.getText().trim();

        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return;
        }

        Player player = null;

        switch (classType) {
            case "knight":
                player = new Knight(playerName);
                break;
            case "mage":
                player = new Mage(playerName);
                break;
            case "rogue":
                player = new Rogue(playerName);
                break;
        }

        // 🔥 ส่ง player ไปเก็บที่ MainMap
        player.getInventory().addPotion(new AtkPotion());
        player.setAttack(1000);
        MainMap.setPlayer(player);

        dispose();

        FXLauncher.openMap();
    }
}