package enemy;

import Entity.Entity;

public abstract class Enemy extends Entity {
    protected int goldReward;

    public Enemy(String name, int maxHp, int attack, int defense, int goldReward) {
        super(name, maxHp, attack, defense);
        this.goldReward = goldReward;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }


    public abstract void performAction(Entity target);
}
