package Entity;

import inventory.Inventory;

public abstract class Player extends Entity {

    protected int energy;
    private int maxEnergy = 5;
    protected int gold;
    protected Inventory inventory;

    public Player(String name, int maxHp, int attack, int defense) {
        super(name, maxHp, attack, defense);
        this.inventory = new Inventory(10);
    }

    // ===== Economy =====

    public int getGold() { return gold; }

    public void addGold(int amount) {
        gold += amount;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    // ===== Energy =====

    public int getEnergy() { return energy; }

    public void focus() {
        gainEnergy(1);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void resetEnergyToZero() {
        energy = 0;
    }

    public void gainEnergy(int amount) {
        energy += amount;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }

    public boolean useEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    public void resetCombatState() {
        this.shield = 0;
        this.energy = 0;
        this.evadeStacks = 0;
        this.vulnerableTurns = 0;
        this.poisonTurns = 0;
    }

    // ===== Inventory =====

    public Inventory getInventory() {
        return inventory;
    }


    // ===== Skills =====

    public abstract boolean skill1(Entity target);
    public abstract boolean skill2(Entity target);
    public abstract boolean skill3(Entity target);

    public abstract String getSkill1Name();
    public abstract String getSkill2Name();
    public abstract String getSkill3Name();

    public abstract int getSkill1Cost();
    public abstract int getSkill2Cost();
    public abstract int getSkill3Cost();

}
