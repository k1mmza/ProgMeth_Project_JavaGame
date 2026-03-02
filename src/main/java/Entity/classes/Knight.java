package Entity.classes;

import Entity.Entity;
import Entity.Player;

/**
 * คลาสอัศวิน (Knight)
 * <p>
 * เป็นคลาสสายป้องกัน (Tank) ที่มีความสามารถด้านเกราะ (Shield)
 * และสามารถใช้เกราะเพื่อเพิ่มความเสียหายได้
 * </p>
 */
public class Knight extends Player {

    /**
     * สร้างตัวละคร Knight พร้อมค่าสถานะเริ่มต้น
     * <ul>
     *     <li>พลังชีวิต (HP): 100</li>
     *     <li>พลังโจมตี (Attack): 8</li>
     *     <li>พลังงานสูงสุด (Max Energy): 6</li>
     * </ul>
     *
     * @param name ชื่อของผู้เล่น
     */
    public Knight(String name) {
        super(name, 100, 8, 6);
        setEnergy(0);
    }

    /**
     * สกิลที่ 1: Shield Strike (ใช้พลังงาน 1)
     * <p>
     * โจมตีเป้าหมายด้วยพลังโจมตีพื้นฐาน +2
     * และเพิ่มเกราะให้ตนเอง 5 หน่วย
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

        System.out.println(getName() + " uses Shield Strike!");
        int damage = getAttack() + 2;
        target.takeDamage(damage);
        addShield(5);

        return true;
    }

    /**
     * คืนค่าค่าพลังงานที่ใช้สำหรับสกิลที่ 1
     *
     * @return ค่าพลังงานที่ใช้ (1)
     */
    @Override
    public int getSkill1Cost() {
        return 1;
    }

    /**
     * สกิลที่ 2: Fortify (ใช้พลังงาน 2)
     * <p>
     * เพิ่มเกราะให้ตนเอง 15 หน่วย โดยไม่โจมตีเป้าหมาย
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

        System.out.println(getName() + " uses Fortify!");
        addShield(15);

        return true;
    }

    /**
     * คืนค่าค่าพลังงานที่ใช้สำหรับสกิลที่ 2
     *
     * @return ค่าพลังงานที่ใช้ (2)
     */
    @Override
    public int getSkill2Cost() {
        return 2;
    }

    /**
     * สกิลที่ 3: Shield Slam (ใช้พลังงาน 3)
     * <p>
     * สร้างความเสียหายเท่ากับพลังโจมตีพื้นฐาน + ค่าเกราะปัจจุบัน
     * จากนั้นทำให้ศัตรูติดสถานะ Vulnerable 1 เทิร์น
     * และรีเซ็ตค่าเกราะของผู้เล่น
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

        System.out.println(getName() + " uses Shield Slam!");
        int damage = getAttack() + getShield();
        target.takeDamage(damage);
        target.applyVulnerable(1);
        resetShield();

        return true;
    }

    /**
     * คืนค่าค่าพลังงานที่ใช้สำหรับสกิลที่ 3
     *
     * @return ค่าพลังงานที่ใช้ (3)
     */
    @Override
    public int getSkill3Cost() {
        return 3;
    }

    /**
     * คืนชื่อสกิลที่ 1
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill1Name() {
        return "Shield Strike";
    }

    /**
     * คืนชื่อสกิลที่ 2
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill2Name() {
        return "Fortify";
    }

    /**
     * คืนชื่อสกิลที่ 3
     *
     * @return ชื่อสกิล
     */
    @Override
    public String getSkill3Name() {
        return "Shield Slam";
    }
}