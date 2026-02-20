package Entity;

import inventory.Inventory;

public abstract class Player extends Entity {

    protected int energy;
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

    public void addEnergy(int amount) {
        energy += amount;
    }

    public void spendEnergy(int amount) {
        energy -= amount;
    }

    public void focus() {
        setEnergy(getEnergy() + 1);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    // ===== Inventory =====

    public Inventory getInventory() {
        return inventory;
    }

    // ===== Skills =====

    public abstract void skill1(Entity target);
    public abstract void skill2(Entity target);
    public abstract void skill3(Entity target);

    public abstract String getSkill1Name();
    public abstract String getSkill2Name();
    public abstract String getSkill3Name();

}
