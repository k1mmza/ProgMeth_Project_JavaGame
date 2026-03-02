package inventory;

import inventory.potion.Potion;
import java.util.ArrayList;
import java.util.List;

/**
 * คลาส Inventory ใช้จัดการคลังไอเทมประเภท Potion ของผู้เล่น
 * <p>
 * กำหนดจำนวนไอเทมสูงสุดที่สามารถเก็บได้
 * และมีเมธอดสำหรับเพิ่ม ลบ และตรวจสอบสถานะของคลัง
 * </p>
 */
public class Inventory {

    private int maxPotion;
    private List<Potion> potions;

    /**
     * สร้างคลังไอเทมพร้อมกำหนดจำนวน Potion สูงสุดที่เก็บได้
     *
     * @param maxPotion จำนวน Potion สูงสุด
     */
    public Inventory(int maxPotion) {
        this.maxPotion = maxPotion;
        this.potions = new ArrayList<>();
    }

    /**
     * คืนรายการ Potion ทั้งหมดในคลัง
     *
     * @return รายการ Potion
     */
    public List<Potion> getPotions() {
        return potions;
    }

    /**
     * เพิ่ม Potion ลงในคลัง หากยังไม่เต็ม
     *
     * @param potion Potion ที่ต้องการเพิ่ม
     * @return true หากเพิ่มสำเร็จ, false หากคลังเต็ม
     */
    public boolean addPotion(Potion potion) {
        if (potions.size() >= maxPotion) {
            return false;
        }
        return potions.add(potion);
    }

    /**
     * ลบ Potion ออกจากคลัง
     *
     * @param potion Potion ที่ต้องการลบ
     * @return true หากลบสำเร็จ, false หากไม่พบไอเทม
     */
    public boolean removePotion(Potion potion) {
        return potions.remove(potion);
    }

    /**
     * ตรวจสอบว่าคลังเต็มหรือไม่
     *
     * @return true หากคลังเต็ม, false หากยังมีที่ว่าง
     */
    public boolean isFull() {
        return potions.size() >= maxPotion;
    }

    /**
     * คืนจำนวน Potion ปัจจุบันในคลัง
     *
     * @return จำนวน Potion
     */
    public int getSize() {
        return potions.size();
    }
}