package inventory.potion;

import Entity.Player;

/**
 * ไอเทมประเภท Potion สำหรับเพิ่มค่าพลังโจมตีให้ผู้เล่น
 * <p>
 * เมื่อใช้งานจะเพิ่มค่า Attack ของผู้เล่นแบบถาวรตามค่าที่กำหนด
 * </p>
 */
public class AtkPotion extends Potion {

    private int increaseAtk;

    /**
     * สร้าง AtkPotion พร้อมกำหนดค่าเริ่มต้น
     * <ul>
     *     <li>ชื่อ: AtkPotion</li>
     *     <li>ราคาซื้อ: 20</li>
     *     <li>ราคาขาย: 10</li>
     *     <li>เพิ่มพลังโจมตี: 1</li>
     * </ul>
     */
    public AtkPotion() {
        super("AtkPotion", 20, 10);
        setIncreaseAtk(1);
        this.description = "Increase attack by 1.";
    }

    /**
     * ใช้ไอเทมเพื่อเพิ่มค่าพลังโจมตีของผู้เล่น
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true เมื่อใช้งานสำเร็จ
     */
    @Override
    public boolean use(Player character) {
        character.setAttack(character.getAttack() + getIncreaseAtk());
        return true;
    }

    /**
     * คืนค่าจำนวนพลังโจมตีที่เพิ่ม
     *
     * @return ค่าพลังโจมตีที่เพิ่ม
     */
    public int getIncreaseAtk() {
        return increaseAtk;
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
     * กำหนดค่าพลังโจมตีที่เพิ่มเมื่อใช้ไอเทม
     *
     * @param increaseAtk ค่าพลังโจมตีที่ต้องการตั้งค่า
     */
    public void setIncreaseAtk(int increaseAtk) {
        this.increaseAtk = increaseAtk;
    }
}