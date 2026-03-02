package map.event;

import Entity.Player;
import java.util.Scanner;

/**
 * อีเวนต์บ่อน้ำพุรักษา (Healing Fountain)
 * <p>
 * เมื่อผู้เล่นเลือกใช้งาน จะฟื้นฟูพลังชีวิต 10 หน่วย
 * </p>
 */
public class HealingEvent implements GameEvent {

    /**
     * เมธอดสำหรับเวอร์ชัน console (ยังไม่ได้ใช้งานใน GUI)
     *
     * @param player ผู้เล่นที่เข้าร่วมอีเวนต์
     * @param scanner ตัวอ่าน input จาก console
     */
    @Override
    public void execute(Player player, Scanner scanner) {
        // console version
    }

    /**
     * ประมวลผลตามตัวเลือกของผู้เล่น
     *
     * @param player ผู้เล่นที่เลือกอีเวนต์
     * @param choice ตัวเลือกที่ผู้เล่นกด
     *               0 = ฟื้นฟู 10 HP
     *               1 = ไม่ทำอะไร
     */
    @Override
    public void applyChoice(Player player, int choice) {
        if (choice == 0) {
            player.heal(10);
        }
    }

    /**
     * @return ชื่ออีเวนต์
     */
    @Override
    public String getName() {
        return "Healing Fountain";
    }

    /**
     * @return คำอธิบายอีเวนต์
     */
    @Override
    public String getDescription() {
        return "You find a mysterious healing fountain.";
    }

    /**
     * @return path ของรูปภาพประกอบอีเวนต์
     */
    @Override
    public String getImagePath() {
        return "/events/fountain.png";
    }

    /**
     * @return ตัวเลือกที่จะแสดงให้ผู้เล่นเลือก
     */
    @Override
    public String[] getOptions() {
        return new String[]{
                "Heal 10 HP",
                "Leave"
        };
    }
}