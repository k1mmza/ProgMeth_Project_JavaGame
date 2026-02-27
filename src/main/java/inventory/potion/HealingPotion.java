package inventory.potion;

import Entity.Player;

public class HealingPotion extends Potion{
    private int increaseHp;

    public HealingPotion() {
        super("HealingPotion",15,10);
        setIncreaseHp(25);
        this.description = "Restore 25 HP.";
    }

    @Override
    public boolean use(Player character) {
        character.heal(25);
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
