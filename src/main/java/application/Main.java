package application;

import javax.swing.*;

/**
 * คลาสหลักสำหรับเริ่มต้นการทำงานของโปรแกรม
 * <p>
 * ทำหน้าที่เป็นจุดเริ่มต้น (Entry Point) ของแอปพลิเคชัน
 * โดยจะเรียกใช้งานหน้าจอ {@link ClassSelectionGUI}
 * ผ่าน Swing Event Dispatch Thread เพื่อความปลอดภัยของการจัดการ GUI
 * </p>
 */
public class Main {

    /**
     * เมธอดหลักของโปรแกรม
     * <p>
     * ใช้ {@link SwingUtilities#invokeLater(Runnable)} เพื่อสร้างและแสดง
     * หน้าจอเลือกคลาสบน Event Dispatch Thread (EDT)
     * ซึ่งเป็นแนวปฏิบัติมาตรฐานในการพัฒนาโปรแกรมด้วย Swing
     * </p>
     *
     * @param args อาร์กิวเมนต์ที่ส่งเข้ามาทาง command line (ไม่ได้ถูกใช้งานในโปรแกรมนี้)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClassSelectionGUI().setVisible(true);
        });
    }
}