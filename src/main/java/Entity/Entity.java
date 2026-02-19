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
        this.maxHp = maxHp;
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

    // ===== Combat =====

    public void normalAttack(Entity target) {
        int damage = Math.max(0, attack - target.defense);
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {

        if (evadeStacks > 0) {
            evadeStacks--;
            return;
        }

        if (vulnerableTurns > 0) {
            damage *= 1.5;
        }

        if (shield > 0) {
            int absorbed = Math.min(shield, damage);
            shield -= absorbed;
            damage -= absorbed;
        }

        hp -= damage;
        if (hp < 0) hp = 0;
    }

    public void endTurn() {

        if (poisonTurns > 0) {
            hp--;
            poisonTurns--;
        }

        if (vulnerableTurns > 0) {
            vulnerableTurns--;
        }
    }

    // ===== Status Effects =====

    public void applyVulnerable(int turns) {
        vulnerableTurns += turns;
    }

    public boolean isVulnerable() {
        return vulnerableTurns > 0;
    }

    public void addPoison(int turns) {
        poisonTurns += turns;
    }

    public void addEvade(int stacks) {
        evadeStacks += stacks;
    }

    // ===== Shield =====

    public void block() {
        addShield(5); // default block amount
    }

    public void addShield(int amount) {
        shield += amount;
    }

    public void resetShield() {
        shield = 0;
    }


}
