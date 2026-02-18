package character;

import enemy.Enemy;
import inventory.Inventory;

public abstract class Character {
    protected String name;
    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int defense;
    protected int energy;
    protected int gold;
    protected Inventory inventory;

    public Character(String name) {
        setName(name);
        setInventory(new Inventory());
    }

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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getEnergy() {
        return energy;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setHp(int hp) {
        int realHp;
        if (hp + getHp() > getMaxHp()) {
            realHp = getMaxHp();
        } else if (hp + getHp() < 0) {
            realHp = 0;
        } else realHp = hp + getHp();
        this.hp = realHp;
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

    public void normalAttack(Enemy enemy) {
        if(enemy.getDefense() - getAttack() <= 0) return;
        enemy.setHp(enemy.getHp() - getAttack());
    }

    public void focus() {
        setEnergy(getEnergy() + 1);
    }

    public void block() {

    }

    public abstract void Skill1(Enemy enemy);

    public abstract void Skill2(Enemy enemy);

    public abstract void Skill3(Enemy enemy);
}
