package application;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import inventory.potion.AtkPotion;

import javax.swing.*;
import java.awt.*;

/**
 * หน้าจอสำหรับให้ผู้เล่นเลือกคลาสตัวละครก่อนเริ่มเกม
 * <p>
 * หน้าจอนี้ทำหน้าที่รับชื่อผู้เล่น และให้เลือกคลาส ได้แก่ Knight, Mage หรือ Rogue
 * เมื่อเลือกคลาสแล้ว ระบบจะสร้างออบเจกต์ {@link Player} ตามคลาสที่เลือก
 * ตั้งค่าเริ่มต้นบางอย่าง และส่งผู้เล่นไปยังหน้าจอแผนที่หลักของเกม
 * </p>
 */
public class ClassSelectionGUI extends JFrame {

    /**
     * ช่องกรอกชื่อผู้เล่น
     */
    private JTextField nameField;

    // ===== สีธีมเกม =====
    /**
     * สีพื้นหลังหลักของหน้าจอ
     */
    private final Color BACKGROUND_COLOR = new Color(25, 25, 25);

    /**
     * สีปุ่มปกติ
     */
    private final Color BUTTON_COLOR = new Color(60, 60, 60);

    /**
     * สีปุ่มเมื่อเมาส์วางอยู่ด้านบน (Hover)
     */
    private final Color BUTTON_HOVER = new Color(120, 30, 30);

    /**
     * สีข้อความหลักของหน้าจอ
     */
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    /**
     * สร้างหน้าจอเลือกคลาสตัวละคร
     * <p>
     * ภายใน constructor จะกำหนดค่า JFrame,
     * จัดวาง layout, สร้างปุ่มเลือกคลาส และกำหนด ActionListener
     * สำหรับเริ่มเกมเมื่อผู้เล่นเลือกคลาส
     * </p>
     */
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

    /**
     * สร้างปุ่มที่มีสไตล์ธีมของเกม
     * <p>
     * ปุ่มจะถูกกำหนดสีพื้นหลัง สีตัวอักษร และเอฟเฟกต์ Hover
     * เพื่อให้เข้ากับธีมของเกม
     * </p>
     *
     * @param text ข้อความที่แสดงบนปุ่ม
     * @return ปุ่มที่ถูกตกแต่งเรียบร้อยแล้ว
     */
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

    /**
     * เริ่มต้นเกมตามคลาสที่ผู้เล่นเลือก
     * <p>
     * เมธอดนี้จะ:
     * <ul>
     *     <li>ตรวจสอบว่าผู้เล่นกรอกชื่อหรือไม่</li>
     *     <li>สร้างออบเจกต์ {@link Player} ตามคลาสที่เลือก</li>
     *     <li>เพิ่มไอเทมเริ่มต้นให้ผู้เล่น</li>
     *     <li>กำหนดค่าพลังโจมตีเริ่มต้น</li>
     *     <li>ส่งข้อมูลผู้เล่นไปยัง {@link MainMap}</li>
     *     <li>ปิดหน้าจอเลือกคลาส และเปิดหน้าจอแผนที่</li>
     * </ul>
     *
     * @param classType ประเภทคลาสที่เลือก (knight, mage หรือ rogue)
     */
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
                // เผื่ออาจารอยากจบไปดูท้ายเกมไวๆ
                player.setAttack(1000);
                break;
        }

        // 🔥 ส่ง player ไปเก็บที่ MainMap
//        player.getInventory().addPotion(new AtkPotion());
//        player.setAttack(1000);
        MainMap.setPlayer(player);

        dispose();

        FXLauncher.openMap();
    }
}