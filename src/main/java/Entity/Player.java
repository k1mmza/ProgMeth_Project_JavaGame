package Entity;

import inventory.Inventory;

/**
 * คลาสนามธรรม Player ใช้แทนตัวละครที่ผู้เล่นควบคุม
 * <p>
 * สืบทอดจาก Entity และเพิ่มระบบพลังงาน (Energy),
 * ระบบเศรษฐกิจ (Gold) และคลังไอเทม (Inventory)
 * รวมถึงกำหนดโครงสร้างสกิลที่คลาสลูกต้องนำไปพัฒนา
 * </p>
 */
public abstract class Player extends Entity {

    protected int energy;
    private int maxEnergy = 5;
    protected int gold;
    protected Inventory inventory;

    /**
     * สร้างผู้เล่นใหม่พร้อมกำหนดค่าสถานะพื้นฐาน
     *
     * @param name   ชื่อตัวละคร
     * @param maxHp  ค่าพลังชีวิตสูงสุด
     * @param attack ค่าพลังโจมตี
     * @param defense ค่าพลังป้องกัน
     */
    public Player(String name, int maxHp, int attack, int defense) {
        super(name, maxHp, attack, defense);
        this.inventory = new Inventory(10);
    }

    // ===== Economy =====

    /**
     * คืนค่าจำนวนทองที่ผู้เล่นมี
     *
     * @return จำนวนทอง
     */
    public int getGold() { return gold; }

    /**
     * เพิ่มจำนวนทองให้ผู้เล่น
     *
     * @param amount จำนวนทองที่เพิ่ม
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * กำหนดจำนวนทองใหม่
     *
     * @param gold จำนวนทองที่ต้องการตั้งค่า
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    // ===== Energy =====

    /**
     * คืนค่าพลังงานปัจจุบัน
     *
     * @return พลังงานที่เหลืออยู่
     */
    public int getEnergy() { return energy; }

    /**
     * เพิ่มพลังงาน 1 หน่วย
     */
    public void focus() {
        gainEnergy(1);
    }

    /**
     * กำหนดค่าพลังงานโดยตรง
     *
     * @param energy ค่าพลังงานใหม่
     */
    public void setEnergy(int energy) {
        if (energy < 0) {
            this.energy = 0;
            return;
        }
        this.energy = Math.min(energy, maxEnergy);
    }

    /**
     * รีเซ็ตพลังงานให้เป็น 0
     */
    public void resetEnergyToZero() {
        energy = 0;
    }

    /**
     * เพิ่มพลังงานตามจำนวนที่กำหนด
     * โดยจะไม่เกินค่า maxEnergy
     *
     * @param amount จำนวนพลังงานที่เพิ่ม
     */
    public void gainEnergy(int amount) {
        energy += amount;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }

    /**
     * ใช้พลังงานตามจำนวนที่กำหนด
     *
     * @param amount จำนวนพลังงานที่ต้องใช้
     * @return true หากพลังงานเพียงพอและถูกหักสำเร็จ,
     *         false หากพลังงานไม่เพียงพอ
     */
    public boolean useEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    /**
     * รีเซ็ตสถานะการต่อสู้ทั้งหมดของผู้เล่น
     * เช่น โล่ พลังงาน และสถานะผิดปกติ
     */
    public void resetCombatState() {
        this.shield = 0;
        this.energy = 0;
        this.evadeStacks = 0;
        this.vulnerableTurns = 0;
        this.poisonTurns = 0;
    }

    // ===== Inventory =====

    /**
     * คืนค่า Inventory ของผู้เล่น
     *
     * @return อ็อบเจกต์ Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    // ===== Skills =====

    /**
     * สกิลที่ 1 ของผู้เล่น
     *
     * @param target เป้าหมายของสกิล
     * @return true หากใช้สำเร็จ, false หากล้มเหลว
     */
    public abstract boolean skill1(Entity target);

    /**
     * สกิลที่ 2 ของผู้เล่น
     *
     * @param target เป้าหมายของสกิล
     * @return true หากใช้สำเร็จ, false หากล้มเหลว
     */
    public abstract boolean skill2(Entity target);

    /**
     * สกิลที่ 3 ของผู้เล่น
     *
     * @param target เป้าหมายของสกิล
     * @return true หากใช้สำเร็จ, false หากล้มเหลว
     */
    public abstract boolean skill3(Entity target);

    /**
     * คืนชื่อสกิลที่ 1
     *
     * @return ชื่อสกิล
     */
    public abstract String getSkill1Name();

    /**
     * คืนชื่อสกิลที่ 2
     *
     * @return ชื่อสกิล
     */
    public abstract String getSkill2Name();

    /**
     * คืนชื่อสกิลที่ 3
     *
     * @return ชื่อสกิล
     */
    public abstract String getSkill3Name();

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 1
     *
     * @return ค่าพลังงานที่ใช้
     */
    public abstract int getSkill1Cost();

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 2
     *
     * @return ค่าพลังงานที่ใช้
     */
    public abstract int getSkill2Cost();

    /**
     * คืนค่าพลังงานที่ใช้สำหรับสกิลที่ 3
     *
     * @return ค่าพลังงานที่ใช้
     */
    public abstract int getSkill3Cost();
}
