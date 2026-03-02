package Entity;

/**
 * คลาสพื้นฐานของสิ่งมีชีวิตในเกม (เช่น ผู้เล่น และศัตรู)
 * <p>
 * จัดการค่าสถานะพื้นฐาน เช่น HP, พลังโจมตี, พลังป้องกัน
 * รวมถึงระบบต่อสู้และสถานะพิเศษ (Status Effects)
 * เช่น Shield, Vulnerable, Poison และ Evade
 * </p>
 */
public abstract class Entity {

    /** ชื่อของตัวละคร */
    protected String name;

    /** พลังชีวิตสูงสุด */
    protected int maxHp;

    /** พลังชีวิตปัจจุบัน */
    protected int hp;

    /** ค่าพลังโจมตี */
    protected int attack;

    /** ค่าพลังป้องกัน */
    protected int defense;

    /** ค่าพลังโล่ป้องกัน (Shield) */
    protected int shield = 0;

    /** จำนวนเทิร์นที่ติดสถานะ Vulnerable */
    protected int vulnerableTurns = 0;

    /** จำนวนเทิร์นที่ติดสถานะ Poison */
    protected int poisonTurns = 0;

    /** จำนวนครั้งที่สามารถหลบการโจมตีได้ */
    protected int evadeStacks = 0;

    /**
     * สร้าง Entity พร้อมค่าสถานะพื้นฐาน
     *
     * @param name   ชื่อของตัวละคร
     * @param maxHp  พลังชีวิตสูงสุด
     * @param attack ค่าพลังโจมตี
     * @param defense ค่าพลังป้องกัน
     */
    public Entity(String name, int maxHp, int attack, int defense) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    // ===== Basic Info =====

    /**
     * ตรวจสอบว่ายังมีชีวิตอยู่หรือไม่
     *
     * @return true หาก HP มากกว่า 0
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /** @return ชื่อตัวละคร */
    public String getName() { return name; }

    /**
     * ตั้งค่าชื่อตัวละคร
     *
     * @param name ชื่อใหม่
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return พลังชีวิตปัจจุบัน */
    public int getHp() { return hp; }

    /**
     * ตั้งค่าพลังชีวิต โดยจะไม่เกิน maxHp และไม่ต่ำกว่า 0
     *
     * @param hp ค่าพลังชีวิตใหม่
     */
    public void setHp(int hp) {
        if (hp > maxHp) {
            this.hp = maxHp;
        } else if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    /** @return พลังชีวิตสูงสุด */
    public int getMaxHp() { return maxHp; }

    /**
     * ตั้งค่าพลังชีวิตสูงสุด
     *
     * @param maxHp ค่าสูงสุดใหม่
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /** @return ค่าพลังโจมตี */
    public int getAttack() { return attack; }

    /**
     * ตั้งค่าพลังโจมตี
     *
     * @param attack ค่าใหม่
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /** @return ค่าพลังป้องกัน */
    public int getDefense() { return defense; }

    /**
     * ตั้งค่าพลังป้องกัน
     *
     * @param defense ค่าใหม่
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /** @return ค่าโล่ป้องกันปัจจุบัน */
    public int getShield() { return shield; }

    /**
     * ฟื้นฟูพลังชีวิต
     *
     * @param amount จำนวน HP ที่ต้องการฟื้นฟู
     */
    public void heal(int amount) {
        setHp(getHp() + amount);
    }

    // ===== Combat =====

    /**
     * โจมตีปกติใส่เป้าหมาย
     *
     * @param target เป้าหมายที่ถูกโจมตี
     */
    public void normalAttack(Entity target) {
        int damage = Math.max(0, attack - target.defense);
        target.takeDamage(damage);
    }

    /**
     * รับความเสียหาย พร้อมคำนวณผลจากสถานะและค่าป้องกัน
     *
     * @param damage ความเสียหายเริ่มต้น
     */
    public void takeDamage(int damage) {

        if (evadeStacks > 0) {
            //System.out.println(getName() + " evaded the attack!");
            evadeStacks--;
            return;
        }

        if (vulnerableTurns > 0) {
            damage = (int)(damage * 1.5);
            //System.out.println(getName() + " is vulnerable! Damage increased!");
        }

        double reduction = defense * 0.05;
        reduction = Math.min(reduction, 0.60);

        int reducedAmount = (int)(damage * reduction);
        damage -= reducedAmount;

        damage = Math.max(damage, 1);

        if (shield > 0) {
            int absorbed = Math.min(shield, damage);
            shield -= absorbed;
            damage -= absorbed;

            //System.out.println(getName() + "'s shield absorbed " + absorbed + " damage!");
        }

        if (damage > 0) {
            hp -= damage;
            //System.out.println(getName() + " took " + damage + " damage!");
        }

        if (hp < 0) hp = 0;
    }

    /**
     * เริ่มต้นเทิร์นของตัวละคร
     * <p>
     * จะคำนวณความเสียหายจาก Poison หากมี
     * </p>
     */
    public void startTurn() {
        if (poisonTurns > 0) {
            int poisonDamage = poisonTurns / 2 + 1;
            System.out.println(getName() + " takes " + poisonDamage + " poison damage!");
            hp -= poisonDamage;
            poisonTurns--;
        }
    }

    /**
     * จบเทิร์นของตัวละคร
     * <p>
     * ลดจำนวนเทิร์นของสถานะ Vulnerable
     * </p>
     */
    public void endTurn() {
        if (vulnerableTurns > 0) {
            vulnerableTurns--;
        }
    }

    // ===== Status Effects =====

    /**
     * เพิ่มสถานะ Vulnerable
     *
     * @param turns จำนวนเทิร์นที่ติดสถานะ
     */
    public void applyVulnerable(int turns) {
        //System.out.println(getName() + " is now vulnerable for " + turns + " turns!");
        vulnerableTurns += turns;
    }

    /**
     * ตรวจสอบว่าติดสถานะ Vulnerable หรือไม่
     *
     * @return true หากยังมีจำนวนเทิร์นคงเหลือ
     */
    public boolean isVulnerable() {
        return vulnerableTurns > 0;
    }

    /**
     * เพิ่มสถานะ Poison
     *
     * @param turns จำนวนเทิร์นที่ติดพิษ
     */
    public void addPoison(int turns) {
        //System.out.println(getName() + " is poisoned for " + turns + " turns!");
        poisonTurns += turns;
    }

    /**
     * เพิ่มจำนวนครั้งการหลบการโจมตี
     *
     * @param stacks จำนวนครั้งที่เพิ่ม
     */
    public void addEvade(int stacks) {
        //System.out.println(getName() + " gains " + stacks + " evade stack(s)!");
        evadeStacks += stacks;
    }

    // ===== Shield =====

    /**
     * ใช้ท่าป้องกันพื้นฐาน เพิ่มโล่ 3 หน่วย
     */
    public void block() {
        addShield(3);
    }

    /**
     * เพิ่มค่าโล่ป้องกัน
     *
     * @param amount จำนวนที่ต้องการเพิ่ม
     */
    public void addShield(int amount) {
        shield += amount;
    }

    /**
     * รีเซ็ตค่าโล่ป้องกันเป็น 0
     */
    public void resetShield() {
        shield = 0;
    }
}