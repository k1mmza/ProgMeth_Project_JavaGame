package map.event;

import Entity.Player;
import java.util.Scanner;

/**
 * อีเวนต์ AncientStatueEvent
 * <p>
 * ผู้เล่นพบรูปปั้นโบราณที่เปล่งแสงจาง ๆ
 * สามารถเลือกเพิ่มพลังโจมตี หรือเพิ่มพลังชีวิตสูงสุด
 * หรือเลือกจากไปโดยไม่ทำอะไร
 * </p>
 */
public class AncientStatueEvent implements GameEvent {

    /**
     * เมธอดสำหรับรองรับโหมด console (ยังไม่ได้ใช้งาน)
     *
     * @param player  ผู้เล่น
     * @param scanner ตัวอ่านอินพุต
     */
    @Override
    public void execute(Player player, Scanner scanner) {
        // ของเดิม (เผื่อ console)
    }

    /**
     * ใช้ผลลัพธ์ตามตัวเลือกที่ผู้เล่นเลือก
     *
     * @param player ผู้เล่น
     * @param choice ตัวเลือกที่เลือก (index ของ option)
     */
    @Override
    public void applyChoice(Player player, int choice) {

        switch (choice) {
            case 0:
                // เพิ่มพลังโจมตี +1
                player.setAttack(player.getAttack() + 1);
                break;

            case 1:
                // เพิ่มพลังชีวิตสูงสุด +5 และฟื้นฟู 5 หน่วย
                player.setMaxHp(player.getMaxHp() + 5);
                player.heal(5);
                break;

            default:
                // Leave (ไม่ทำอะไร)
                break;
        }
    }

    /**
     * คืนชื่ออีเวนต์
     *
     * @return ชื่ออีเวนต์
     */
    @Override
    public String getName() {
        return "Ancient Statue";
    }

    /**
     * คืนคำอธิบายอีเวนต์
     *
     * @return คำอธิบาย
     */
    @Override
    public String getDescription() {
        return "You discover an ancient statue glowing faintly.";
    }

    /**
     * คืน path ของรูปภาพที่ใช้แสดงในอีเวนต์
     *
     * @return path ของรูปภาพ
     */
    @Override
    public String getImagePath() {
        return "/events/statue.png";
    }

    /**
     * คืนตัวเลือกทั้งหมดที่ผู้เล่นสามารถเลือกได้
     *
     * @return อาร์เรย์ของตัวเลือก
     */
    @Override
    public String[] getOptions() {
        return new String[]{
                "Gain +1 Attack",
                "Gain +5 Max HP",
                "Leave"
        };
    }
}