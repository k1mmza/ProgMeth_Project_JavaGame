package Entity.classes;

import Entity.Entity;
import Entity.Player;

/**
 * คลาสนักเวท (Mage)
 * <p>
 * เป็นคลาสสายโจมตีเวทมนตร์ที่มีพลังโจมตีสูง
 * แต่มีพลังชีวิตค่อนข้างต่ำเมื่อเทียบกับคลาสอื่น
 * เหมาะสำหรับการสร้างความเสียหายรุนแรงในระยะสั้น
 * </p>
 */
public class Mage extends Player {

    /**
     * สร้างตัวละคร Mage พร้อมค่าสถานะเริ่มต้น
     * <ul>
     *     <li>พลังชีวิต (HP): 70</li>
     *     <li>พลังโจมตี (Attack): 13</li>
     *     <li>พลังงานสูงสุด (Max Energy): 4</li>
     * </ul>
     *
     * @param name ชื่อของผู้เล่น
     */
    public Mage(String name) {
        super(name, 70, 13, 4);
        setEnergy(0); // เริ่มต้นพลังงานที่ 0 ตามระบบพลังงานใหม่
    }

    /**
     * สกิลที่ 1: Firebolt (ใช้พลังงาน 2)
     * <p>
     * สร้างความเสียหายเท่ากับพลังโจมตีพื้นฐาน + 6
     * </p>
     *
     * @param target เป้าหมายที่ถูกโจมตี
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill1(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Firebolt!");
        int damage = getAttack() + 6;
        target.takeDamage(damage);

        return true;
    }

    /**
     * สกิลที่ 2: Mana Barrier (ใช้พลังงาน 2)
     * <p>
     * เพิ่มเกราะ (Shield) ให้ตนเอง 15 หน่วย
     * </p>
     *
     * @param target เป้าหมาย (ไม่ได้ถูกใช้งานในสกิลนี้)
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill2(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Mana Barrier!");
        addShield(15);

        return true;
    }

    /**
     * สกิลที่ 3: Arcane Nova (ใช้พลังงาน 3)
     * <p>
     * สร้างความเสียหายรุนแรงเท่ากับ (พลังโจมตี × 2) + 6
     * </p>
     *
     * @param target เป้าหมายที่ถูกโจมตี
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill3(Entity target) {

        int cost = 3;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Arcane Nova!");
        int damage = getAttack() * 2 + 6;
        target.takeDamage(damage);
        return true;
    }

    /**
     * คืนชื่อสกิลที่ 1
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill1Name() {
        return "Firebolt";
    }

    /**
     * คืนชื่อสกิลที่ 2
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill2Name() {
        return "Mana Barrier";
    }

    /**
     * คืนชื่อสกิลที่ 3
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill3Name() {
        return "Arcane Nova";
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 1
     *
     * @return ค่าพลังงานที่ใช้ (2)
     */
    @Override
    public int getSkill1Cost() {
        return 2;
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 2
     *
     * @return ค่าพลังงานที่ใช้ (2)
     */
    @Override
    public int getSkill2Cost() {
        return 2;
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 3
     *
     * @return ค่าพลังงานที่ใช้ (3)
     */
    @Override
    public int getSkill3Cost() {
        return 3;
    }
}