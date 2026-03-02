package Entity.classes;

import Entity.Entity;
import Entity.Player;

/**
 * คลาสโจร (Rogue)
 * <p>
 * เป็นคลาสสายโจมตีที่เน้นความรวดเร็วและเอฟเฟกต์สถานะ
 * สามารถสร้างความเสียหายเพิ่มเมื่อศัตรูติดสถานะ
 * และมีความสามารถในการหลบหลีกการโจมตี
 * </p>
 */
public class Rogue extends Player {

    /**
     * สร้างตัวละคร Rogue พร้อมค่าสถานะเริ่มต้น
     * <ul>
     *     <li>พลังชีวิต (HP): 80</li>
     *     <li>พลังโจมตี (Attack): 10</li>
     *     <li>พลังงานสูงสุด (Max Energy): 6</li>
     * </ul>
     *
     * @param name ชื่อของผู้เล่น
     */
    public Rogue(String name) {
        super(name, 80, 10, 6);
        setEnergy(0);
    }

    /**
     * สกิลที่ 1: Backstab (ใช้พลังงาน 1)
     * <p>
     * สร้างความเสียหายเท่ากับพลังโจมตีพื้นฐาน + 5
     * หากเป้าหมายติดสถานะ Vulnerable จะเพิ่มความเสียหาย 50%
     * </p>
     *
     * @param target เป้าหมายที่ถูกโจมตี
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill1(Entity target) {

        int cost = 1;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Backstab!");

        int damage = getAttack() + 5;

        if (target.isVulnerable()) {
            damage = (int) (damage * 1.5);
        }

        target.takeDamage(damage);
        return true;
    }

    /**
     * สกิลที่ 2: Smoke Bomb (ใช้พลังงาน 1)
     * <p>
     * ทำให้ผู้เล่นสามารถหลบการโจมตีครั้งถัดไปได้ 1 ครั้ง
     * และทำให้ศัตรูติดสถานะ Vulnerable 1 เทิร์น
     * </p>
     *
     * @param target เป้าหมายที่ได้รับสถานะ
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill2(Entity target) {

        int cost = 1;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Smoke Bomb!");

        addEvade(1);                // หลบการโจมตีครั้งถัดไป
        target.applyVulnerable(1);  // ทำให้ศัตรูรับความเสียหายเพิ่ม

        return true;
    }

    /**
     * สกิลที่ 3: Poisoned Blade (ใช้พลังงาน 2)
     * <p>
     * สร้างความเสียหายเท่ากับพลังโจมตีพื้นฐาน
     * และทำให้เป้าหมายติดสถานะพิษ 4 หน่วย
     * </p>
     *
     * @param target เป้าหมายที่ถูกโจมตี
     * @return true หากใช้สกิลสำเร็จ, false หากพลังงานไม่เพียงพอ
     */
    @Override
    public boolean skill3(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Poisoned Blade!");

        target.takeDamage(getAttack());
        target.addPoison(4);

        return true;
    }

    /**
     * คืนชื่อสกิลที่ 1
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill1Name() {
        return "Backstab";
    }

    /**
     * คืนชื่อสกิลที่ 2
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill2Name() {
        return "Smoke Bomb";
    }

    /**
     * คืนชื่อสกิลที่ 3
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill3Name() {
        return "Poisoned Blade";
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 1
     *
     * @return ค่าพลังงานที่ใช้ (1)
     */
    @Override
    public int getSkill1Cost() {
        return 1;
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 2
     *
     * @return ค่าพลังงานที่ใช้ (1)
     */
    @Override
    public int getSkill2Cost() {
        return 1;
    }

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 3
     *
     * @return ค่าพลังงานที่ใช้ (2)
     */
    @Override
    public int getSkill3Cost() {
        return 2;
    }
}