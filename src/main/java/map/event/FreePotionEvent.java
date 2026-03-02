package map.event;

import Entity.Player;
import inventory.potion.*;
import java.util.Random;
import java.util.Scanner;

/**
 * อีเวนต์สุ่มรับโพชั่นฟรี
 * <p>
 * เมื่อผู้เล่นเลือก "Take the potion" ระบบจะสุ่มโพชั่น 1 ชนิดจากทั้งหมด 4 แบบ:
 * HealingPotion, AtkPotion, DefPotion หรือ EnergyPotion
 * แล้วเพิ่มเข้า Inventory ของผู้เล่น
 * </p>
 */
public class FreePotionEvent implements GameEvent {

    private Random random = new Random();

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
     *               0 = รับโพชั่น
     *               1 = ไม่ทำอะไร
     */
    @Override
    public void applyChoice(Player player, int choice) {

        if (choice == 0) {

            Potion potion;
            int roll = random.nextInt(4);

            switch (roll) {
                case 0: potion = new HealingPotion(); break;
                case 1: potion = new AtkPotion(); break;
                case 2: potion = new DefPotion(); break;
                default: potion = new EnergyPotion();
            }

            player.getInventory().addPotion(potion);
        }
    }

    /**
     * @return ชื่ออีเวนต์
     */
    @Override
    public String getName() {
        return "Mysterious Potion";
    }

    /**
     * @return คำอธิบายอีเวนต์
     */
    @Override
    public String getDescription() {
        return "You find a mysterious glowing potion.";
    }

    /**
     * @return path ของรูปภาพประกอบอีเวนต์
     */
    @Override
    public String getImagePath() {
        return "/events/potion.png";
    }

    /**
     * @return ตัวเลือกที่จะแสดงให้ผู้เล่นเลือก
     */
    @Override
    public String[] getOptions() {
        return new String[]{
                "Take the potion",
                "Leave it"
        };
    }
}