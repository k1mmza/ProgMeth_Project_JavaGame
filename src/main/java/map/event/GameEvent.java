package map.event;

import Entity.Player;
import java.util.Scanner;

/**
 * Interface สำหรับอีเวนต์ทั้งหมดในเกม
 * <p>
 * GameEvent กำหนดโครงสร้างพื้นฐานของอีเวนต์ เช่น
 * การแสดงผล, การรับตัวเลือกจากผู้เล่น และการประมวลผลผลลัพธ์
 * โดยรองรับทั้งเวอร์ชัน console และ GUI
 * </p>
 */
public interface GameEvent {

    /**
     * ใช้สำหรับเวอร์ชัน console ของอีเวนต์
     *
     * @param player ผู้เล่นที่เข้าร่วมอีเวนต์
     * @param scanner ตัวอ่าน input จาก console
     */
    void execute(Player player, Scanner scanner);

    /**
     * ประมวลผลตามตัวเลือกของผู้เล่น (ใช้กับระบบ GUI)
     *
     * @param player ผู้เล่นที่เลือกอีเวนต์
     * @param choice ดัชนีของตัวเลือกที่ผู้เล่นเลือก
     */
    void applyChoice(Player player, int choice);

    /**
     * @return ชื่อของอีเวนต์
     */
    String getName();

    /**
     * @return คำอธิบายอีเวนต์
     */
    String getDescription();

    /**
     * @return path ของรูปภาพประกอบอีเวนต์
     */
    String getImagePath();

    /**
     * @return รายการตัวเลือกที่ผู้เล่นสามารถเลือกได้
     */
    String[] getOptions();
}