package inventory.potion;

import Entity.Player;

/**
 * ไอเทมประเภท Potion สำหรับฟื้นฟูพลังงาน (Energy) ให้ผู้เล่น
 * <p>
 * เมื่อใช้งานจะฟื้นฟูพลังงาน 2 หน่วย
 * โดยจะไม่เกินค่าพลังงานสูงสุดของผู้เล่น
 * </p>
 */
public class EnergyPotion extends Potion {

    /**
     * สร้าง EnergyPotion พร้อมกำหนดค่าเริ่มต้น
     * <ul>
     *     <li>ชื่อ: EnergyPotion</li>
     *     <li>ราคาซื้อ: 15</li>
     *     <li>ราคาขาย: 10</li>
     * </ul>
     */
    public EnergyPotion() {
        super("EnergyPotion", 15, 10);
        this.description = "Restore 2 Energy.";
    }

    /**
     * ใช้ไอเทมเพื่อฟื้นฟูพลังงานให้ผู้เล่น 2 หน่วย
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true เมื่อใช้งานสำเร็จ
     */
    @Override
    public boolean use(Player character) {
        character.gainEnergy(2);
        return true;
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
}