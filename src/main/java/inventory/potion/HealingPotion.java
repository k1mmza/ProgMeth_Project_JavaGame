package inventory.potion;

import Entity.Player;

/**
 * ไอเทมประเภท Potion สำหรับฟื้นฟูพลังชีวิต (HP) ให้ผู้เล่น
 * <p>
 * เมื่อใช้งานจะฟื้นฟูพลังชีวิตตามค่าที่กำหนด
 * โดยพลังชีวิตจะไม่เกินค่า HP สูงสุดของผู้เล่น
 * </p>
 */
public class HealingPotion extends Potion {

    private int increaseHp;

    /**
     * สร้าง HealingPotion พร้อมกำหนดค่าเริ่มต้น
     * <ul>
     *     <li>ชื่อ: HealingPotion</li>
     *     <li>ราคาซื้อ: 15</li>
     *     <li>ราคาขาย: 10</li>
     *     <li>ฟื้นฟูพลังชีวิต: 25</li>
     * </ul>
     */
    public HealingPotion() {
        super("HealingPotion", 15, 10);
        setIncreaseHp(25);
        this.description = "Restore 25 HP.";
    }

    /**
     * ใช้ไอเทมเพื่อฟื้นฟูพลังชีวิตให้ผู้เล่น
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true เมื่อใช้งานสำเร็จ
     */
    @Override
    public boolean use(Player character) {
        character.heal(increaseHp);
        return true;
    }

    /**
     * คืนค่าจำนวนพลังชีวิตที่ฟื้นฟู
     *
     * @return ค่าพลังชีวิตที่เพิ่ม
     */
    public int getIncreaseHp() {
        return increaseHp;
    }

    /**
     * คืนค่าราคาซื้อของไอเทม
     *
     * @return ราคาซื้อ
     */
    public int getBuyCost() {
        return buyCost;
    }

    /**
     * คืนค่าราคาขายของไอเทม
     *
     * @return ราคาขาย
     */
    public int getSellCost() {
        return sellCost;
    }

    /**
     * กำหนดค่าพลังชีวิตที่ฟื้นฟูเมื่อใช้ไอเทม
     *
     * @param increaseHp ค่าพลังชีวิตที่ต้องการตั้งค่า
     */
    public void setIncreaseHp(int increaseHp) {
        this.increaseHp = increaseHp;
    }
}