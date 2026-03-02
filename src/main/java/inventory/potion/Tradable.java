package inventory.potion;

import Entity.Player;

/**
 * อินเทอร์เฟซ Tradable
 * <p>
 * กำหนดพฤติกรรมสำหรับไอเทมที่สามารถซื้อและขายได้
 * คลาสที่ implements อินเทอร์เฟซนี้ต้องกำหนดวิธีการซื้อและขายเอง
 * </p>
 */
public interface Tradable {

    /**
     * ทำการซื้อไอเทม
     *
     * @param character ผู้เล่นที่ทำการซื้อ
     * @return true หากซื้อสำเร็จ, false หากไม่สำเร็จ (เช่น ทองไม่พอ)
     */
    boolean buy(Player character);

    /**
     * ทำการขายไอเทม
     *
     * @param character ผู้เล่นที่ทำการขาย
     * @return true หากขายสำเร็จ
     */
    boolean sell(Player character);
}