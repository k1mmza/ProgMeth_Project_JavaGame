package inventory.potion;

import Entity.Player;

/**
 * ไอเทมประเภท Potion สำหรับเพิ่มค่าพลังป้องกันให้ผู้เล่น
 * <p>
 * เมื่อใช้งานจะเพิ่มค่า Defense ของผู้เล่นแบบถาวรตามค่าที่กำหนด
 * </p>
 */
public class DefPotion extends Potion {

    private int increaseDef;

    /**
     * สร้าง DefPotion พร้อมกำหนดค่าเริ่มต้น
     * <ul>
     *     <li>ชื่อ: DefPotion</li>
     *     <li>ราคาซื้อ: 20</li>
     *     <li>ราคาขาย: 10</li>
     *     <li>เพิ่มพลังป้องกัน: 1</li>
     * </ul>
     */
    public DefPotion() {
        super("DefPotion", 20, 10);
        setIncreaseDef(1);
        this.description = "Increase defense by 1.";
    }

    /**
     * ใช้ไอเทมเพื่อเพิ่มค่าพลังป้องกันของผู้เล่น
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true เมื่อใช้งานสำเร็จ
     */
    @Override
    public boolean use(Player character) {
        character.setDefense(character.getDefense() + getIncreaseDef());
        return true;
    }

    /**
     * คืนค่าจำนวนพลังป้องกันที่เพิ่ม
     *
     * @return ค่าพลังป้องกันที่เพิ่ม
     */
    public int getIncreaseDef() {
        return increaseDef;
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
     * กำหนดค่าพลังป้องกันที่เพิ่มเมื่อใช้ไอเทม
     *
     * @param increaseDef ค่าพลังป้องกันที่ต้องการตั้งค่า
     */
    public void setIncreaseDef(int increaseDef) {
        this.increaseDef = increaseDef;
    }
}