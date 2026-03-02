package inventory.potion;

import Entity.Player;

/**
 * อินเทอร์เฟซ Usable
 * <p>
 * กำหนดพฤติกรรมสำหรับไอเทมที่สามารถใช้งานได้
 * คลาสที่ implements อินเทอร์เฟซนี้ต้องกำหนดผลลัพธ์ของการใช้งานเอง
 * </p>
 */
public interface Usable {

    /**
     * ใช้งานไอเทมกับผู้เล่น
     *
     * @param character ผู้เล่นที่ใช้ไอเทม
     * @return true หากใช้งานสำเร็จ, false หากไม่สามารถใช้งานได้
     */
    boolean use(Player character);
}