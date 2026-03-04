package Entity;

public abstract class Entity {

    protected String name;

    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int defense;

    protected int shield = 0;

    protected int vulnerableTurns = 0;
    protected int poisonTurns = 0;
    protected int evadeStacks = 0;

    public Entity(String name, int maxHp, int attack, int defense) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    // ===== Basic Info =====

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public int getHp() { return hp; }
    public void setHp(int hp) {
        if (hp > maxHp) {
            this.hp = maxHp;
        } else if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }
    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        if (hp > this.maxHp) {
            hp = this.maxHp;
        }
    }
    public int getAttack() { return attack; }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getDefense() { return defense; }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getShield() { return shield; }
    public void heal(int amount) {
        if (!isAlive()) return;

        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }


    public int getEvadeStacks() {
        return evadeStacks;
    }

    public int getPoisonTurns() {
        return poisonTurns;
    }

    public int getVulnerableTurns() {
        return vulnerableTurns;
    }

    // ===== Combat =====

    public void normalAttack(Entity target) {
        int damage = Math.max(0, attack - target.defense);
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {

        if (evadeStacks > 0) {
            System.out.println(getName() + " evaded the attack!");
            evadeStacks--;
            return;
        }

        if (vulnerableTurns > 0) {
            damage = (int)(damage * 1.5);
            System.out.println(getName() + " is vulnerable! Damage increased!");
        }

        // === NEW: Defense Percentage Reduction ===
        double reduction = defense * 0.05;  // 5% per defense
        reduction = Math.min(reduction, 0.60); // cap at 60%

        int reducedAmount = (int)(damage * reduction);
        damage -= reducedAmount;

        damage = Math.max(damage, 1); // always at least 1 damage

        if (shield > 0) {
            int absorbed = Math.min(shield, damage);
            shield -= absorbed;
            damage -= absorbed;

            System.out.println(getName() + "'s shield absorbed " + absorbed + " damage!");
        }

        if (damage > 0) {
            hp -= damage;
            System.out.println(getName() + " took " + damage + " damage!");
        }

        if (hp < 0) hp = 0;
    }

    public void startTurn() {
        if (poisonTurns > 0) {
            int poisonDamage = poisonTurns / 2 + 1;
            System.out.println(getName() + " takes " + poisonDamage + " poison damage!");
            setHp((getHp() - poisonDamage));
            poisonTurns--;
        }

    }

    public void endTurn() {
        if (vulnerableTurns > 0) {
            vulnerableTurns--;
        }
    }

    // ===== Status Effects =====

    public void applyVulnerable(int turns) {
        System.out.println(getName() + " is now vulnerable for " + turns + " turns!");
        vulnerableTurns += turns;
    }

    public boolean isVulnerable() {
        return vulnerableTurns > 0;
    }

    public void addPoison(int turns) {
        System.out.println(getName() + " is poisoned for " + turns + " turns!");
        poisonTurns += turns;
    }

    public void addEvade(int stacks) {
        System.out.println(getName() + " gains " + stacks + " evade stack(s)!");
        evadeStacks += stacks;
    }


    // ===== Shield =====

    public void block() {
        addShield(3); // default block amount
    }

    public void addShield(int amount) {
        shield += amount;
    }

    public void resetShield() {
        shield = 0;
    }


}
