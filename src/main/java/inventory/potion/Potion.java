package inventory.potion;

import Entity.Player;

/**
 * คลาสนามธรรม Potion แทนไอเทมประเภทน้ำยาที่สามารถซื้อ ขาย และใช้งานได้
 * <p>
 * implements อินเทอร์เฟซ Tradable และ Usable
 * โดยกำหนดโครงสร้างพื้นฐานของไอเทม เช่น ชื่อ ราคา และคำอธิบาย
 * คลาสลูกต้องกำหนดพฤติกรรมของเมธอด use()
 * </p>
 */
public abstract class Potion implements Tradable, Usable {

    protected String name;
    protected int buyCost;
    protected int sellCost;
    protected String description;

    /**
     * สร้าง Potion พร้อมกำหนดชื่อ ราคาซื้อ และราคาขาย
     *
     * @param name     ชื่อไอเทม
     * @param buyCost  ราคาซื้อ
     * @param sellCost ราคาขาย
     */
    public Potion(String name, int buyCost, int sellCost) {
        this.name = name;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    /**
     * ซื้อไอเทม โดยหักทองจากผู้เล่นและเพิ่มไอเทมเข้าคลัง
     *
     * @param character ผู้เล่นที่ทำการซื้อ
     * @return true หากทองเพียงพอและซื้อสำเร็จ, false หากทองไม่พอ
     */
    @Override
    public boolean buy(Player character) {
        if (character.getGold() >= buyCost) {
            character.setGold(character.getGold() - buyCost);
            character.getInventory().addPotion(this);
            return true;
        }
        return false;
    }

    /**
     * ขายไอเทม โดยเพิ่มทองให้ผู้เล่นและลบไอเทมออกจากคลัง
     *
     * @param character ผู้เล่นที่ทำการขาย
     * @return true เมื่อขายสำเร็จ
     */
    @Override
    public boolean sell(Player character) {
        character.setGold(character.getGold() + sellCost);
        character.getInventory().removePotion(this);
        return true;
    }

    /**
     * ใช้งานไอเทม
     * คลาสลูกต้องกำหนดพฤติกรรมการใช้งานเอง
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true หากใช้งานสำเร็จ
     */
    @Override
    public abstract boolean use(Player character);

    /**
     * คืนชื่อไอเทม
     *
     * @return ชื่อไอเทม
     */
    public String getName() {
        return name;
    }

    /**
     * กำหนดชื่อไอเทม
     *
     * @param name ชื่อใหม่ของไอเทม
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * คืนค่าราคาขาย
     *
     * @return ราคาขาย
     */
    public int getSellCost() {
        return sellCost;
    }

    /**
     * กำหนดราคาขาย
     *
     * @param sellCost ราคาขายใหม่
     */
    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }

    /**
     * คืนค่าราคาซื้อ
     *
     * @return ราคาซื้อ
     */
    public int getBuyCost() {
        return buyCost;
    }

    /**
     * กำหนดราคาซื้อ
     *
     * @param buyCost ราคาซื้อใหม่
     */
    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    /**
     * คืนคำอธิบายไอเทม
     *
     * @return คำอธิบาย
     */
    public String getDescription() {
        return description;
    }
}