package map.shop;

import inventory.potion.*;
import Entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาส Shop แทนร้านค้าในเกม
 * <p>
 * ร้านค้าจะมีรายการ Potion ที่สามารถซื้อได้
 * ผู้เล่นสามารถดูสินค้าและซื้อได้หากมีทองเพียงพอ
 * </p>
 *
 * <p>
 * ปัจจุบันร้านค้าใช้ stock แบบคงที่ (Fixed Stock)
 * ซึ่งจะถูกสร้างเมื่อมีการสร้าง Shop ใหม่
 * </p>
 */
public class Shop {

    private List<Potion> stock;

    /**
     * สร้างร้านค้าและกำหนดรายการสินค้าเริ่มต้น
     * โดยเพิ่ม Potion พื้นฐานลงใน stock
     */
    public Shop() {
        stock = new ArrayList<>();

        // Fixed stock
        stock.add(new HealingPotion());
        stock.add(new AtkPotion());
        stock.add(new DefPotion());
        stock.add(new EnergyPotion());
    }

    /**
     * คืนค่ารายการสินค้าในร้าน
     *
     * @return List ของ Potion ที่มีในร้าน
     */
    public List<Potion> getStock() {
        return stock;
    }

    /**
     * แสดงรายการสินค้าในร้าน (สำหรับ console)
     * จะแสดงชื่อ ราคา Buy และราคา Sell
     */
    public void displayStock() {
        System.out.println("=== Shop ===");
        for (int i = 0; i < stock.size(); i++) {
            Potion potion = stock.get(i);
            System.out.println(i + ". " + potion.getName()
                    + " | Buy: " + potion.getBuyCost()
                    + " | Sell: " + potion.getSellCost());
        }
    }

    /**
     * ให้ผู้เล่นซื้อ Potion ตาม index ที่เลือก
     *
     * @param index ตำแหน่งของ Potion ใน stock
     * @param character ผู้เล่นที่ต้องการซื้อ
     * @return true หากซื้อสำเร็จ, false หากไม่สำเร็จ
     */
    public boolean buyPotion(int index, Player character) {
        if (index < 0 || index >= stock.size()) {
            return false;
        }

        Potion potion = stock.get(index);

        if (potion.buy(character)) {
            System.out.println("You bought " + potion.getName());
            return true;
        }

        System.out.println("Not enough gold!");
        return false;
    }
}
