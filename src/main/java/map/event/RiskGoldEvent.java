package map.event;

import Entity.Player;
import java.util.Scanner;

/**
 * อีเวนต์เสี่ยงรับทอง (Risky Deal)
 * <p>
 * ผู้เล่นสามารถเลือกรับทอง 25 หน่วย
 * แต่จะต้องเสียพลังชีวิต 8 หน่วยเป็นการแลกเปลี่ยน
 * </p>
 */
public class RiskGoldEvent implements GameEvent {

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
     *               0 = รับทอง 25 และเสีย 8 HP
     *               1 = ปฏิเสธ
     */
    @Override
    public void applyChoice(Player player, int choice) {

        if (choice == 0) {
            player.takeDamage(8);
            player.addGold(25);
        }
    }

    /**
     * @return ชื่ออีเวนต์
     */
    @Override
    public String getName() {
        return "Risky Deal";
    }

    /**
     * @return คำอธิบายอีเวนต์
     */
    @Override
    public String getDescription() {
        return "A shady merchant offers you gold... for a price.";
    }

    /**
     * @return path ของรูปภาพประกอบอีเวนต์
     */
    @Override
    public String getImagePath() {
        return "/events/merchant.png";
    }

    /**
     * @return ตัวเลือกที่จะแสดงให้ผู้เล่นเลือก
     */
    @Override
    public String[] getOptions() {
        return new String[]{
                "Gain 25 Gold (Lose 8 HP)",
                "Refuse"
        };
    }
}