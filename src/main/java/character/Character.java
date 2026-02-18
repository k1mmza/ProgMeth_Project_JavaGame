package character;

import inventory.Inventory;

public abstract class Character {

    protected String name;
    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int defense;

    protected int block = 0;

    protected int energy;
    protected int gold;
    protected Inventory inventory;

    protected int vulnerableTurns = 0;
    protected int poisonTurns = 0;
    protected int evadeStacks = 0;

    public Character(String name) {
        setName(name);
        setInventory(new Inventory());
    }

    // Basic Info

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getShield() {
        return block;
    }

    public int getEnergy() {
        return energy;
    }

    public int getGold() {
        return gold;
    }

    public Inventory getInventory() {
        return inventory;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setHp(int hp) {
        if (hp > maxHp) {
            this.hp = maxHp;
        } else if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // Combat System

    public void normalAttack(Character target) {
        int damage = calculateDamage(target);
        target.takeDamage(damage);
    }

    protected int calculateDamage(Character target) {
        return Math.max(0, this.attack - target.defense);
    }

    public void takeDamage(int damage) {

        if (hasEvade()) {
            evadeStacks--;
            return;
        }

        if (isVulnerable()) {
            damage = (int)(damage * 1.5);
        }

        if (block > 0) {
            int absorbed = Math.min(block, damage);
            block -= absorbed;
            damage -= absorbed;
        }

        setHp(getHp() - damage);
    }


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

    public boolean hasEvade() {
        return evadeStacks > 0;
    }

    public void endTurn() {

        if (poisonTurns > 0) {
            setHp(getHp() - 1);
            poisonTurns--;
        }

        if (vulnerableTurns > 0) {
            vulnerableTurns--;
        }

    }

    // Block System

    public void addShield(int amount) {
        block += amount;
    }

    public void resetShield() {
        block = 0;
    }

    public void block() {
        addShield(5); // default block amount
    }

    // Energy

    public void focus() {
        setEnergy(getEnergy() + 1);
    }

}
