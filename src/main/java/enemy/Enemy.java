package enemy;

import inventory.Inventory;

public abstract class Enemy {
    protected String name;
    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int defense;

    public Enemy(String name) {
        setName(name);
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

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
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

    public void normalAttack(Enemy enemy) {
        if(enemy.getDefense() - getAttack() <= 0) return;
        enemy.setHp(enemy.getHp() - getAttack());
    }
}
