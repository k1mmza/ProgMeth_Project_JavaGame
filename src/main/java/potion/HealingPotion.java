package potion;

import Entity.Player;

public class HealingPotion extends Potion{
    private int increaseHp;

    public HealingPotion() {
        super("HealingPotion",15,10);
        setIncreaseHp(15);
    }

    @Override
    public boolean use(Player character) {
        character.setHp(character.getHp() + getIncreaseHp());
        character.getInventory().removePotion(this);
        return true;
    }

    public int getIncreaseHp() {
        return increaseHp;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setIncreaseHp(int increaseHp) {
        this.increaseHp = increaseHp;
    }
}
